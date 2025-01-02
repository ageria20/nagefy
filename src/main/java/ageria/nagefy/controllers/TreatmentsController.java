package ageria.nagefy.controllers;

import ageria.nagefy.dto.TreatmentDTO;
import ageria.nagefy.entities.Treatment;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.services.TreatmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/treatments")
public class TreatmentsController {

    @Autowired
    TreatmentsService treatmentsService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)

    public Page<Treatment> findAll(@RequestParam(defaultValue = "0") int pages,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        return this.treatmentsService.getAlLTreatments(pages, size, sortBy);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Treatment> getTreatmentByName(@RequestParam String name){
        return this.treatmentsService.findTreatmentFromName(name);
    }
    //POST TREATMENT
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Treatment createTreatment(@RequestBody @Validated TreatmentDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.treatmentsService.saveTreatment(body);
    }

    //PUT TREATMENT
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Treatment updateTreatment(@PathVariable UUID id, @RequestBody @Validated TreatmentDTO body, BindingResult validation){
        if(validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.treatmentsService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTreatment(@PathVariable UUID id){
        this.treatmentsService.deleteTreatment(id);
    }
}
