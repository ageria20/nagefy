package ageria.nagefy.dto;

import ageria.nagefy.entities.Discount;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.Treatment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentDTO(@NotEmpty(message = "User id is required")
                             String user,
                             @NotNull(message = "Treatments are required")
                             List<Treatment> treatments,
                             @NotEmpty(message = "Staff id is required")
                             String staff,
                             @NotNull(message = "Start Time is required")
                             LocalDateTime startTime,
                             LocalDateTime endTime
                             ) {
}
