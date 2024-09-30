package ageria.nagefy.exceptions;


import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Element with id: " + id + " NOT FOUND");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
