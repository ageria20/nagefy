package ageria.nagefy.controllers;

import ageria.nagefy.dto.DiscountDTO;
import ageria.nagefy.entities.Discount;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.services.DiscountsService;
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
public class DiscountsController {

    @Autowired
    DiscountsService discountsService;


    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Discount> findAll(@RequestParam(defaultValue = "0") int pages,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.discountsService.getAlLDiscount(pages, size, sortBy);
    }

    // POST DISCOUNT
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Discount createDiscount(@RequestBody @Validated DiscountDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.discountsService.saveDiscount(body);
    }

    //PUT DISCOUNT
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Discount updateDiscount(@PathVariable UUID id, @RequestBody @Validated DiscountDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.discountsService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDiscount(@PathVariable UUID id){
        this.discountsService.deleteDiscount(id);
    }
}
