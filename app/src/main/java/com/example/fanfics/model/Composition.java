package com.example.fanfics.model;


import java.util.HashSet;
import java.util.Set;

public class Composition {

    private Long id;

    private String name;

    private String description;

    private String image;

    private Set<Chapter> chapters;

    private Fandom fandom;

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public Fandom getFandom() {
        return fandom;
    }
}
