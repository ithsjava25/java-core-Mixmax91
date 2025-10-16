package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable{

    private final int warrantyMonths;
    private final BigDecimal weight;

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
