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

    public List<FreeSlotDTO> getFreeSlots(
            @RequestParam StaffIdDTO staff,
            @RequestParam String date, // Parametro date (solo la data)
            @RequestParam(required = false, defaultValue = "12") int hoursOpen // durata giornata
    ) {
        // Imposta l'orario predefinito alle 08:30 per la data fornita
        LocalDateTime startOfDay = LocalDateTime.parse(date + "T08:30:00");

        // Calcola la fine della giornata
        LocalDateTime endOfDay = startOfDay.plusHours(hoursOpen).minusSeconds(1);

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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
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
