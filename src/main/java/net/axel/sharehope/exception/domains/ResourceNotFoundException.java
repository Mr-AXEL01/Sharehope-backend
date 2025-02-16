package net.axel.sharehope.exception.domains;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entityName, Object id) {
        super(entityName + " not found with ID : " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}