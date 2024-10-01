package ageria.nagefy.dto;

import ageria.nagefy.entities.Staff;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleDTO(@NotNull Staff staff,
                          @NotNull LocalDateTime fromTime,
                          @NotNull LocalDateTime toTime) {
}
