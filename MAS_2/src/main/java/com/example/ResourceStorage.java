package com.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResourceStorage {
    private final String id;

    private HabitatModule habitatModule;

    private final Map<String, Resource> resources = new HashMap<>();

    public ResourceStorage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public HabitatModule getHabitatModule() {
        return habitatModule;
    }

    public void setHabitatModule(HabitatModule habitatModule) {
        if (this.habitatModule != null) {
            this.habitatModule.removeResourceStorage(this);
        }
        this.habitatModule = habitatModule;
        if (habitatModule != null && !habitatModule.getResourceStorages().contains(this)) {
            habitatModule.addResourceStorage(this);
        }
    }

    public Map<String, Resource> getResources() {
        return Collections.unmodifiableMap(resources);
    }

    public void addResource(Resource resource) {
        if (resource == null) return;
        resources.put(resource.getResourceType(), resource);
        if (resource.getResourceStorage() != this) {
            resource.setResourceStorage(this);
        }
    }

    public void removeResource(String resourceType) {
        Resource resource = resources.remove(resourceType);
        if (resource != null) {
            resource.setResourceStorage(null);
        }
    }

    public Resource getResource(String resourceType) {
        return resources.get(resourceType);
    }
}

