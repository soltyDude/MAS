package com.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class HabitatBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    /* ---------- static attribute-constraint ---------- */
    public static final int CAPACITY_MIN = 1;
    public static final int CAPACITY_MAX = 100;

    /* ---------- class extent ---------- */
    private static final Set<HabitatBlock> EXTENT = new HashSet<>();

    public static List<HabitatBlock> getExtent() {
        return Collections.unmodifiableList(new ArrayList<HabitatBlock>(EXTENT));
    }

    /* ---------- attributes ---------- */
    private final String blockCode;                 // business key (unique in real life)
    private int capacity;                           // static-range constraint

    private double currentPressure;                 // just a sample ordinary attr

    /* ---------- associations ---------- */
    private final Set<Inhabitant> residents    = new HashSet<>();
    private final Set<Inhabitant> vipResidents = new HashSet<>();   // subset

    /* ---------- constructor ---------- */
    public HabitatBlock(String blockCode, int capacity) {

        this.blockCode = Objects.requireNonNull(blockCode, "blockCode null");
        setCapacity(capacity);                      // validates range
        EXTENT.add(this);
    }

    /* ---------- capacity (static constraint) ---------- */
    public void setCapacity(int capacity) {
        if (capacity < CAPACITY_MIN || capacity > CAPACITY_MAX) {
            throw new IllegalArgumentException("capacity out of range");
        }
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    /* ---------- subset / superset helpers ---------- */

    public void addResident(Inhabitant inh) {
        Objects.requireNonNull(inh, "inhabitant null");

        if (residents.size() >= capacity) {
            throw new IllegalStateException("block is full");
        }
        if (inh.getHabitat() != null && inh.getHabitat() != this) {
            // move inhabitant from previous block
            inh.getHabitat().removeResident(inh);
        }
        residents.add(inh);
        inh.setHabitat(this);                       // back-reference
    }

    public void removeResident(Inhabitant inh) {
        if (residents.remove(inh)) {
            vipResidents.remove(inh);               // keep subset invariant
            inh.setHabitat(null);
        }
    }

    public void addVipResident(Inhabitant inh) {
        addResident(inh);                           // validates capacity & back-ref
        vipResidents.add(inh);                      // subset rule now guaranteed
    }

    public void removeVipResident(Inhabitant inh) {
        vipResidents.remove(inh);
        // resident link remains – still lives here, just not VIP
    }

    /* ---------- queries ---------- */

    public List<Inhabitant> getResidents() {
        return Collections.unmodifiableList(new ArrayList<Inhabitant>(residents));
    }

    public List<Inhabitant> getVipResidents() {
        return Collections.unmodifiableList(new ArrayList<Inhabitant>(vipResidents));
    }

    public boolean hasFreeSlot() {
        return residents.size() < capacity;
    }

    /* ---------- equals / hashCode (business key) ---------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HabitatBlock)) return false;
        HabitatBlock that = (HabitatBlock) o;
        return blockCode.equalsIgnoreCase(that.blockCode);
    }

    @Override
    public int hashCode() {
        return blockCode.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "HabitatBlock{" + blockCode +
                ", cap=" + capacity +
                ", res=" + residents.size() +
                ", vip=" + vipResidents.size() + '}';
    }
}
