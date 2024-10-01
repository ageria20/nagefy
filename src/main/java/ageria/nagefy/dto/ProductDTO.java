package ageria.nagefy.dto;

import ageria.nagefy.entities.Catogory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.Category;

public record ProductDTO(@NotEmpty(message = "Name is required")
                         @Size(max = 30, message = "Name has to be max to 30 characters")
                         String name,
                         @NotNull(message = "Price is required")
                         Double price,
                         @NotNull(message = "Quantity is required")
                         Long availableQuantity,
                         @NotNull(message = "Category is required")
                         Catogory category) {
}
