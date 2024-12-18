package ageria.nagefy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record StaffDTO(
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Surname is required")
        String surname,
        @NotEmpty(message = "Telephone is required")
        @Size(min = 9, max = 10, message = "Telephone has to be 9 or 30 characters")
        String telephone,
        @Email(message = "Email should be valid")
        @NotEmpty(message = "Email  is required")
        String email,
        @NotEmpty(message = "Password  is required")
        String password,
        String avatar) {
}
