package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordDTO(@NotNull(message = "New Password required")
                                String password) {
}
