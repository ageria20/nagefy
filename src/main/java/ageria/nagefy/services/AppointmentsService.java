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
import java.util.List;
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


    public Appointment findById(UUID id){
        return this.appointmentsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Appointment saveAppointment(AppointmentDTO body){
        Staff staffFromDB = this.staffsService.findById(body.staffMember().getId());
        User userFromDB = this.usersService.findById(body.user().getId());
        Discount discountFromDB = this.discountsService.findById(body.discount().getId());
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
                discountFromDB,
                treatmentFromDB.getPrice()

        );
        return this.appointmentsRepository.save(newAppointment);
    }

    public void findByIdAndDelete(UUID id){
        Appointment found = this.findById(id);
        this.appointmentsRepository.delete(found);
    }
}
