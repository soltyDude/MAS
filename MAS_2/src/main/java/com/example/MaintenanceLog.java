package com.example;

import java.time.LocalDate;

public class MaintenanceLog {
    private final String id;
    private LocalDate date;
    private String description;

    private HabitatModule habitatModule;

    public MaintenanceLog(String id, LocalDate date, String description, HabitatModule habitatModule) {
        this.id = id;
        this.date = date;
        this.description = description;
        setHabitatModule(habitatModule);
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HabitatModule getHabitatModule() {
        return habitatModule;
    }

    public void setHabitatModule(HabitatModule habitatModule) {
        if (this.habitatModule != null) {
            this.habitatModule.removeMaintenanceLog(this);
        }

        this.habitatModule = habitatModule;

        if (habitatModule != null && !habitatModule.getMaintenanceLogs().contains(this)) {
            habitatModule.addMaintenanceLog(this);
        }
    }
}
