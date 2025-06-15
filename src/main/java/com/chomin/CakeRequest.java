package com.chomin;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;

@Introspected
@Serdeable
public class CakeRequest {
    @NotNull
    private String title;
    @NotNull
    private String desc;
    @NotNull
    private String image;

    public CakeRequest() {}

    public CakeRequest(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
