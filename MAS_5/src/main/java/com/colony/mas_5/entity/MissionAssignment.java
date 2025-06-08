package com.colony.mas_5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "mission_assignment")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MissionAssignment {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private MissionAssignmentId id = new MissionAssignmentId();

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("missionId")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("specialistId")
    private Specialist specialist;

    @Column(nullable = false, length = 50)
    @NotBlank @Size(min = 3, max = 50)
    private String role;

    public MissionAssignment(Mission m, Specialist s, String role) {
        if (m.getId() == null || s.getId() == null) {
            throw new IllegalStateException("Mission and Specialist must be persisted first");
        }
        this.mission = m;
        this.specialist = s;
        this.role = role;
        this.id = new MissionAssignmentId(m.getId(), s.getId());
        m.addAssignment(this);
        s.addAssignment(this);
    }
}
