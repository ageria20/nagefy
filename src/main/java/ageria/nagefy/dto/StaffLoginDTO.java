package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;

public record StaffLoginDTO(@NotEmpty(message = "Email  is required")
                            String email,
                            @NotEmpty(message = "Password  is required")
                            String password) {
}