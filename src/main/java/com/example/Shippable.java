package com.example;

import java.math.BigDecimal;

/**
 * Interface for shippable Products
 * exposes calculateShippingCost() and weight()
 */
public interface Shippable {
    BigDecimal calculateShippingCost();
    double weight();
}
