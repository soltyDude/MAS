package com.example;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Task {
    private final String id;
    private String description;
    private String status;

    private final Set<Assignment> assignments = new HashSet<>();

    public Task(String id, String description, String status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addAssignment(Assignment assignment) {
        if (assignment == null) return;
        if (assignments.add(assignment)) {
            assignment.setTask(this);
        }
    }

    public void removeAssignment(Assignment assignment) {
        if (assignment == null) return;
        if (assignments.remove(assignment)) {
            assignment.setTask(null);
        }
    }

    public Set<Assignment> getAssignments() {
        return Collections.unmodifiableSet(assignments);
    }
}
