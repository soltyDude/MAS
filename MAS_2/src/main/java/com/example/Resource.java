package com.example;

public class Resource {
    private final String id;
    private String resourceType;  // Квалификатор
    private int quantity;

    private ResourceStorage resourceStorage;

    public Resource(String id, String resourceType, int quantity) {
        this.id = id;
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        if (!this.resourceType.equals(resourceType)) {
            if (resourceStorage != null) {
                resourceStorage.removeResource(this.resourceType);
            }
            this.resourceType = resourceType;
            if (resourceStorage != null) {
                resourceStorage.addResource(this);
            }
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ResourceStorage getResourceStorage() {
        return resourceStorage;
    }

    public void setResourceStorage(ResourceStorage resourceStorage) {
        if (this.resourceStorage != null) {
            this.resourceStorage.removeResource(this.resourceType);
        }
        this.resourceStorage = resourceStorage;
        if (resourceStorage != null && !resourceStorage.getResources().containsKey(this.resourceType)) {
            resourceStorage.addResource(this);
        }
    }
}
