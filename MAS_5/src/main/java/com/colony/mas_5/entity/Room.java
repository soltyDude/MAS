package com.colony.mas_5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/** Functional area that cannot exist outside its parent HabitatUnit. */
@Entity
@Table(name = "room")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(min = 3, max = 100)
    private String purpose;

    /** Many rooms belong to one unit; LAZY to avoid N+1. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "habitat_unit_id", nullable = false)
    @NotNull
    @ToString.Exclude
    private HabitatUnit habitatUnit;

    public Room(String purpose) {
        this.purpose = purpose;
    }
}
