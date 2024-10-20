package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;

public record ChangePasswordDTO(@NotEmpty(message = "New Password required")
                                String confirmedPassword) {
}
