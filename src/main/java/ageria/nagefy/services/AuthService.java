package ageria.nagefy.services;


import ageria.nagefy.dto.StaffLoginDTO;
import ageria.nagefy.dto.UserLoginDTO;
import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.User;
import ageria.nagefy.exceptions.UnauthorizedException;
import ageria.nagefy.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UsersService userService;

    @Autowired
    StaffsService staffsService;

    @Autowired
    ClientsService clientsService;


    @Autowired
    JWTTools jwtTools;

    @Autowired
    PasswordEncoder bcrypt;


    public String checkCredentialsAndGenerateToken(UserLoginDTO body){
        User found = this.userService.findFromEmail(body.email());
        System.out.println("BODY PSW: " + body.password());
        System.out.println("HASED PSW USER: "+ found.getPassword());
        if(bcrypt.matches(body.password(), found.getPassword())){
            return jwtTools.createUserToken(found);
        }
        else {
            throw new UnauthorizedException("CREDENTIALS ARE NOT VALID");
        }
    }

    public String checkCredentialsAndGenerateTokenStaff(UserLoginDTO body){
        Staff found = this.staffsService.findFromEmail(body.email());
        System.out.println("STAFF: "+ found);
        System.out.println("HASED PSW: " + found.getPassword());
        System.out.println("BODY PSW: " + body.password());
        boolean passwordMatches = bcrypt.matches(body.password(), found.getPassword());
        System.out.println("Password matches: " + passwordMatches);

        //VECCHIA PASSWORD(1234): $2a$11$iWx/swa02iTUI/PSDCIJpO6fQJUflsgr.bLyCQueU90GbXN3G4iF
        // NUOVA PASSWORD(12345): $2a$11$B3HcDMHj4J46qJyUf8NdyuRHiuKJ0MUj7gipA74yJcZCgKC1kMINy
        if(bcrypt.matches(body.password(), found.getPassword())){
            return jwtTools.createStaffToken(found);
        }
        else {
            throw new UnauthorizedException("CREDENTIALS ARE NOT VALID");
        }
    }

    public String checkCredentialsAndGenerateTokenClient(UserLoginDTO body){
        Client found = this.clientsService.findFromEmail(body.email());
        System.out.println("CLIENT: "+ found);
        System.out.println("Provided password: " + body.password()); // Password fornita
        System.out.println("Stored password: " + found.getPassword());
        if(bcrypt.matches(body.password(), found.getPassword())){
            return jwtTools.createClientToken(found);
        }
        else {
            throw new UnauthorizedException("CREDENTIALS ARE NOT VALID");
        }
    }


}
