package com.example.RestTest.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
}
