package com.example;

import java.io.Serializable;
import java.util.*;


public class TransportMission implements Serializable {

    private static final long serialVersionUID = 1L;

    /* ---------- extent ---------- */
    private static final Set<TransportMission> EXTENT = new HashSet<>();
    public static List<TransportMission> getExtent() {
        return Collections.unmodifiableList(new ArrayList<TransportMission>(EXTENT));
    }

    /* ---------- identity ---------- */
    private final String       missionId;
    private final HabitatBlock destination;

    /* ---------- XOR rovers ---------- */
    private final Set<Rover> rovers = new HashSet<>();

    /* ---------- simple cargo logic ---------- */
    private static final double MAX_ROVER_PAYLOAD_KG = 500.0;   // per rover
    private double totalPayloadKg = 0.0;

    /* ---------- constructor ---------- */
    public TransportMission(String missionId, HabitatBlock dest) {
        this.missionId   = Objects.requireNonNull(missionId, "missionId null");
        this.destination = Objects.requireNonNull(dest,      "dest null");
        EXTENT.add(this);
    }

    /* ---------- package-private hooks (XOR) ---------- */
    void internalAdd(Rover r)   { rovers.add(r); }
    void internalRemove(Rover r){ rovers.remove(r); }

    /* ---------- public business API ---------- */

    /** Load some cargo; checks rover capacity. */
    public void loadCargo(double kg) {
        if (kg <= 0) throw new IllegalArgumentException("kg must be positive");
        double possible = rovers.size() * MAX_ROVER_PAYLOAD_KG;
        if (totalPayloadKg + kg > possible) {
            throw new IllegalStateException("Not enough rover capacity");
        }
        totalPayloadKg += kg;
    }

    /** Unload cargo into the destination block. */
    public void unloadAll() {
        if (!destination.hasFreeSlot()) {
            throw new IllegalStateException("Destination block is full – cannot unload crew/cargo");
        }
        totalPayloadKg = 0.0;   // cargo delivered
    }

    public double getTotalPayloadKg() { return totalPayloadKg; }

    /* ---------- queries ---------- */
    public List<Rover> getRovers() {
        return Collections.unmodifiableList(new ArrayList<Rover>(rovers));
    }

    /* ---------- equals / hashCode ---------- */
    @Override public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof TransportMission)) return false;
        return missionId.equalsIgnoreCase(((TransportMission)o).missionId);
    }
    @Override public int hashCode(){ return missionId.toLowerCase().hashCode(); }

    @Override public String toString(){
        return "TransportMission{" + missionId +
                " → " + destination +
                ", payload=" + totalPayloadKg + " kg" +
                ", rovers=" + rovers.size() + '}';
    }
}
