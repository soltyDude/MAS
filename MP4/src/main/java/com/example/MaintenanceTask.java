package com.example;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class MaintenanceTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /* ---------- class extent (unordered storage) ---------- */
    private static final List<MaintenanceTask> EXTENT = new ArrayList<>();


    public static List<MaintenanceTask> getExtent() {
        List<MaintenanceTask> copy = new ArrayList<MaintenanceTask>(EXTENT);
        copy.sort(Comparator.comparing(MaintenanceTask::getScheduledFor));
        return Collections.unmodifiableList(copy);
    }

    /* ---------- attributes ---------- */
    private final String         title;
    private LocalDateTime        scheduledFor;     // mutable for demo; edits re-ordered dynamically

    /* ---------- constructor ---------- */
    public MaintenanceTask(String title, LocalDateTime scheduledFor) {
        this.title        = Objects.requireNonNull(title,        "title null");
        this.scheduledFor = Objects.requireNonNull(scheduledFor, "date null");
        EXTENT.add(this);
    }

    /* ---------- getters / setters with validation ---------- */

    public String getTitle() {
        return title;
    }

    public LocalDateTime getScheduledFor() {
        return scheduledFor;
    }

    public void setScheduledFor(LocalDateTime newDate) {
        this.scheduledFor = Objects.requireNonNull(newDate, "date null");
    }

    /* ---------- equals / hashCode (title + scheduledFor) ---------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaintenanceTask)) return false;
        MaintenanceTask that = (MaintenanceTask) o;
        return title.equalsIgnoreCase(that.title) &&
                scheduledFor.equals(that.scheduledFor);
    }

    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode() * 31 + scheduledFor.hashCode();
    }

    @Override
    public String toString() {
        return "MaintenanceTask{" + title + " @ " + scheduledFor + '}';
    }
}
