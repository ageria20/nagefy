package ageria.nagefy.services;


import ageria.nagefy.dto.AppointmentDTO;
import ageria.nagefy.entities.*;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppointmentsService {

    @Autowired
    AppointmentsRepository appointmentsRepository;

    @Autowired
    StaffsService staffsService;

    @Autowired
    UsersService usersService;

    @Autowired
    TreatmentsService treatmentsService;


    public Appointment findById(UUID id){
        return this.appointmentsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Appointment saveAppointment(AppointmentDTO body){
        Staff staffFromDB = this.staffsService.findById(body.staffMember().getId());
        User userFromDB = this.usersService.findById(body.user().getId());
        Treatment treatmentFromDB = this.treatmentsService.findById(body.treatment().getId());
        if(this.appointmentsRepository.existsByStaffAndStartTime(staffFromDB, LocalDateTime.from(body.startDateTime()))){
            throw new BadRequestException("STAFF MEMBER ALREADY OCCUPIED");
        }
        Appointment newAppointment = new Appointment(
                userFromDB,
                treatmentFromDB,
                staffFromDB,
                body.paymentMethod(),
                body.startDateTime(),
                body.endDateTime(),
                body.cancelled(),

        );
    }
}
