package com.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class Profession implements Serializable {

    private static final long serialVersionUID = 1L;

    /* ---------- class extent ---------- */
    private static final Set<Profession> EXTENT = new HashSet<>();


    public static List<Profession> getExtent() {
        return Collections.unmodifiableList(new ArrayList<Profession>(EXTENT));
    }

    /* ---------- attribute ---------- */
    private String name;   // business key, may change but must stay unique

    /* ---------- constructor ---------- */
    public Profession(String name) {
        setName(name);      // validates uniqueness
        EXTENT.add(this);
    }

    /* ---------- setter with uniqueness validation ---------- */
    public void setName(String newName) {
        String n = Objects.requireNonNull(newName, "name null").trim();
        if (n.isEmpty()) {
            throw new IllegalArgumentException("name must be non-empty");
        }

        /* If value unchanged (ignoring case) – do nothing */
        if (this.name != null && this.name.equalsIgnoreCase(n)) {
            return;
        }

        /* Check uniqueness inside extent (case-insensitive) */
        for (Profession p : EXTENT) {
            if (p != this && p.name.equalsIgnoreCase(n)) {
                throw new IllegalArgumentException("profession already exists: " + n);
            }
        }

        /* Passed validation – assign */
        this.name = n;
    }

    /* ---------- getter ---------- */
    public String getName() {
        return name;
    }

    /* ---------- business-key based equality ---------- */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profession)) return false;
        Profession that = (Profession) o;
        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "Profession{" + name + '}';
    }
}
