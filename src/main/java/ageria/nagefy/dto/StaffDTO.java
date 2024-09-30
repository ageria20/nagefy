package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record StaffDTO(
        @NotEmpty(message = "Name is required")
        @Size(min = 3, max = 10, message = "Name has to be 9 or 30 characters")
        String name,
        @NotEmpty(message = "Email  is required")
        String email) {
}
