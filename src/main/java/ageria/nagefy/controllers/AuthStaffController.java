package ageria.nagefy.controllers;

import ageria.nagefy.dto.StaffDTO;
import ageria.nagefy.dto.StaffLoginDTO;
import ageria.nagefy.dto.StaffRespDTO;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.services.AuthService;
import ageria.nagefy.services.StaffsService;
import ageria.nagefy.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthStaffController {

    @Autowired
    AuthService authService;


    @Autowired
    StaffsService staffsService;

    @PostMapping("/staff-login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public StaffRespDTO loginUStaff(@RequestBody StaffLoginDTO body){
        return new StaffRespDTO(this.authService.checkCredentialsAndGenerateTokenStaff(body));
    }

    @PostMapping("/staff-register")
    @ResponseStatus(HttpStatus.CREATED)
    public Staff registerStaff(@RequestBody @Validated StaffDTO body, BindingResult validationRes){
        if(validationRes.hasErrors()) {
            String msg = validationRes.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        } else {
            return this.staffsService.saveStaff(body);
        }
    }
}
