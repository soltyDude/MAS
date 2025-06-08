package com.colony.mas_5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@DiscriminatorValue("HUMAN")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HumanColonist extends Colonist {

    @Column(length = 100)
    @NotBlank @Size(min = 2, max = 100)
    private String nationality;

    public HumanColonist(String name, String nationality) {
        super(null, name);
        this.nationality = nationality;
    }
}
