package com.colony.mas_5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "HabitatUnit.withRooms",
                attributeNodes = @NamedAttributeNode("rooms")
        )
})
@Entity
@Table(name = "habitat_unit")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HabitatUnit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank @Size(min = 3, max = 100)
    private String unitName;

    @OneToMany(mappedBy = "habitatUnit",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Room> rooms = new ArrayList<>();

    public HabitatUnit(String unitName) {
        this.unitName = unitName;
    }

    public void addRoom(Room room) {
        if (room == null || rooms.contains(room)) return;
        rooms.add(room);
        room.setHabitatUnit(this);
    }

    public void removeRoom(Room room) {
        if (room == null || !rooms.remove(room)) return;
        room.setHabitatUnit(null);
    }
}
