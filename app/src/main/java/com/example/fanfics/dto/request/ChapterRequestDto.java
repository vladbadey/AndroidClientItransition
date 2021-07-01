package com.example.fanfics.dto.request;

public class ChapterRequestDto {

    private int id;

    private String name;

    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
