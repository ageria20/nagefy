package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record DiscountDTO(@NotEmpty(message = "Description  is required")
                          String description,
                          @NotEmpty(message = "Percentage  is required")
                          Double percentage,
                          @NotEmpty(message = "Duration  is required")
                          LocalDateTime duration) {
}
