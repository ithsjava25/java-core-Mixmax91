package com.example;

import java.time.LocalDate;

/**
 * interface for Perishable products
 * Exposes expirationDate() and default isExpired()
 */

public interface Perishable {
    LocalDate expirationDate();

    /**
     *
     * @return true if expirationDate isBefore LocalDate.now() else false
     */
    default boolean isExpired() {
        return expirationDate().isBefore(LocalDate.now());
    }
}
