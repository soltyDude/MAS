package com.example.cyborg;

public class Cyborg implements IColonist, IRobot {
    private final HumanPart humanPart;
    private final RobotPart robotPart;

    public Cyborg(String name, String model) {
        this.humanPart = new HumanPart(name);
        this.robotPart = new RobotPart(model);
    }

    @Override
    public void performColonistDuties() {
        humanPart.performColonistDuties();
    }

    @Override
    public void performRobotDuties() {
        robotPart.performRobotDuties();
    }

    @Override
    public String getName() {
        return humanPart.getName();
    }

    public String getModel() {
        return robotPart.getModel();
    }
}
