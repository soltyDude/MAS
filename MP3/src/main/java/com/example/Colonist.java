package com.example;

import java.util.*;

public abstract class Colonist {
    private final String id;
    private String name;

    public Colonist(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public abstract void performDuties();
}

class EngineerColonist extends Colonist {
    private Map<String, Boolean> equipmentStatus;

    public EngineerColonist(String name) {
        super(name);
        equipmentStatus = new HashMap<>();
        equipmentStatus.put("Hydraulic Lift", false);  // false означает, что оборудование сломано
        equipmentStatus.put("Generator", true);       // true означает, что оборудование работает
    }

    @Override
    public void performDuties() {
        System.out.println(getName() + " is starting to perform maintenance tasks.");

        for (Map.Entry<String, Boolean> entry : equipmentStatus.entrySet()) {
            String equipment = entry.getKey();
            boolean isWorking = entry.getValue();

            if (!isWorking) {
                // Если оборудование сломано, инженер его ремонтирует
                System.out.println(getName() + " is repairing " + equipment + ".");
            } else {
                // Если оборудование работает, инженер проверяет его состояние
                System.out.println(getName() + " is checking the status of " + equipment + ".");
            }
        }
    }
}

class ScientistColonist extends Colonist {
    private List<String> researchProjects;

    public ScientistColonist(String name) {
        super(name);
        researchProjects = new ArrayList<>();
        researchProjects.add("Biology Study");
        researchProjects.add("Engineering Research");
        researchProjects.add("Astrobiology Experiment");
    }

    @Override
    public void performDuties() {
        System.out.println(getName() + " is beginning their research tasks.");

        // Ученый выбирает проект для исследования в зависимости от его приоритетности
        for (String project : researchProjects) {
            System.out.println(getName() + " is working on the " + project + ".");
            // Можем добавить логику для приоритетов исследований
        }
    }
}

class MedicColonist extends Colonist {
    private List<String> injuredColonists;

    public MedicColonist(String name) {
        super(name);
        injuredColonists = new ArrayList<>();
        injuredColonists.add("John Doe");
        injuredColonists.add("Jane Smith");
    }

    @Override
    public void performDuties() {
        System.out.println(getName() + " is starting their medical duties.");

        // Лечим раненых колонистов
        for (String injured : injuredColonists) {
            System.out.println(getName() + " is treating injuries of " + injured + ".");
        }

        // Также можно добавить логику для распределения медикаментов или проверки здоровья
    }
}
