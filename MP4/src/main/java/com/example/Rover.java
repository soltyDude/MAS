package com.example;

import java.io.Serializable;
import java.util.*;


public class Rover implements Serializable {

    private static final long serialVersionUID = 1L;

    /* ---------- extent ---------- */
    private static final Set<Rover> EXTENT = new HashSet<>();
    public static List<Rover> getExtent() {
        return Collections.unmodifiableList(new ArrayList<Rover>(EXTENT));
    }

    /* ---------- attributes ---------- */
    private final String callSign;          // business key

    /* ---------- XOR links ---------- */
    private ExplorationMission explorationMission;
    private TransportMission   transportMission;

    /* ---------- constructor ---------- */
    public Rover(String callSign) {
        this.callSign = Objects.requireNonNull(callSign, "callSign null");
        EXTENT.add(this);
    }

    /* ---------- public API enforcing XOR ---------- */

    public void assignExploration(ExplorationMission m) {
        Objects.requireNonNull(m, "mission null");
        if (transportMission != null) {                           // было: transportMission != null && transportMission != m
            throw new IllegalStateException("Already on transport mission: XOR violated");
        }
        if (explorationMission == m) return;                      // уже назначен

        detachFromCurrent();
        explorationMission = m;
        m.internalAdd(this);
    }

    public void assignTransport(TransportMission m) {
        Objects.requireNonNull(m, "mission null");
        if (explorationMission != null) {                         // было: explorationMission != null && explorationMission != m
            throw new IllegalStateException("Already on exploration mission: XOR violated");
        }
        if (transportMission == m) return;

        detachFromCurrent();
        transportMission = m;
        m.internalAdd(this);
    }


    /** Leaves whatever mission the rover is currently in. */
    public void detachFromCurrent() {
        if (explorationMission != null) {
            explorationMission.internalRemove(this);
            explorationMission = null;
        }
        if (transportMission != null) {
            transportMission.internalRemove(this);
            transportMission = null;
        }
    }

    /* ---------- getters ---------- */
    public String getCallSign() { return callSign; }
    public Optional<ExplorationMission> getExplorationMission() {
        return Optional.ofNullable(explorationMission);
    }
    public Optional<TransportMission> getTransportMission() {
        return Optional.ofNullable(transportMission);
    }

    /* ---------- equals / hashCode ---------- */
    @Override public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Rover)) return false;
        return callSign.equalsIgnoreCase(((Rover)o).callSign);
    }
    @Override public int hashCode(){ return callSign.toLowerCase().hashCode(); }

    @Override public String toString(){
        String mission = explorationMission != null ? explorationMission.toString()
                : transportMission   != null ? transportMission.toString()
                : "idle";
        return "Rover{" + callSign + " @ " + mission + '}';
    }
}
