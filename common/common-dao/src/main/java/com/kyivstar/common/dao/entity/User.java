package com.kyivstar.common.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Created by igor on 24.09.17.
 */
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @NotNull
    private String phone;

    private String name;
    private String surname;
    private String melodyId;

    private Double balance;

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getMelodyId() {
        return melodyId;
    }

    public User setMelodyId(String melodyId) {
        this.melodyId = melodyId;
        return this;
    }

    public Double getBalance() {
        return balance;
    }

    public User setBalance(Double balance) {
        this.balance = balance;
        return this;
    }
}
