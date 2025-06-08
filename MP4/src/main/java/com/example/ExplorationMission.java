package com.example;

import java.io.Serializable;
import java.util.*;


public class ExplorationMission implements Serializable {

    private static final long serialVersionUID = 1L;

    /* ---------- extent ---------- */
    private static final Set<ExplorationMission> EXTENT = new HashSet<>();
    public static List<ExplorationMission> getExtent() {
        return Collections.unmodifiableList(new ArrayList<ExplorationMission>(EXTENT));
    }

    /* ---------- identity ---------- */
    private final String missionId;
    private final String sector;

    /* ---------- XOR rovers ---------- */
    private final Set<Rover> rovers = new HashSet<>();

    /* ---------- simple science stats ---------- */
    private double distanceKm = 0.0;
    private int    samplesCollected = 0;

    /* ---------- constructor ---------- */
    public ExplorationMission(String missionId, String sector) {
        this.missionId = Objects.requireNonNull(missionId, "missionId null");
        this.sector    = Objects.requireNonNull(sector,    "sector null");
        EXTENT.add(this);
    }

    /* ---------- package-private hooks (XOR) ---------- */
    void internalAdd(Rover r)   { rovers.add(r); }
    void internalRemove(Rover r){ rovers.remove(r); }

    /* ---------- public business API ---------- */

    /** Rover reports travelled distance. */
    public void reportDistance(double km) {
        if (km <= 0) throw new IllegalArgumentException("km must be positive");
        distanceKm += km;
    }

    /** Rover drops a geological sample into the box. */
    public void collectSample() {
        samplesCollected++;
    }

    public double getDistanceKm()       { return distanceKm; }
    public int    getSamplesCollected() { return samplesCollected; }

    /* ---------- queries ---------- */
    public List<Rover> getRovers() {
        return Collections.unmodifiableList(new ArrayList<Rover>(rovers));
    }

    /* ---------- equals / hashCode ---------- */
    @Override public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof ExplorationMission)) return false;
        return missionId.equalsIgnoreCase(((ExplorationMission)o).missionId);
    }
    @Override public int hashCode(){ return missionId.toLowerCase().hashCode(); }

    @Override public String toString(){
        return "ExplorationMission{" + missionId +
                ", sector=" + sector +
                ", km=" + distanceKm +
                ", samples=" + samplesCollected +
                ", rovers=" + rovers.size() + '}';
    }
}
