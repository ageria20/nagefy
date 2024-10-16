package ageria.nagefy.dto;

import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.Treatment;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentUpdateStaffDTO(@NotNull(message = "Treatments are required")
                                        List<Treatment> treatments,
                                        @NotNull(message = "Staff is required")
                                        String staff,
                                        @NotNull(message = "Start Time is required")
                                        LocalDateTime startTime,
                                        LocalDateTime endTime
){
}
