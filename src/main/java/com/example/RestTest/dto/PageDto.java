package com.example.RestTest.dto;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Text;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;

import java.util.List;

@JsonView(Views.ID_NAME.class)
public class PageDto {
    @JsonView(Views.ID_NAME.class)
    private List<com.example.RestTest.domain.Text> messages;
    @JsonView(Views.ID_NAME.class)
    private int currentPage;
    @JsonView(Views.ID_NAME.class)
    private int totalPages;

    public PageDto(List<com.example.RestTest.domain.Text> messages, int currentPage, int totalPages) {
        this.messages = messages;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }


    public List<Text> getMessages() {
        return messages;
    }

    public void setMessages(List<Text> messages) {
        this.messages = messages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "PageDto{" +
                "messages=" + messages +
                ", currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                '}';
    }
}
