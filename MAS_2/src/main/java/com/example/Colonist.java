package com.example;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Colonist {
    private final String id;
    private String name;
    private String rank;


    private final Set<Assignment> assignments = new HashSet<>();

    public Colonist(String id, String name, String rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void addAssignment(Assignment assignment) {
        if (assignment == null) return;
        if (assignments.add(assignment)) {
            assignment.setColonist(this);
        }
    }

    public void removeAssignment(Assignment assignment) {
        if (assignment == null) return;
        if (assignments.remove(assignment)) {
            assignment.setColonist(null);
        }
    }

    public Set<Assignment> getAssignments() {
        return Collections.unmodifiableSet(assignments);
    }
}
