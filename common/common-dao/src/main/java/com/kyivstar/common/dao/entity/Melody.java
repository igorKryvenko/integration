package com.kyivstar.common.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


@Document(collection = "melodies")
public class Melody {
    @Id
    private String id;
    @NotNull
    private String melodyLink;

    private Double price;

    public String getId() {
        return id;
    }

    public Melody setId(String id) {
        this.id = id;
        return this;
    }

    public String getMelodyLink() {
        return melodyLink;
    }

    public Melody setMelodyLink(String melodyLink) {
        this.melodyLink = melodyLink;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Melody setPrice(Double price) {
        this.price = price;
        return this;
    }
}
