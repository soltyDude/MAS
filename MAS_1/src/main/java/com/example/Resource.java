package com.example;

import java.io.Serializable;
import java.util.Objects;

/**
 * Domain entity representing a named resource with a quantity,
 * e.g. "Water 20 l".
 *
 * Implements:
 *  • complex attribute (Quantity)
 *  • mandatory attributes
 *  • simple business logic: add / consume
 *  • method overriding (toString / equals / hashCode)
 */
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    /* --------- attributes --------- */
    private String name;         // mandatory, non-blank
    private Quantity quantity;   // complex attribute, mandatory

    /* --------- constructor --------- */
    public Resource(String name, Quantity quantity) {
        setName(name);
        setQuantity(quantity);
    }

    /* --------- getters / setters --------- */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name must be non-empty");
        }
        this.name = name.trim();
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        if (quantity == null) {
            throw new IllegalArgumentException("quantity cannot be null");
        }
        this.quantity = quantity;
    }

    /* --------- business methods --------- */

    /**
     * Adds given amount to current quantity.
     * @param delta positive value to add
     */
    public void add(double delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("delta must be positive");
        }
        double newAmount = quantity.getAmount() + delta;
        this.quantity = new Quantity(newAmount, quantity.getUnit());
    }

    /**
     * Consumes given amount from the resource.
     * @param delta positive amount to subtract
     * @throws IllegalStateException if not enough available
     */
    public void consume(double delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("delta must be positive");
        }
        double newAmount = quantity.getAmount() - delta;
        if (newAmount < 0) {
            throw new IllegalStateException("not enough " + name + " to consume");
        }
        this.quantity = new Quantity(newAmount, quantity.getUnit());
    }

    /* --------- overrides --------- */

    @Override
    public String toString() {
        return "Resource{name='" + name + "', quantity=" + quantity + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource)) return false;
        Resource resource = (Resource) o;
        return name.equals(resource.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
