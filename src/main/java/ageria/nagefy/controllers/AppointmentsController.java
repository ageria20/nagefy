package ageria.nagefy.controllers;


import ageria.nagefy.dto.AppointmentDTO;
import ageria.nagefy.dto.AppointmentUpdateStaffDTO;
import ageria.nagefy.dto.FreeSlotDTO;
import ageria.nagefy.dto.StaffIdDTO;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentsController {

    @Autowired
    AppointmentsService appointmentsService;

    // 1. GET access only the ADMIN
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Page<Appointment> findAll(@RequestParam(defaultValue = "0") int pages,
                                     @RequestParam(defaultValue = "1000") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.appointmentsService.getAllAppointments(pages, size, sortBy);
    }

    @GetMapping("/free-slots")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<FreeSlotDTO> getFreeSlots(@RequestParam StaffIdDTO staff,
                                          @RequestParam String date) {
        LocalDateTime startOfDay = LocalDateTime.parse(date);
        LocalDateTime endOfDay = startOfDay.plusHours(8).minusSeconds(1);

        // Ottieni gli slot liberi dallo staff per il giorno specifico
        return appointmentsService.getFreeSlotsForStaff(staff, startOfDay, endOfDay);
    }


    @GetMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Appointment getFromId(@PathVariable UUID appointmentId){
        return this.appointmentsService.findById(appointmentId);
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
    public Appointment updateAppointment(@PathVariable UUID id, @RequestBody @Validated AppointmentUpdateStaffDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.appointmentsService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable UUID id){
        this.appointmentsService.findByIdAndDelete(id);
    }


}
