package com.example.RestTest.dto;

public class MetaDto {
    private String title;
    private String cover;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MetaDto(String title, String description, String cover){
        this.title = title;
        this.description = description;
        this.cover = cover;
    }


}
