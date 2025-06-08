package com.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Domain entity representing a room in the shelter.
 *
 * Implements:
 *   • mandatory multi-value attribute (equipment)
 *   • mandatory attributes (number, capacity)
 *   • simple business logic: add / remove equipment, allocate inhabitant
 *   • method overriding (toString / equals / hashCode)
 */
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    /* --------- attributes --------- */

    private final int number;                // mandatory, unique per shelter
    private final int capacity;              // mandatory, > 0
    private final List<String> equipment;    // mandatory multi-value

    private final List<Inhabitant> occupants = new ArrayList<>(); // internal list

    /* --------- constructor --------- */

    /**
     * Creates a room with given number, capacity and at least one equipment item.
     *
     * @param number   room identifier, must be positive
     * @param capacity maximum number of inhabitants (>0)
     * @param equipmentList list with at least one non-blank item
     */
    public Room(int number, int capacity, List<String> equipmentList) {
        if (number <= 0) {
            throw new IllegalArgumentException("number must be positive");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        validateEquipmentList(equipmentList);

        this.number = number;
        this.capacity = capacity;
        // defensive copy
        this.equipment = new ArrayList<String>(equipmentList);
    }

    /* --------- multi-value attribute helpers --------- */

    public List<String> getEquipment() {
        // return copy to prevent external modification
        return Collections.unmodifiableList(new ArrayList<String>(equipment));
    }

    public boolean addEquipment(String item) {
        if (item == null || item.trim().isEmpty()) {
            throw new IllegalArgumentException("equipment item must be non-empty");
        }
        return equipment.add(item.trim());
    }

    /**
     * Removes item; if last element would be removed, throws exception
     */
    public boolean removeEquipment(String item) {
        if (item == null || item.trim().isEmpty()) {
            throw new IllegalArgumentException("equipment item must be non-empty");
        }
        if (equipment.size() == 1 && equipment.contains(item.trim())) {
            throw new IllegalStateException("cannot remove last equipment item");
        }
        return equipment.remove(item.trim());
    }

    private static void validateEquipmentList(List<String> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("equipment list must contain at least one item");
        }
        for (int i = 0; i < list.size(); i++) {
            String it = list.get(i);
            if (it == null || it.trim().isEmpty()) {
                throw new IllegalArgumentException("equipment items must be non-blank");
            }
        }
    }

    /* --------- occupancy business logic --------- */

    public List<Inhabitant> getOccupants() {
        return Collections.unmodifiableList(new ArrayList<Inhabitant>(occupants));
    }

    /**
     * Allocates an inhabitant if room has free slot.
     */
    public void allocate(Inhabitant inhabitant) {
        if (inhabitant == null) {
            throw new IllegalArgumentException("inhabitant cannot be null");
        }
        if (occupants.size() >= capacity) {
            throw new IllegalStateException("room is full");
        }
        occupants.add(inhabitant);
    }

    public void vacate(Inhabitant inhabitant) {
        occupants.remove(inhabitant);
    }

    public boolean hasFreePlace() {
        return occupants.size() < capacity;
    }

    /* --------- getters for simple fields --------- */

    public int getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }

    /* --------- overrides --------- */

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", capacity=" + capacity +
                ", equipment=" + equipment +
                ", occupants=" + occupants.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return number == room.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
