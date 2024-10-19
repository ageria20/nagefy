package ageria.nagefy.controllers;


import ageria.nagefy.dto.*;
import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.User;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.services.AuthService;
import ageria.nagefy.services.ClientsService;
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
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UsersService usersService;

    @Autowired
    StaffsService staffsService;

    @Autowired
    ClientsService clientsService;


    @PostMapping("/staff-register")
    @ResponseStatus(HttpStatus.CREATED)
    public Staff registerUser(@RequestBody @Validated StaffDTO body, BindingResult validationRes){
        if(validationRes.hasErrors()) {
            String msg = validationRes.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        } else {
            return this.staffsService.saveStaff(body);
        }
    }
    @PostMapping("/client-register")
    @ResponseStatus(HttpStatus.CREATED)
    public Client registerClient(@RequestBody @Validated StaffDTO body, BindingResult validationRes){
        if(validationRes.hasErrors()) {
            String msg = validationRes.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        } else {
            return this.clientsService.saveClient(body);
        }
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserRespDTO loginAdmin(@RequestBody UserLoginDTO body){
        return new UserRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    /*@PostMapping("/staff-login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public StaffRespDTO loginStaff(@RequestBody StaffLoginDTO body){
        return new StaffRespDTO(this.authService.checkCredentialsAndGenerateTokenStaff(body));
    }*/

    /*@PostMapping("/client-login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public StaffRespDTO loginClient(@RequestBody StaffLoginDTO body){
        return new StaffRespDTO(this.authService.checkCredentialsAndGenerateTokenClient(body));
    }*/

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody @Validated UserDTO body, BindingResult validationRes){
        if(validationRes.hasErrors()) {
            String msg = validationRes.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        } else {
            return this.usersService.saveUser(body);
        }
    }






}
