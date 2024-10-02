package ageria.nagefy.services;


import ageria.nagefy.dto.StaffLoginDTO;
import ageria.nagefy.dto.UserLoginDTO;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.User;
import ageria.nagefy.exceptions.UnauthorizedException;
import ageria.nagefy.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UsersService userService;

    @Autowired
    StaffsService staffsService;

    @Autowired
    JWTTools jwtTools;

    @Autowired
    PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateTokenUser(UserLoginDTO body){
        User found = this.userService.findFromEmail(body.email());
        if(bcrypt.matches(body.password(), found.getPassword())){
            return jwtTools.createUserToken(found);
        }
        else {
            throw new UnauthorizedException("CREDENTIALS ARE NOT VALID");
        }
    }

    public String checkCredentialsAndGenerateTokenStaff(StaffLoginDTO body){
        Staff found = this.staffsService.findFromEmail(body.email());
        if(bcrypt.matches(body.password(), found.getPassword())){
            return jwtTools.createStaffToken(found);
        }
        else {
            throw new UnauthorizedException("CREDENTIALS ARE NOT VALID");
        }
    }
}
