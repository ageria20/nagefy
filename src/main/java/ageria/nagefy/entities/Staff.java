package ageria.nagefy.entities;


import ageria.nagefy.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("Staff")
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "username"})
public class Staff extends User {




    public Staff(String name, String surname, String telephone, String email, String password, boolean isVerified, Role role, String avatar) {
        super(name, surname, telephone, email, password, isVerified, role, avatar);
    }

    public Staff(String name, String surname, String telephone, String email, Role role, String avatar) {
        super(name, surname, telephone, email, role, avatar);
    }
}
