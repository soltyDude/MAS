package com.colony.mas_5.data;

import com.colony.mas_5.entity.*;
import com.colony.mas_5.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Populates the database with demo data on first application start.
 * Each section is executed only if the corresponding table is empty.
 */
@Slf4j
@Component
@RequiredArgsConstructor          // ctor for all final fields
@Transactional                    // one atomic transaction
public class DataLoader implements CommandLineRunner {

    private final HydroponicPlantRepository   plantRepo;
    private final ColonistRepository          colonistRepo;
    private final EnergySourceRepository      energySourceRepo;
    private final MissionRepository           missionRepo;
    private final SpecialistRepository        specialistRepo;
    private final MissionAssignmentRepository assignmentRepo;
    private final HabitatUnitRepository       habitatRepo;

    @Override
    public void run(String... args) {

        /* ───────── 1. Hydroponic plants ───────── */
        if (plantRepo.count() == 0) {
            plantRepo.saveAll(List.of(
                    new HydroponicPlant("Lettuce", 3, 22.0),
                    new HydroponicPlant("Tomato",  5, 24.0),
                    new HydroponicPlant("Spinach", 4, 20.5)
            ));
            log.info("HydroponicPlant demo data loaded.");
        }

        /* ───────── 2. Colonists (inheritance) ───────── */
        if (colonistRepo.count() == 0) {
            colonistRepo.saveAll(List.of(
                    new HumanColonist("Alice", "Russian"),
                    new RobotColonist("XR-17", "WorkerBot-9000")
            ));
            log.info("Colonist demo data loaded.");
        }

        /* ───────── 3. Energy sources & drones ───────── */
        if (energySourceRepo.count() == 0) {
            EnergySource solar   = new EnergySource("Solar");
            EnergySource nuclear = new EnergySource("Nuclear");

            solar.addDrone(new Drone("DR-001", solar));
            solar.addDrone(new Drone("DR-002", solar));
            nuclear.addDrone(new Drone("DR-003", nuclear));
            nuclear.addDrone(new Drone("DR-004", nuclear));

            energySourceRepo.saveAll(List.of(solar, nuclear));   // cascade saves drones
            log.info("EnergySource & Drone demo data loaded.");
        }

        /* ───────── 4. Missions - Specialists - Assignments ───────── */
        if (assignmentRepo.count() == 0) {

            // create & persist missions
            Mission explore = new Mission();
            explore.setObjective("Explore Sector 7G");
            Mission repair  = new Mission();
            repair.setObjective("Repair communication array");
            missionRepo.saveAll(List.of(explore, repair));
            missionRepo.flush();                // ensure IDs present

            // create & persist specialists
            Specialist alpha = new Specialist();
            alpha.setCodename("Alpha");
            Specialist beta  = new Specialist();
            beta.setCodename("Beta");
            Specialist gamma = new Specialist();
            gamma.setCodename("Gamma");
            specialistRepo.saveAll(List.of(alpha, beta, gamma));
            specialistRepo.flush();             // ensure IDs present

            // link via association-class
            explore.addAssignment(new MissionAssignment(explore, alpha, "Engineer"));
            explore.addAssignment(new MissionAssignment(explore, beta,  "Scout"));
            repair .addAssignment(new MissionAssignment(repair,  gamma, "Medic"));

            // cascade from Mission & Specialist persists assignments
            log.info("Mission, Specialist & MissionAssignment demo data loaded.");
        }

        /* ───────── 5. Habitat units & rooms (composition) ───────── */
        if (habitatRepo.count() == 0) {
            HabitatUnit alphaUnit = new HabitatUnit("Alpha Module");
            HabitatUnit betaUnit  = new HabitatUnit("Beta Module");

            alphaUnit.addRoom(new Room("Living Quarters"));
            alphaUnit.addRoom(new Room("Laboratory"));
            alphaUnit.addRoom(new Room("Storage"));

            betaUnit.addRoom(new Room("Command Center"));
            betaUnit.addRoom(new Room("Gym"));

            habitatRepo.saveAll(List.of(alphaUnit, betaUnit));   // cascade saves rooms
            log.info("HabitatUnit & Room demo data loaded.");
        }
    }
}
