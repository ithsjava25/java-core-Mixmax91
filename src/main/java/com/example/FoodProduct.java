package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Implements Perishable and Shippable.
 * Fields: LocalDate expirationDate, BigDecimal weight (kg).
 * Price and weight cannot be negative.
 * Contains productDetails().
 * Shipping cost = weight * 50.
 */

public class FoodProduct extends Product implements Perishable, Shippable {

    private final LocalDate expirationDate;
    private final BigDecimal weight;

    /**
     *
     * @throws IllegalArgumentException if weight or price is negative
     */
    public FoodProduct(UUID id, String name, Category category,
                       BigDecimal price, LocalDate expirationDate, BigDecimal weight) {
        super(id, name, category, price);

        if(weight.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Weight cannot be negative.");

        if(price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Price cannot be negative.");

        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    String productDetails() {
        return "Food: " + name() + ", Expires: " + expirationDate;
    }

    @Override
    public LocalDate expirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        return Perishable.super.isExpired();
    }

    /**
     * Shipping cost = 50 * weight (kg)
     * @return BigDecimal
     */
    @Override
    public BigDecimal calculateShippingCost() {
        BigDecimal base = new BigDecimal(50);
        return weight.multiply(base);
    }

    @Override
    public double weight() {
        return weight.doubleValue();
    }
}
