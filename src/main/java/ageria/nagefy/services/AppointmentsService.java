package ageria.nagefy.services;


import ageria.nagefy.dto.AppointmentDTO;
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
        Staff staffFromDB = this.staffsService.findById(body.staffMember().getId());
        User userFromDB = this.usersService.findById(body.user().getId());
        Discount discountFromDB = this.discountsService.findById(body.discount().getId());
        List<Treatment> treatmentsFromDB = body.treatments().stream().map(treatment -> this.treatmentsService.findById(UUID.fromString(String.valueOf(treatment.getId())))).collect(Collectors.toList());
        LocalDateTime startAppointment = body.startDateTime();
        LocalDateTime endAppointment = startAppointment.plusMinutes(treatmentsFromDB.stream().mapToLong(duration -> duration.getDuration()).sum());
        if(this.appointmentsRepository.existsByStaffAndStartTime(staffFromDB, LocalDateTime.from(body.startDateTime()))){
            throw new BadRequestException("STAFF MEMBER ALREADY OCCUPIED");
        }
        Appointment newAppointment = new Appointment(
                userFromDB,
                treatmentsFromDB,
                staffFromDB,
                body.paymentMethod(),
                startAppointment,
                endAppointment,
                body.cancelled(),
                treatmentsFromDB.stream().mapToDouble(price -> price.getPrice()).sum()

        );
        return this.appointmentsRepository.save(newAppointment);
    }

    public Appointment saveAppointmentUser(UUID userId, AppointmentDTO body){
        Staff staffFromDB = this.staffsService.findById(body.staffMember().getId());
        User userFromDB = this.usersService.findById(userId);
        Discount discountFromDB = this.discountsService.findById(body.discount().getId());
        List<Treatment> treatmentsFromDB = body.treatments().stream().map(treatment -> this.treatmentsService.findById(UUID.fromString(String.valueOf(treatment.getId())))).collect(Collectors.toList());
        LocalDateTime startAppointment = body.startDateTime();
        LocalDateTime endAppointment = startAppointment.plusMinutes(treatmentsFromDB.stream().mapToLong(duration -> duration.getDuration()).sum());
        if(this.appointmentsRepository.existsByStaffAndStartTime(staffFromDB, LocalDateTime.from(body.startDateTime()))){
            throw new BadRequestException("STAFF MEMBER ALREADY OCCUPIED");
        }
        Appointment newAppointment = new Appointment(
                userFromDB,
                treatmentsFromDB,
                staffFromDB,
                body.paymentMethod(),
                startAppointment,
                endAppointment,
                body.cancelled(),
                treatmentsFromDB.stream().mapToDouble(price -> price.getPrice()).sum()

        );
        return this.appointmentsRepository.save(newAppointment);
    }
    public Appointment saveAppointmentStaff(UUID staffId, AppointmentDTO body){
        Staff staffFromDB = this.staffsService.findById(staffId);
        User userFromDB = this.usersService.findById(body.user().getId());
        Discount discountFromDB = this.discountsService.findById(body.discount().getId());
        List<Treatment> treatmentsFromDB = body.treatments().stream().map(treatment -> this.treatmentsService.findById(UUID.fromString(String.valueOf(treatment.getId())))).collect(Collectors.toList());
        LocalDateTime startAppointment = body.startDateTime();
        LocalDateTime endAppointment = startAppointment.plusMinutes(treatmentsFromDB.stream().mapToLong(duration -> duration.getDuration()).sum());
        if(this.appointmentsRepository.existsByStaffAndStartTime(staffFromDB, LocalDateTime.from(body.startDateTime()))){
            throw new BadRequestException("STAFF MEMBER ALREADY OCCUPIED");
        }
        Appointment newAppointment = new Appointment(
                userFromDB,
                treatmentsFromDB,
                staffFromDB,
                body.paymentMethod(),
                startAppointment,
                endAppointment,
                body.cancelled(),
                treatmentsFromDB.stream().mapToDouble(price -> price.getPrice()).sum()

        );
        return this.appointmentsRepository.save(newAppointment);
    }

    public Appointment findByIdAndUpdate(UUID id, AppointmentDTO body){
        Appointment appointmentFromDB = this.findById(id);
        LocalDateTime startAppointment = body.startDateTime();
        LocalDateTime endAppointment = startAppointment.plusMinutes(body.treatments().stream().mapToLong(duration -> duration.getDuration()).sum());
        appointmentFromDB.setUser(body.user());
        appointmentFromDB.setTreatmentsList(body.treatments());
        appointmentFromDB.setStaff(body.staffMember());
        appointmentFromDB.setPaymentMethod(body.paymentMethod());
        appointmentFromDB.setStartTime(startAppointment);
        appointmentFromDB.setEndTime(endAppointment);
        appointmentFromDB.setTotal(body.treatments().stream().mapToDouble(price -> price.getPrice()).sum());
        return this.appointmentsRepository.save(appointmentFromDB);
    }

    public void findByIdAndDelete(UUID id){
        Appointment found = this.findById(id);
        this.appointmentsRepository.delete(found);
    }
}
