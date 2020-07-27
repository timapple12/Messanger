package com.example.RestTest.controller;

import ch.qos.logback.core.net.ObjectWriter;
import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Text;
import com.example.RestTest.domain.User;
import com.example.RestTest.dto.EventType;
import com.example.RestTest.dto.MetaDto;
import com.example.RestTest.dto.ObjectType;
import com.example.RestTest.exceptions.NotFoundException;
import com.example.RestTest.repository.TextRepository;
import com.example.RestTest.util.WsSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("message")
public class MessageController {

    private static String PATTERN_URL = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String PATTERN_IMG = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern REGEX_URL = Pattern.compile(PATTERN_URL, Pattern.CASE_INSENSITIVE);
    private static Pattern REGEX_IMG = Pattern.compile(PATTERN_IMG, Pattern.CASE_INSENSITIVE);

    private final TextRepository messages;
    private LocalDateTime localDateTime;
    private final BiConsumer<EventType,Text> wsSender;
    @Autowired
    public MessageController(TextRepository messages, WsSender wsSender) {
        this.messages = messages;
        this.wsSender = wsSender.getSender(Views.ID_NAME.class, ObjectType.MESSAGE);
    }

    @GetMapping
    @JsonView(Views.ID_NAME.class)
    public List<Text> listOFMessages() {
        return messages.findAll();
    }
    @GetMapping("{id}")
    public Text getOneMessage(@PathVariable("id") Text id){
        return id;
    }
   /* private Map<String, String> getMessages(@PathVariable String id){
        return messages.stream()
                .filter(message->message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);                 // Throws 404 exception
    }*/
    @PostMapping
    public Text createMessage(
            @RequestBody Text message,
            @AuthenticationPrincipal User user,
            Principal principal){

        OAuth2Authentication auth = (OAuth2Authentication) principal;
        String author = auth.getUserAuthentication().getDetails().toString();
        // System.out.println(author);

        message.setCreationTime(LocalDateTime.now());
      //  message.setAuthor(user);
        fillMetaData(message);
        Text text = messages.save(message);
        wsSender.accept(EventType.CREATE,text);
        return text;
    }

    @PutMapping("{id}")                 //refresh' the list of messages
    public Text refresh(@PathVariable("id") Text textFromDb,
                        @RequestBody Text message){
        BeanUtils.copyProperties(message,textFromDb,"id");              // copy from messages to textFromDb ignoring id
        Text refreshedMessage=messages.save(textFromDb);
        wsSender.accept(EventType.UPDATE,refreshedMessage);
        return refreshedMessage;
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Text message){
        if(messages==null){
            throw new NotFoundException();
        }else{
            messages.delete(message);
            wsSender.accept(EventType.REMOVE,message);
        }
    }
    private void fillMetaData(Text mess) {
        String message = mess.getText();
        Matcher matcher = REGEX_URL.matcher(message);
        if (matcher.find()) {
            String url = message.substring(matcher.start(), matcher.end());
            matcher = REGEX_IMG.matcher(url);
            mess.setLink(url);
            if(matcher.find()){
                mess.setCover(url);
            }else if(!url.contains("youtu")){
                MetaDto meta = getMetaData(url);
                mess.setTitle(meta.getTitle());
                mess.setDescription(meta.getDescription());
                mess.setCover(meta.getCover());
            }
        }
    }
    private MetaDto getMetaData(String url){
        try {
            Document document = Jsoup.connect(url).get();
            Elements title  = document.select("meta[name$=title], meta[property$=title]");
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
    private String getContent(Element element){
        return element == null?"":element.attr("content");
    }
}
