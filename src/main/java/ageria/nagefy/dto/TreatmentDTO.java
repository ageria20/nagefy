package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TreatmentDTO(@NotEmpty(message = "Name is required")
                           @Size(max = 30, message = "Name has to be max to 30 characters")
                           String name,
                           @NotNull(message = "Price is required")
                           double price,
                           @NotNull(message = "Duration is required")
                           Integer duration) {
}
