package ageria.nagefy.controllers;


import ageria.nagefy.dto.UserDTO;
import ageria.nagefy.dto.UserLoginDTO;
import ageria.nagefy.dto.UserRespDTO;
import ageria.nagefy.entities.User;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.services.AuthService;
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

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserRespDTO login(@RequestBody UserLoginDTO body){
        return new UserRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Validated UserDTO body, BindingResult validationRes){
        if(validationRes.hasErrors()) {
            String msg = validationRes.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        } else {
            return this.usersService.saveUser(body);
        }
    }

}