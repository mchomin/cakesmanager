package com.chomin;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class Cake {
    private Integer id;
    private String title;
    private String desc;
    private String image;

    public Cake() {}

    public Cake(Integer id, String title, String desc, String image) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public Cake(CakeRequest cakeRequest) {
        this.id = null;
        this.title = cakeRequest.getTitle();
        this.desc = cakeRequest.getDesc();
        this.image = cakeRequest.getImage();
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
