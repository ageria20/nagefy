package ageria.nagefy.dto;

import ageria.nagefy.entities.Discount;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.Treatment;
import ageria.nagefy.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentDTO(@NotNull(message = "User id is required")
                             User user,
                             @NotNull(message = "Treatments are required")
                             List<Treatment> treatments,
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
