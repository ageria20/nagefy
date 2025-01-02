package ageria.nagefy.dto;

import ageria.nagefy.entities.Admin;
import ageria.nagefy.entities.Product;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderDetailDTO(@NotNull Admin user,
                             @NotNull Product product,
                             @NotNull String paymentMethod,
                             @NotNull LocalDate date,
                             @NotNull double quantity,
                             @NotNull double total) {
}
