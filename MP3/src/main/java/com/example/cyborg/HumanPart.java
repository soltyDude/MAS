package com.example.cyborg;

public class HumanPart implements IColonist {
    private final String name;

    public HumanPart(String name) {
        this.name = name;
    }

    @Override
    public void performColonistDuties() {
        System.out.println(name + " (human part) is planting crops and repairing equipment.");
    }

    @Override
    public String getName() {
        return name;
    }
}
