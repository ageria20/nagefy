package ageria.nagefy.services;


import ageria.nagefy.dto.CashDTO;
import ageria.nagefy.entities.Appointment;
import ageria.nagefy.entities.Cash;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.CashCriteriaRepository;
import ageria.nagefy.repositories.CashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CashService {


    @Autowired
    CashRepository cashRepository;

    @Autowired
    AppointmentsService appointmentsService;

    @Autowired
    CashCriteriaRepository cashCriteriaRepository;


    public Page<Cash> getAllCashes(int pages, int size, String sortBy) {
        if (pages > 50) pages = 50;
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.cashRepository.findAll(pageable);
    }

    public Cash findFromId(UUID id){
        return  this.cashRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public List<Cash> findFromPaymentMethod(String paymentMethod){
        return this.cashRepository.findByPaymentMethod(paymentMethod);
    }

    public List<Cash> getFilteredCash(LocalDate startDate, LocalDate endTime, String paymentMethod, UUID staffId){
        return this.cashCriteriaRepository.findWithFilters(startDate, endTime, paymentMethod, staffId);
    }

    public Cash createCash(CashDTO body){
        Appointment appointmentFromDB = this.appointmentsService.findById(UUID.fromString(body.appointment()));


        Cash newCash = new Cash(
                appointmentFromDB,
                body.paymentMethod(),
                ZonedDateTime.now(ZoneId.of("Europe/Rome")),
                body.total()
        );

        return this.cashRepository.save(newCash);

    }
}

