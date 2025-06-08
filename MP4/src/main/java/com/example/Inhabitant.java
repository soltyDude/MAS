package com.example;
import java.util.*;

public class Inhabitant {
    private static final Set<Inhabitant> EXTENT = new HashSet<>();

    private final UUID lunarId = UUID.randomUUID();
    private String name;
    private int age;
    private Profession profession;


    private HabitatBlock habitat;                       // back‑reference

    public Inhabitant(String name, int age, Profession profession) {
        setName(name);
        setAge(age);
        setProfession(profession);
        EXTENT.add(this);
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name).trim();
    }

    public void setAge(int age) {
        if (age < 0) throw new IllegalArgumentException("Negative age");
        this.age = age;
    }

    public void setProfession(Profession p) {
        this.profession = Objects.requireNonNull(p);
    }

    /* ---------- back-reference (package-private setter уже есть) ---------- */

    public HabitatBlock getHabitat() {            // ← ДОБАВИТЬ
        return habitat;
    }


   void setHabitat(HabitatBlock h) {      // called only by HabitatBlock
        this.habitat = h;
    }

    public static Set<Inhabitant> extent() {
        return Collections.unmodifiableSet(EXTENT);
    }
}
