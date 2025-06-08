package com.colony.mas_5.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionAssignmentId implements Serializable {
    private Long missionId;
    private Long specialistId;
}
