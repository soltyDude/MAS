package com.colony.mas_5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@DiscriminatorValue("ROBOT")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RobotColonist extends Colonist {

    @Column(length = 100)
    @NotBlank @Size(min = 2, max = 100)
    private String model;

    public RobotColonist(String name, String model) {
        super(null, name);
        this.model = model;
    }
}
