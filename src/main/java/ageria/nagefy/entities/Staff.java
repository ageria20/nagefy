package ageria.nagefy.entities;


import ageria.nagefy.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"authorities", "enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired"})
public class Staff {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Staff(String name, String email) {
        this.name = name;
        this.email = email;
        this.role = Role.EMPLOYEE;
    }
}
