package ageria.nagefy.services;


import ageria.nagefy.dto.AppointmentDTO;
import ageria.nagefy.dto.AppointmentUpdateStaffDTO;
import ageria.nagefy.dto.FreeSlotDTO;
import ageria.nagefy.dto.StaffIdDTO;
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
import java.util.*;
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

    public Page<Appointment> getAppointmentByClient(int pages, int size, String sortBy, UUID id){
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.appointmentsRepository.findByUserId(pageable, id);
    }


    public Appointment findById(UUID id){
        return this.appointmentsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Appointment saveAppointment( AppointmentDTO body){
        Staff staffFromDB = this.staffsService.findById(UUID.fromString(body.staff()));
        Admin userFromDB = this.usersService.findById(UUID.fromString(body.user()));
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
                endAppointment,
                false
        );
        return this.appointmentsRepository.save(newAppointment);
    }

    public Appointment saveAppointmentUser(UUID userId, AppointmentDTO body){
        Staff staffFromDB = this.staffsService.findById(UUID.fromString(body.staff()));
        Admin userFromDB = this.usersService.findById(UUID.fromString(body.user()));
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
                endAppointment,
                false
        );
        return this.appointmentsRepository.save(newAppointment);
    }
    public Appointment saveAppointmentStaff(UUID staffId, AppointmentDTO body){
        Staff staffFromDB = this.staffsService.findById(UUID.fromString(body.staff()));
        Admin userFromDB = this.usersService.findById(UUID.fromString(body.user()));
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
                endAppointment,
                false
        );
        return this.appointmentsRepository.save(newAppointment);
    }


    public List<FreeSlotDTO> getFreeSlotsForStaff(StaffIdDTO staff, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        Staff found = staffsService.findById(UUID.fromString(staff.staff()));

        // Recupera tutte le prenotazioni dello staff nel giorno specificato
        List<Appointment> appointments = appointmentsRepository.findByStaffAndDate(found, startOfDay, endOfDay);

        // Ordina le prenotazioni per orario di inizio
        List<Appointment> sortedAppointments = appointments.stream()
                .sorted(Comparator.comparing(Appointment::getStartTime))
                .toList();

        // Lista per memorizzare gli slot liberi divisi per ogni ora
        List<FreeSlotDTO> freeHourlySlots = new ArrayList<>();


        LocalDateTime current = startOfDay;

        while (current.isBefore(endOfDay)) {
            // Calcola l'orario di fine per ogni slot orario (ogni intervallo dura al massimo un'ora)
            LocalDateTime slotStart = current;
            LocalDateTime slotEnd = slotStart.plusHours(1).isAfter(endOfDay) ? endOfDay : slotStart.plusHours(1);

            // Verifica se lo slot orario si sovrappone con qualche appuntamento
            boolean isOverlapping = sortedAppointments.stream()
                    .anyMatch(appointment ->
                            (appointment.getStartTime().isBefore(slotEnd) && appointment.getEndTime().isAfter(slotStart))
                    );

            // Se non c'è sovrapposizione, aggiungi lo slot orario come slot libero
            if (!isOverlapping) {
                freeHourlySlots.add(new FreeSlotDTO(slotStart, slotEnd));
            }

            // Avanza di un'ora
            current = slotEnd;
        }

        return freeHourlySlots;
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
        appointmentFromDB.setPayed(body.isPayed());
        return this.appointmentsRepository.save(appointmentFromDB);
    }

    public void findByIdAndDelete(UUID id){
        Appointment found = this.findById(id);
        this.appointmentsRepository.delete(found);
    }
}
