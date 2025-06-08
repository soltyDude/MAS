package com.example.cyborg;

public class RobotPart implements IRobot {
    private final String model;

    public RobotPart(String model) {
        this.model = model;
    }

    @Override
    public void performRobotDuties() {
        System.out.println("Robot part (" + model + ") is scanning environment and analyzing data.");
    }

    public String getModel() {
        return model;
    }
}
