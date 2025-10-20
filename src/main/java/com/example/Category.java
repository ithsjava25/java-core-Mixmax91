package com.example;

import java.util.HashMap;
import java.util.Map;

/**
 * Private constructor and public static factory of(String name)
 * Name can't be null or blank.
 * Formats to initial capital letter
 * Uses cache/flyweight
 *
 */

public class Category {
    private static final Map<String, Category> CACHEMAP = new HashMap<>();
    String name;

    private Category(String name){
        this.name = name;
    }

    /**
     *
     * @param name
     * Formats a String so that it starts with uppercase
     * Returns instance with the String name or creates a new one
     * @throws IllegalArgumentException if name is null or blank
     * @return Category
     */
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

    /**
     *
     * @param name
     * Checks if there is an instance with the String name and returns it
     * Creates a new if it doesn't exist
     * @return Category
     */
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