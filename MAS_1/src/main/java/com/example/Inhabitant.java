package com.example;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Inhabitant implements Serializable {

    /* --------- persistence --------- */
    private static final long serialVersionUID = 1L;

    /* --------- class extent --------- */
    private static final List<Inhabitant> EXTENT = new ArrayList<>();


    public static List<Inhabitant> getExtent() {
        return Collections.unmodifiableList(new ArrayList<Inhabitant>(EXTENT));
    }


    public void remove() {
        synchronized (EXTENT) {
            EXTENT.remove(this);
        }
    }

    /* --------- attributes --------- */

    // mandatory
    private String name;               // non-null, non-blank
    private LocalDate dateOfBirth;     // non-null, not in the future

    // optional
    private String profession;         // may be null, otherwise non-blank

    // class attribute
    private static int minAllowedAge = 18; // default threshold

    /* --------- constructors --------- */


    public Inhabitant(String name, LocalDate dateOfBirth) {
        setName(name);
        setDateOfBirth(dateOfBirth);

        synchronized (EXTENT) {
            EXTENT.add(this);
        }
    }


    public Inhabitant(String name, LocalDate dateOfBirth, String profession) {
        this(name, dateOfBirth);
        setProfession(profession);
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("dateOfBirth cannot be null");
        }
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("dateOfBirth cannot be in the future");
        }
        this.dateOfBirth = dateOfBirth;
    }


    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        if (profession != null && profession.trim().isEmpty()) {
            throw new IllegalArgumentException("profession cannot be blank");
        }
        this.profession = profession;
    }

    /* --------- class attribute accessors --------- */

    public static int getMinAllowedAge() {
        return minAllowedAge;
    }

    public static void setMinAllowedAge(int minAllowedAge) {
        if (minAllowedAge <= 0) {
            throw new IllegalArgumentException("minAllowedAge must be > 0");
        }
        Inhabitant.minAllowedAge = minAllowedAge;
    }

    /* --------- derived attribute --------- */


    public boolean isAdult() {
        return getAge() >= minAllowedAge;
    }

    /* --------- business method --------- */

       public static double getAverageAge() {
        synchronized (EXTENT) {
            if (EXTENT.isEmpty()) {
                return 0.0;
            }
            int sum = 0;
            for (int i = 0; i < EXTENT.size(); i++) {
                sum += EXTENT.get(i).getAge();
            }
            return sum / (double) EXTENT.size();
        }
    }

    /* --------- overrides --------- */

    @Override
    public String toString() {
        return "Inhabitant{name='" + name + "', dateOfBirth=" + dateOfBirth +
                ", profession='" + profession + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inhabitant)) return false;
        Inhabitant that = (Inhabitant) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(dateOfBirth, that.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }

    /* --------- extent helper for persistence --------- */

    public static void replaceExtent(List<Inhabitant> newExtent) {
        if (newExtent == null) {
            throw new IllegalArgumentException("newExtent cannot be null");
        }
        synchronized (EXTENT) {
            EXTENT.clear();
            EXTENT.addAll(newExtent);
        }
    }
}
