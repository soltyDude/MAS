package com.example;

import java.time.LocalDate;

public class Assignment {
    private final String id;
    private String role;
    private LocalDate startDate;

    private Colonist colonist;
    private Task task;

    private HabitatModule habitatModule;

    public Assignment(String id, String role, LocalDate startDate, Colonist colonist, Task task, HabitatModule habitatModule) {
        this.id = id;
        this.role = role;
        this.startDate = startDate;
        setColonist(colonist);
        setTask(task);
        setHabitatModule(habitatModule);
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Colonist getColonist() {
        return colonist;
    }

    public void setColonist(Colonist colonist) {
        if (this.colonist != null) {
            this.colonist.removeAssignment(this);
        }
        this.colonist = colonist;
        if (colonist != null && !colonist.getAssignments().contains(this)) {
            colonist.addAssignment(this);
        }
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        if (this.task != null) {
            this.task.removeAssignment(this);
        }
        this.task = task;
        if (task != null && !task.getAssignments().contains(this)) {
            task.addAssignment(this);
        }
    }

    public HabitatModule getHabitatModule() {
        return habitatModule;
    }

    public void setHabitatModule(HabitatModule habitatModule) {
        if (this.habitatModule != null) {
            this.habitatModule.removeAssignment(this);
        }
        this.habitatModule = habitatModule;
        if (habitatModule != null && !habitatModule.getAssignments().contains(this)) {
            habitatModule.addAssignment(this);
        }
    }
}
