package ageria.nagefy.controllers;


import ageria.nagefy.dto.AppointmentDTO;
import ageria.nagefy.dto.ChangePasswordDTO;
import ageria.nagefy.dto.ClientDTO;
import ageria.nagefy.dto.StaffDTO;
import ageria.nagefy.entities.Appointment;
import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.repositories.UserRepository;
import ageria.nagefy.services.AppointmentsService;
import ageria.nagefy.services.ClientsService;
import ageria.nagefy.services.StaffsService;
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
@RequestMapping("/clients")
public class ClientsController {


    @Autowired
    ClientsService clientsService;



    @Autowired
    AppointmentsService appointmentsService;



    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Page<Client> findAll(@RequestParam(defaultValue = "0") int pages,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.clientsService.getAlLClients(pages, size, sortBy);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Client> getClientsByName(@RequestParam(required = false)  String name){
        return this.clientsService.findFromName(name);
    }

    // POST STAFF
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Client createStaffMember(@RequestBody @Validated StaffDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.clientsService.saveClient(body);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Client createNewClient(@RequestBody @Validated ClientDTO body, BindingResult validation) throws MessagingException {
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.clientsService.createNewClientWithPassword(body);
    }

    @PutMapping("/reset")
    public Client resetPassword(@RequestParam("email") String email, @RequestBody ChangePasswordDTO newPassword) {
        return this.clientsService.findByEmailAndResetPassword(email, newPassword);
    }



    // PUT STAFF
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Client updateStaffMember(@RequestBody @Validated StaffDTO body, @PathVariable UUID id, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.clientsService.findByIdAndUpdate(id, body);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteStaffMember(@PathVariable UUID id){
        this.clientsService.deleteClient(id);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Client findMe(@AuthenticationPrincipal Client currClientAuth){
        return currClientAuth;
    }

    // GET Appointments by staff
    @GetMapping("/me/appointments")
    @ResponseStatus(HttpStatus.OK)
    public Page<Appointment> getAllClientAppointment(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "15") int size,
                                                    @RequestParam(defaultValue = "user") String sortBy,
                                                    @AuthenticationPrincipal Client currClientAuthenticated){
        return this.appointmentsService.getAppointmentByClient(page, size, sortBy, currClientAuthenticated.getId());
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment createAppointmentUser(@AuthenticationPrincipal Client currClientAuth, @RequestBody @Validated AppointmentDTO body, BindingResult validation){
        if(validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.appointmentsService.saveAppointmentStaff(currClientAuth.getId(), body);
    }
    @PutMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Client updateStaffMember(@AuthenticationPrincipal Staff currStaffAuthenticated, @RequestBody StaffDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.clientsService.findByIdAndUpdate(currStaffAuthenticated.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Client currClientAuth){
        this.clientsService.deleteClient(currClientAuth.getId());
    }
}
