package com.example;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Implements Shippable.
 * Fields: int warrantyMonths, BigDecimal weight (kg).
 * Warranty and weight cannot be negative.
 * Contains productDetails().
 * Shipping cost = base 79 + 49 if weight > 5.0 kg.
 */

public class ElectronicsProduct extends Product implements Shippable{

    private final int warrantyMonths;
    private final BigDecimal weight;

    /**
     *
     * @throws IllegalArgumentException if warrantyMonths or weight is negative
     */
    public ElectronicsProduct(UUID id, String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight) {
        super(id, name, category, price);

        if(warrantyMonths <= 0)
            throw new IllegalArgumentException("Warranty months cannot be negative.");

        if(weight.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Weight cannot be negative.");

        this.warrantyMonths = warrantyMonths;
        this.weight = weight;
    }


    @Override
    String productDetails() {
        return "Electronics: " + name() + ", Warranty: " + warrantyMonths + " months";
    }

    /**
     * Shipping cost = base 79 + 49 if weight > 5.0 kg
     * @return
     */
    @Override
    public BigDecimal calculateShippingCost() {
        BigDecimal base = new BigDecimal(79);
        BigDecimal maxBaseWeight = new BigDecimal(5);
        BigDecimal addedCost = new BigDecimal(49);

        return weight.compareTo(maxBaseWeight) >= 0 ? base.add(addedCost) : base;
    }

    @Override
    public double weight() {
        return weight.doubleValue();
    }
}
