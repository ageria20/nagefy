package ageria.nagefy.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserDTO(@NotEmpty(message = "Name is required")
                      @Size(min = 2, max = 30, message = "Name has to be from 3 to 30 characters")
                      String name,
                      @NotEmpty(message = "Surname is required")
                      String surname,
                      @NotEmpty(message = "Telephone is required")
                      @Size(min = 9, max = 10, message = "Telephone has to be 9 or 30 characters")
                      String telephone,
                      @NotEmpty(message = "Email  is required")
                      String email,
                      @NotEmpty(message = "Password  is required")
                      String password){
}
