package com.example.fanfics.model;

public class Chapter {

    private Long id;

    private String name;

    private String content;

    private Composition composition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
