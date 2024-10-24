package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FreeSlotDTO(@NotNull(message = "Start Time is required")
                          LocalDateTime startTime,
                          @NotNull(message = "End Time is required")
                          LocalDateTime endTime) {
}
