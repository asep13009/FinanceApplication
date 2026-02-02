package org.asep.finance.handling.exception;

public class ResourceTypeNotFoundException extends RuntimeException {

    public ResourceTypeNotFoundException(String resourceType) {
        super("Unsupported resourceType: " + resourceType);
    }
}
