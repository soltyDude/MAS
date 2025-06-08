package com.example;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HabitatModule {
    private final String id;
    private String type;
    private String status;

    private final Set<Assignment> assignments = new HashSet<>();

    private final Set<ResourceStorage> resourceStorages = new HashSet<>();

    public HabitatModule(String id, String type, String status) {
        this.id = id;
        this.type = type;
        this.status = status;
    }

    private final Set<MaintenanceLog> maintenanceLogs = new HashSet<>();

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
            assignment.setHabitatModule(this);
        }
    }

    public void removeAssignment(Assignment assignment) {
        if (assignment == null) return;
        if (assignments.remove(assignment)) {
            assignment.setHabitatModule(null);
        }
    }

    public Set<Assignment> getAssignments() {
        return Collections.unmodifiableSet(assignments);
    }

    public void addResourceStorage(ResourceStorage storage) {
        if (storage == null) return;
        if (resourceStorages.add(storage)) {
            storage.setHabitatModule(this);  // Устанавливаем обратную связь
        }
    }

    public void removeResourceStorage(ResourceStorage storage) {
        if (storage == null) return;
        if (resourceStorages.remove(storage)) {
            storage.setHabitatModule(null);  // Убираем обратную связь
        }
    }

    public Set<ResourceStorage> getResourceStorages() {
        return Collections.unmodifiableSet(resourceStorages);
    }
    public void addMaintenanceLog(MaintenanceLog maintenanceLog) {
        if (maintenanceLog == null) return;
        if (maintenanceLogs.add(maintenanceLog)) {
            maintenanceLog.setHabitatModule(this);  // Устанавливаем обратную связь
        }
    }

    public void removeMaintenanceLog(MaintenanceLog maintenanceLog) {
        if (maintenanceLog == null) return;
        if (maintenanceLogs.remove(maintenanceLog)) {
            maintenanceLog.setHabitatModule(null);  // Убираем обратную связь
        }
    }

    public void removeAllMaintenanceLogs() {
        for (MaintenanceLog log : new HashSet<>(maintenanceLogs)) {
            removeMaintenanceLog(log);  // Удаляем каждый лог
        }
    }

    public Set<MaintenanceLog> getMaintenanceLogs() {
        return Collections.unmodifiableSet(maintenanceLogs);
    }
}
