package ageria.nagefy.dto;

import ageria.nagefy.entities.Discount;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.Treatment;
import ageria.nagefy.entities.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentDTO(@NotNull(message = "User id is required")
                             User user,
                             @NotNull(message = "Treatment id is required")
                             Treatment treatment,
                             @NotNull(message = "Staff id is required")
                             Staff staffMember,
                             @NotNull(message = "Payment emthod is required")
                             String paymentMethod,
                             @NotNull(message = "Start Time is required")
                             LocalDateTime startDateTime,
                             @NotNull(message = "End Time is required")
                             LocalDateTime endDateTime,
                             @NotNull(message = "Cancelled is required")
                             boolean cancelled,
                             String cancelledReason,
                             Discount discount,
                             @NotNull(message = "Total is required")
                             double total
                             ) {
}
