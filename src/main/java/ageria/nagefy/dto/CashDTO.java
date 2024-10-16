package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;

public record CashDTO(@NotEmpty(message = "Payment Method  is required")
                      String paymentMethod,
                      @NotEmpty(message = "Appointment  is required")
                      String appointment,
                      Double total) {
}
