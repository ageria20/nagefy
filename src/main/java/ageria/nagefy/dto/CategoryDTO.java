package ageria.nagefy.dto;

import jakarta.validation.constraints.NotEmpty;

public record CategoryDTO(@NotEmpty(message = "Category name is required")
                          String name) {
}
