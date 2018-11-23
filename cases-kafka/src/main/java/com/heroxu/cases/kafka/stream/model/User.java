package com.heroxu.cases.kafka.stream.model;

import lombok.Data;

@Data
public class User {
    private String name;
    private String address;
    private String gender;
    private int age;
}