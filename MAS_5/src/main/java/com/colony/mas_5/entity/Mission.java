package com.colony.mas_5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Mission.withAssignments",
                attributeNodes = @NamedAttributeNode("assignments")
        )
})
@Entity
@Table(name = "mission")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank @Size(min = 3, max = 200)
    private String objective;

    @OneToMany(mappedBy = "mission",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<MissionAssignment> assignments = new HashSet<>();

    public void addAssignment(MissionAssignment ma) {
        if (ma == null || assignments.contains(ma)) return;
        assignments.add(ma);
        ma.setMission(this);
    }

    public void removeAssignment(MissionAssignment ma) {
        if (ma == null || !assignments.remove(ma)) return;
        ma.setMission(null);
    }
}
