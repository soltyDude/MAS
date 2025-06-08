package com.example;

import java.io.Serializable;
import java.util.Objects;


public final class Quantity implements Serializable {

    private static final long serialVersionUID = 1L;

    /* --------- fields --------- */
    private final double amount; // must be > 0
    private final String unit;   // non-null, non-blank

    /* --------- constructor --------- */
    public Quantity(double amount, String unit) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("unit must be non-empty");
        }
        this.amount = amount;
        this.unit = unit.trim();
    }

    /* --------- getters --------- */
    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    /* --------- utility --------- */
    @Override
    public String toString() {
        return amount + " " + unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantity)) return false;
        Quantity quantity = (Quantity) o;
        return Double.compare(quantity.amount, amount) == 0 &&
                unit.equals(quantity.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, unit);
    }
}
