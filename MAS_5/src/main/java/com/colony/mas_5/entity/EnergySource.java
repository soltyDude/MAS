package com.colony.mas_5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Power source that supplies one-to-many drones.
 */
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "EnergySource.withDrones",
                attributeNodes = @NamedAttributeNode("drones")
        )
})
@Entity
@Table(name = "energy_source")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class EnergySource {

    /** Surrogate PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /** Source type (Solar, Battery, Nuclear…). */
    @Column(nullable = false, length = 64)
    @NotBlank
    @Size(min = 3, max = 64)
    private String type;

    /** Drones that draw power from this source. */
    @OneToMany(mappedBy = "energySource",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @ToString.Exclude
    private List<Drone> drones = new ArrayList<>();

    public EnergySource(String type) {
        this.type = type;
    }

    /** Bidirectionally attach a drone. */
    public void addDrone(Drone drone) {
        if (drone == null || drones.contains(drone)) return;
        drones.add(drone);
        drone.setEnergySource(this);
    }

    /** Bidirectionally detach a drone. */
    public void removeDrone(Drone drone) {
        if (drone == null || !drones.remove(drone)) return;
        drone.setEnergySource(null);
    }
}
