package com.villege.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonBean implements Serializable {

    private long id;
    private String name;
}
