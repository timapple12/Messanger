package com.example.RestTest.service;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Text;
import com.example.RestTest.domain.User;
import com.example.RestTest.dto.EventType;
import com.example.RestTest.dto.MetaDto;
import com.example.RestTest.dto.ObjectType;
import com.example.RestTest.dto.PageDto;
import com.example.RestTest.repository.TextRepository;
import com.example.RestTest.repository.UserDataRepository;
import com.example.RestTest.util.WsSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageService {
    private final static String PATTERN_URL = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private final static String PATTERN_IMG = "\\.(jpeg|jpg|gif|png)$";

    private final static Pattern REGEX_URL = Pattern.compile(PATTERN_URL, Pattern.CASE_INSENSITIVE);
    private final static Pattern REGEX_IMG = Pattern.compile(PATTERN_IMG, Pattern.CASE_INSENSITIVE);

    private final TextRepository textRepository;
    private final UserDataRepository userDataRepository;
    private final BiConsumer<EventType, Text> wsSender;

    @Autowired
    public MessageService(TextRepository textRepository, UserDataRepository userDataRepository, WsSender wsSender) {
        this.textRepository = textRepository;
        this.userDataRepository = userDataRepository;
        this.wsSender = wsSender.getSender(Views.ID_NAME.class, ObjectType.MESSAGE);
    }

    public void deleteMessage(Text message) {
        textRepository.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }

    public Text refresh(Text textFromDb, Text message) {
        BeanUtils.copyProperties(message, textFromDb, "id");              // copy from messages to textFromDb ignoring id
        Text refreshedMessage = textRepository.save(textFromDb);
        wsSender.accept(EventType.UPDATE, refreshedMessage);
        return refreshedMessage;
    }


    public Text createMessage(Text message, Principal principal) {
        User authorisedUser = new UserGetAuthService().getAuthorisedUser(principal, userDataRepository);

        message.setCreationTime(LocalDateTime.now());
        message.setUser(authorisedUser);
        Text text = textRepository.save(message);

        fillMetaData(text);
        wsSender.accept(EventType.CREATE, text);
        return text;
    }

    public static void fillMetaData(Text mess) {
        String message = mess.getText();
        Matcher matcher = REGEX_URL.matcher(message);
        if (matcher.find()) {
            String url = message.substring(matcher.start(), matcher.end());
            matcher = REGEX_IMG.matcher(url);
            mess.setLink(url);
            if (matcher.find()) {
                mess.setCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = getMetaData(url);
                mess.setTitle(meta.getTitle());
                mess.setDescription(meta.getDescription());
                mess.setCover(meta.getCover());
            }
        }
    }

    private static MetaDto getMetaData(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements title = document.select("meta[name$=title], meta[property$=title]");
            Elements description = document.select("meta[name$=description], meta[property$=description]");
            Elements cover = document.select("meta[name$=image], meta[property$=image]");
            return new MetaDto(
                    getContent(title.first()),
                    getContent(description.first()),
                    getContent(cover.first())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }

    public PageDto findAll(Pageable pageable) {

        Page<Text> page = textRepository.findAll(pageable);

        List<Text> textList = page.getContent();
        textList.forEach(obj -> MessageService.fillMetaData(obj));

        return new PageDto(page.getContent(),
                            pageable.getPageNumber(),
                            page.getTotalPages());
    }
}
