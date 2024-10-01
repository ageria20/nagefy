package ageria.nagefy.dto;

import ageria.nagefy.entities.Product;
import ageria.nagefy.entities.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderDetailDTO(@NotNull User user,
                             @NotNull Product product,
                             @NotNull String paymentMethod,
                             @NotNull LocalDate date,
                             @NotNull double quantity,
                             @NotNull double total) {
}
