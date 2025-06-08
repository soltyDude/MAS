package com.example;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class ResourceTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    /* ---------- class extent (list to allow duplicates) ---------- */
    private static final List<ResourceTransfer> EXTENT = new ArrayList<>();

    public static List<ResourceTransfer> getExtent() {
        return Collections.unmodifiableList(new ArrayList<ResourceTransfer>(EXTENT));
    }

    /* ---------- attributes ---------- */
    private final ResourceTank   tank;         // source
    private final HabitatBlock   block;        // destination
    private final double         kgMoved;      // > 0
    private final LocalDateTime  timestamp;    // when transfer happened

    /* ---------- constructor ---------- */
    public ResourceTransfer(ResourceTank   tank,
                            HabitatBlock   block,
                            double         kgMoved,
                            LocalDateTime  timestamp)
    {
        this.tank      = Objects.requireNonNull(tank,      "tank null");
        this.block     = Objects.requireNonNull(block,     "block null");
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp null");

        if (kgMoved <= 0) {
            throw new IllegalArgumentException("kgMoved must be positive");
        }
        this.kgMoved = kgMoved;

        EXTENT.add(this);              // duplicates allowed – bag semantics
    }

    /* ---------- getters ---------- */

    public ResourceTank getTank() {
        return tank;
    }

    public HabitatBlock getBlock() {
        return block;
    }

    public double getKgMoved() {
        return kgMoved;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /* ---------- toString (no equals / hashCode on purpose!) ---------- */

    @Override
    public String toString() {
        return "ResourceTransfer{" + kgMoved + " kg "
                + tank.getType() + " -> " + block
                + " @ " + timestamp + '}';
    }
}
