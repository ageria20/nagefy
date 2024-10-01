package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record StaffDTO(
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Email  is required")
        String email) {
}
