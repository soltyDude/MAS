package com.colony.mas_5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Colonist who can take part in missions.
 */
@Entity
@Table(name = "specialist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Specialist {

    /** Surrogate PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /** Unique callsign of the specialist. */
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank
    @Size(min = 2, max = 100)
    private String codename;

    /** Assignment links (mission ↔ specialist). */
    @OneToMany(mappedBy = "specialist",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<MissionAssignment> assignments = new HashSet<>();

    /* ---------- bidirectional helpers ---------- */

    public void addAssignment(MissionAssignment assignment) {
        if (assignment == null || assignments.contains(assignment)) return;
        assignments.add(assignment);
        assignment.setSpecialist(this);
    }

    public void removeAssignment(MissionAssignment assignment) {
        if (assignment == null || !assignments.remove(assignment)) return;
        assignment.setSpecialist(null);
    }
}
