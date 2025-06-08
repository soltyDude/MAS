package com.colony.mas_5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "hydroponic_plant")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HydroponicPlant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank @Size(min = 3, max = 100)
    private String name;

    @Column(name = "water_requirement_lpd", nullable = false)
    @NotNull @Min(1) @Max(100)
    private Integer waterRequirementLitersPerDay;

    @Column(name = "optimal_temperature_c", nullable = false)
    @NotNull @DecimalMin("10.0") @DecimalMax("35.0")
    private Double optimalTemperatureCelsius;

    public HydroponicPlant(String name,
                           Integer waterRequirementLitersPerDay,
                           Double optimalTemperatureCelsius) {
        this.name = name;
        this.waterRequirementLitersPerDay = waterRequirementLitersPerDay;
        this.optimalTemperatureCelsius = optimalTemperatureCelsius;
    }
}
