package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TreatmentDTO(@NotEmpty(message = "Name is required")
                           @Size(max = 30, message = "Name has to be max to 30 characters")
                           String name,
                           @NotEmpty(message = "Price is required")
                           Long price,
                           @NotEmpty(message = "Duration is required")
                           LocalDateTime duration) {
}
