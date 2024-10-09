package ageria.nagefy.entities;

import ageria.nagefy.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("Client")
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "username"})
public class Client extends User {




    public Client(String name, String surname, String telephone, String email, String password, Role role, String avatar) {
        super(name, surname, telephone, email, password, role, avatar);

    }

    public Client(String name, String surname, String telephone, String email, Role role) {
        super(name, surname, telephone, email, role);
    }

    @Override
    public String toString() {
        return "Client{}";
    }
}
