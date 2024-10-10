package ageria.nagefy.controllers;


import ageria.nagefy.dto.*;
import ageria.nagefy.entities.*;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.repositories.StaffRepository;
import ageria.nagefy.services.AppointmentsService;
import ageria.nagefy.services.AuthService;
import ageria.nagefy.services.StaffsService;
import ageria.nagefy.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staffs")
public class StaffsController {

    @Autowired
    StaffsService staffsService;

    @Autowired
    AppointmentsService appointmentsService;



    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public Page<Staff> findAll(@RequestParam(defaultValue = "0") int pages,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return this.staffsService.getAlLStaff(pages, size, sortBy);
    }


    @GetMapping("/search")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public List<Staff> getStaffByName(@RequestParam String name){
        return this.staffsService.findFromName(name);
    }
    // POST STAFF
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Staff createStaffMember(@RequestBody @Validated StaffDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.staffsService.saveStaff(body);
    }
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Staff createNewStaff(@RequestBody @Validated NewStaffDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.staffsService.createNewStaff(body);
    }


    // PUT STAFF
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Staff updateStaffMember(@RequestBody @Validated StaffDTO body, @PathVariable UUID id, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.staffsService.findByIdAndUpdate(id, body);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteStaffMember(@PathVariable UUID id){
        this.staffsService.deleteStaff(id);
    }

    // GET Appointments by staff
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Page<Appointment> getAllStaffAppointment(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "15") int size,
                                                    @RequestParam(defaultValue = "staff") String sortBy,
                                                    @AuthenticationPrincipal Staff currStaffAuthenticated){
        return this.appointmentsService.getAppointmentByStaff(page, size, sortBy, currStaffAuthenticated.getId());
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment createAppointmentUser(@AuthenticationPrincipal Staff currStaffAuth, @RequestBody @Validated AppointmentDTO body, BindingResult validation){
        if(validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.appointmentsService.saveAppointmentStaff(currStaffAuth.getId(), body);
    }
    @PutMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Staff updateStaffMember(@AuthenticationPrincipal Staff currStaffAuthenticated, @RequestBody StaffDTO body, BindingResult validation){
        if (validation.hasErrors()){
            String msg = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.staffsService.findByIdAndUpdate(currStaffAuthenticated.getId(), body);
    }
}
