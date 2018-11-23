package com.heroxu.cases.kafka.stream.model;

import lombok.Data;

@Data
public class Order {
    private String userName;
    private String itemName;
    private long transactionDate;
    private int quantity;
}