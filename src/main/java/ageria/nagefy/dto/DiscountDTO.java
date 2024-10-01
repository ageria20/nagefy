package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DiscountDTO(@NotEmpty(message = "Description  is required")
                          String description,
                          @NotNull(message = "Percentage  is required")
                          Double percentage,
                          @NotNull(message = "Duration  is required")
                          LocalDateTime duration) {
}
