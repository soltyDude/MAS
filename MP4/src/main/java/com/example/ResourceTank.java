package com.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class ResourceTank implements Serializable {

    private static final long serialVersionUID = 1L;

    /* ---------- dynamic constraint constant ---------- */
    private static final double MAX_DROP_RATIO = 0.10;   // 10 %

    /* ---------- class extent ---------- */
    private static final Set<ResourceTank> EXTENT = new HashSet<>();

    public static List<ResourceTank> getExtent() {
        return Collections.unmodifiableList(new ArrayList<ResourceTank>(EXTENT));
    }

    /* ---------- attributes ---------- */
    private final String id;                 // business key
    private final ResourceType type;
    private final double capacityKg;         // > 0

    private double levelKg;                  // 0 ≤ level ≤ capacity

    /* ---------- constructor ---------- */
    public ResourceTank(String id,
                        ResourceType type,
                        double capacityKg,
                        double initialLevelKg)
    {
        this.id   = Objects.requireNonNull(id,   "id null");
        this.type = Objects.requireNonNull(type, "type null");

        if (capacityKg <= 0) {
            throw new IllegalArgumentException("capacityKg must be positive");
        }
        this.capacityKg = capacityKg;

        /* initial fill must obey static bounds only */
        validateLevelRange(initialLevelKg);
        this.levelKg = initialLevelKg;

        EXTENT.add(this);
    }

    /* ---------- dynamic constraint logic ---------- */


    public void setLevelKg(double newLevelKg) {
        validateLevelRange(newLevelKg);

        double drop = this.levelKg - newLevelKg;           // positive when level falls
        if (drop > capacityKg * MAX_DROP_RATIO) {
            throw new IllegalArgumentException(
                    "Level drop (" + drop + " kg) exceeds "
                            + (MAX_DROP_RATIO * 100) + "% of capacity");
        }
        this.levelKg = newLevelKg;
    }

    private void validateLevelRange(double lvl) {
        if (lvl < 0 || lvl > capacityKg) {
            throw new IllegalArgumentException("levelKg out of [0; capacity]");
        }
    }

    /* ---------- getters ---------- */

    public String getId() { return id; }

    public ResourceType getType() { return type; }

    public double getCapacityKg() { return capacityKg; }

    public double getLevelKg() { return levelKg; }

    /* ---------- equals / hashCode by business key ---------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceTank)) return false;
        ResourceTank that = (ResourceTank) o;
        return id.equalsIgnoreCase(that.id);
    }

    @Override
    public int hashCode() {
        return id.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "ResourceTank{" + id +
                ", type=" + type +
                ", level=" + levelKg + "/" + capacityKg + " kg}";
    }
}
