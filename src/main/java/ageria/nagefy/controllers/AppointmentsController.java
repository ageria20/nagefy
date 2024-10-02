package ageria.nagefy.controllers;


import ageria.nagefy.dto.AppointmentDTO;
import ageria.nagefy.entities.Appointment;
import ageria.nagefy.entities.User;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.services.AppointmentsService;
import ageria.nagefy.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentsController {

    @Autowired
    AppointmentsService appointmentsService;

    // 1. GET access only the ADMIN
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Appointment> findAll(@RequestParam(defaultValue = "0") int pages,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.appointmentsService.getAllAppointments(pages, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Appointment createAppointment(@RequestBody @Validated AppointmentDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.appointmentsService.saveAppointment(body);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Appointment updateAppointment(@PathVariable UUID id, @RequestBody @Validated AppointmentDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.appointmentsService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public void deleteAppointment(@PathVariable UUID id){
        this.appointmentsService.findByIdAndDelete(id);
    }


}
