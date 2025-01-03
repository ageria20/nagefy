package ageria.nagefy.controllers;

import ageria.nagefy.dto.CashDTO;
import ageria.nagefy.entities.Appointment;
import ageria.nagefy.entities.Cash;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.services.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cash")
public class CashController {

    @Autowired
    CashService cashService;

    // 1. GET access only the ADMIN
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Cash> findAll(@RequestParam(defaultValue = "0") int pages,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return this.cashService.getAllCashes(pages, size, sortBy);
    }

    @GetMapping("/report")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Cash> getFilteredCash(
            @RequestParam(required = false)LocalDate startDate,
            @RequestParam(required = false)LocalDate endDate,
            @RequestParam(required = false)String paymentMethod,
            @RequestParam(required = false)UUID staffIf
            ){
        return this.cashService.getFilteredCash(startDate, endDate, paymentMethod, staffIf);
    }




    // POST CREATING CASH
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Cash createCash(@RequestBody CashDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.cashService.createCash(body);
    }


}
