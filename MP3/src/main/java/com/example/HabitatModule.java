package com.example;

public class HabitatModule {
    private String id;
    private ModuleType moduleType;

    public HabitatModule(String id, ModuleType moduleType) {
        this.id = id;
        this.moduleType = moduleType;
    }

    public void operate() {
        System.out.print("Module " + id + ": ");
        moduleType.operate();
    }

    public void changeModuleType(ModuleType newType) {
        this.moduleType = newType;
        System.out.println("Module " + id + " changed to new type.");
    }
}
