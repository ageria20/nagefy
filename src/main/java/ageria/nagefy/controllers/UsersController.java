package ageria.nagefy.controllers;

import ageria.nagefy.dto.AppointmentDTO;
import ageria.nagefy.dto.UserDTO;
import ageria.nagefy.dto.UserLoginDTO;
import ageria.nagefy.dto.UserRespDTO;
import ageria.nagefy.entities.Appointment;
import ageria.nagefy.entities.User;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.services.AppointmentsService;
import ageria.nagefy.services.AuthService;
import ageria.nagefy.services.UsersService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    UsersService userService;

    @Autowired
    AppointmentsService appointmentsService;





    // 1. GET access only the ADMIN
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public Page<User> findAll(@RequestParam(defaultValue = "0") int pages,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return this.userService.getAllUsers(pages, size, sortBy);
    }

    // POST USER
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public User creaUser(@RequestBody @Validated UserDTO body, BindingResult validation) throws MessagingException {
        if(validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.userService.saveUser(body);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public User getUserMe(@AuthenticationPrincipal User currAuthUser){
        return this.userService.findById(currAuthUser.getId());
    }

    // PUT USER
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public User updateUser(@PathVariable UUID id, @RequestBody @Validated UserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.userService.findByIdAndUpdate(id, body);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUserProfile(@AuthenticationPrincipal User currAuthUser, @RequestBody UserDTO body ){
        return this.userService.findByIdAndUpdate(currAuthUser.getId(), body);
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable UUID id){
            this.userService.deleteUser(id);
            return "USER DELETED CORRECTLY";
    }

    // GET ME endpoint
    @GetMapping("/me/appointments")
    public List<Appointment> getUserBooking(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return this.appointmentsService.getAppointments((currentAuthenticatedUser.getId()));
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    // TODO DA MODIFICARE IN PAGE
    public Appointment createAppointmentUser(@AuthenticationPrincipal User currUserAuth, @RequestBody @Validated AppointmentDTO body, BindingResult validation){
        if(validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.appointmentsService.saveAppointmentUser(currUserAuth.getId(), body);
    }

    @PutMapping("/me/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment createAppointment(@RequestBody @Validated AppointmentDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.appointmentsService.saveAppointment(body);
    }


    @DeleteMapping("/me")
    public void deleteProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.userService.deleteUser(currentAuthenticatedUser.getId());
    }
}
