package ageria.nagefy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record StaffDTO(
        @NotEmpty(message = "Name is required")
        String name,
        @Email(message = "Email should be valid")
        @NotEmpty(message = "Email  is required")
        String email,
        @NotEmpty(message = "Password  is required")
        String password) {
}
