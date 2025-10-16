package com.example;

import java.util.HashMap;
import java.util.Map;


public class Category {

    private static final Map<String, Category> CACHEMAP = new HashMap<>();
    String name;

    private Category(String name){
        this.name = name;
    }

    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Category name can't be blank");
        }
        String formattedName = name.trim().substring(0, 1).toUpperCase() + name.substring(1);
        return cacheAndAdd(formattedName);
    }

    private static Category cacheAndAdd(String name) {
        if (!CACHEMAP.containsKey(name)) {
            CACHEMAP.put(name, new Category(name));
        }
        return CACHEMAP.get(name);
    }

    public String getName(){
        return this.name;
    }

}