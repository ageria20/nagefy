package ageria.nagefy.entities;

import ageria.nagefy.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("admins")
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "username"})
public class Admin extends User{

    public Admin(String name, String surname, String telephone, String email, String password, boolean isVerified, Role role, String avatar) {
        super(name, surname, telephone, email, password, isVerified, role, avatar);

    }

    public Admin(String name, String surname, String telephone, String email, Role role, String avatar) {
        super(name, surname, telephone, email, role, avatar);
    }


}
