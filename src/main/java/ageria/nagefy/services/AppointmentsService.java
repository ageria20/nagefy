package ageria.nagefy.services;


import ageria.nagefy.dto.AppointmentDTO;
import ageria.nagefy.dto.AppointmentUpdateStaffDTO;
import ageria.nagefy.entities.*;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    DiscountsService discountsService;


    public Page<Appointment> getAllAppointments(int pages, int size, String sortBy) {
        if (pages > 50) pages = 50;
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.appointmentsRepository.findAll(pageable);
    }

    public List<Appointment> getAppointments(UUID id){
        return this.appointmentsRepository.findByUserId(id);
    }

    public Page<Appointment> getAppointmentByStaff(int pages, int size, String sortBy, UUID id){
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.appointmentsRepository.findByStaffId(pageable, id);
    }


    public Appointment findById(UUID id){
        return this.appointmentsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Appointment saveAppointment( AppointmentDTO body){
        Staff staffFromDB = this.staffsService.findById(UUID.fromString(body.staff()));
        User userFromDB = this.usersService.findById(UUID.fromString(body.user()));
        List<Treatment> treatmentsFromDB = body.treatments().stream().map(treatment -> this.treatmentsService.findById(UUID.fromString(String.valueOf(treatment.getId())))).collect(Collectors.toList());
        LocalDateTime startAppointment = body.startTime();
        LocalDateTime endAppointment = startAppointment.plusMinutes(treatmentsFromDB.stream().mapToLong(duration -> duration.getDuration()).sum());
        if(this.appointmentsRepository.existsByStaffAndStartTime(staffFromDB, LocalDateTime.from(body.startTime()))){
            throw new BadRequestException("STAFF MEMBER ALREADY OCCUPIED");
        }
        Appointment newAppointment = new Appointment(
                userFromDB,
                treatmentsFromDB,
                staffFromDB,
                startAppointment,
                endAppointment
        );
        return this.appointmentsRepository.save(newAppointment);
    }

    public Appointment saveAppointmentUser(UUID userId, AppointmentDTO body){
        Staff staffFromDB = this.staffsService.findById(UUID.fromString(body.staff()));
        User userFromDB = this.usersService.findById(UUID.fromString(body.user()));
        List<Treatment> treatmentsFromDB = body.treatments().stream().map(treatment -> this.treatmentsService.findById(UUID.fromString(String.valueOf(treatment.getId())))).collect(Collectors.toList());
        LocalDateTime startAppointment = body.startTime();
        LocalDateTime endAppointment = startAppointment.plusMinutes(treatmentsFromDB.stream().mapToLong(duration -> duration.getDuration()).sum());
        if(this.appointmentsRepository.existsByStaffAndStartTime(staffFromDB, LocalDateTime.from(body.startTime()))){
            throw new BadRequestException("STAFF MEMBER ALREADY OCCUPIED");
        }
        Appointment newAppointment = new Appointment(
                userFromDB,
                treatmentsFromDB,
                staffFromDB,
                startAppointment,
                endAppointment
        );
        return this.appointmentsRepository.save(newAppointment);
    }
    public Appointment saveAppointmentStaff(UUID staffId, AppointmentDTO body){
        Staff staffFromDB = this.staffsService.findById(UUID.fromString(body.staff()));
        User userFromDB = this.usersService.findById(UUID.fromString(body.user()));
        List<Treatment> treatmentsFromDB = body.treatments().stream().map(treatment -> this.treatmentsService.findById(UUID.fromString(String.valueOf(treatment.getId())))).collect(Collectors.toList());
        LocalDateTime startAppointment = body.startTime();
        LocalDateTime endAppointment = startAppointment.plusMinutes(treatmentsFromDB.stream().mapToLong(duration -> duration.getDuration()).sum());
        if(this.appointmentsRepository.existsByStaffAndStartTime(staffFromDB, LocalDateTime.from(body.startTime()))){
            throw new BadRequestException("STAFF MEMBER ALREADY OCCUPIED");
        }
        Appointment newAppointment = new Appointment(
                userFromDB,
                treatmentsFromDB,
                staffFromDB,
                startAppointment,
                endAppointment
        );
        return this.appointmentsRepository.save(newAppointment);
    }

    public Appointment findByIdAndUpdate(UUID id, AppointmentUpdateStaffDTO body){
        Appointment appointmentFromDB = this.findById(id);
        Staff staffFromDB = this.staffsService.findById(UUID.fromString(body.staff()));
        LocalDateTime startAppointment = body.startTime();
        LocalDateTime endAppointment = startAppointment.plusMinutes(body.treatments().stream().mapToLong(duration -> duration.getDuration()).sum());
        appointmentFromDB.setTreatmentsList(body.treatments());
        appointmentFromDB.setStaff(staffFromDB);
        appointmentFromDB.setStartTime(startAppointment);
        appointmentFromDB.setEndTime(endAppointment);
        return this.appointmentsRepository.save(appointmentFromDB);
    }

    public void findByIdAndDelete(UUID id){
        Appointment found = this.findById(id);
        this.appointmentsRepository.delete(found);
    }
}
