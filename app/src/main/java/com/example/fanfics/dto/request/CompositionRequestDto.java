package com.example.fanfics.dto.request;

public class CompositionRequestDto {
        private String name;

        private String description;

        private String image;

    public CompositionRequestDto(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public CompositionRequestDto(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public CompositionRequestDto() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
