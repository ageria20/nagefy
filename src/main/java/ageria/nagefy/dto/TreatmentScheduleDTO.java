package ageria.nagefy.dto;

import ageria.nagefy.entities.Appointment;
import ageria.nagefy.entities.Treatment;
import jakarta.validation.constraints.NotNull;

public record TreatmentScheduleDTO(@NotNull Treatment treatment, @NotNull Appointment appointment) {
}
