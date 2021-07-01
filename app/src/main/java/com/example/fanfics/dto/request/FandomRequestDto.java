package com.example.fanfics.dto.request;

public class FandomRequestDto {

    private String name;

    private String image;

    public FandomRequestDto() {}

    public FandomRequestDto(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
