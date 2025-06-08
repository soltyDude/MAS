package com.colony.mas_5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "drone")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    @NotBlank
    @Size(min = 3, max = 64)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "energy_source_id", nullable = false)
    @NotNull
    @ToString.Exclude
    private EnergySource energySource;


    public Drone(String serialNumber, EnergySource energySource) {
        this.serialNumber = serialNumber;
        this.setEnergySource(energySource);
    }

    public void setEnergySource(EnergySource newEnergySource) {
        if (this.energySource == newEnergySource) {
            return;
        }

        if (this.energySource != null) {
            this.energySource.getDrones().remove(this);
        }

        this.energySource = newEnergySource;

        if (newEnergySource != null && !newEnergySource.getDrones().contains(this)) {
            newEnergySource.getDrones().add(this);
        }
    }
}
