package ageria.nagefy.services;


import ageria.nagefy.dto.*;
import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.Treatment;
import ageria.nagefy.enums.Role;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.StaffRepository;
import ageria.nagefy.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.List;
import java.util.UUID;

@Service
public class StaffsService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder bcrypt;



    public Page<Staff> getAlLStaff(int pages, int size, String sortBy) {
        if (pages > 50) pages = 50;
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.staffRepository.findAll(pageable);
    }

    public Staff findFromEmail(String email){
        return this.staffRepository.findByEmail(email.toLowerCase());
    }

    public List<Staff> findFromName(String name) { return this.userRepository.findStaffsByName(name);}

    public Staff findById(UUID id){
        return this.staffRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Staff saveStaff(StaffDTO body){
        if(this.staffRepository.existsByEmail(body.email())){
            throw new BadRequestException("EMAIL ALREADY PRESENT IN DATABASE");
        }
        Staff newStaff = new Staff(
                body.name(),
                body.surname(),
                body.telephone(),
                body.email(),
                bcrypt.encode(body.password()),
                false,
                Role.EMPLOYEE,
                "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());

        return this.staffRepository.save(newStaff);
    }
    public Staff createNewStaff(NewStaffDTO body) throws MessagingException {
        if(this.staffRepository.existsByEmail(body.email())){
            throw new BadRequestException("EMAIL ALREADY PRESENT IN DATABASE");
        }
        String token = UUID.randomUUID().toString();
        Staff newStaff = new Staff(
                body.name(),
                body.surname(),
                body.telephone(),
                body.email(),
                bcrypt.encode(token),
                false,
                Role.EMPLOYEE,
                "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());

        Staff savedStaff = this.staffRepository.save(newStaff);
        this.emailService.sendEmailStaff(savedStaff.getEmail());
        return savedStaff;
    }

    public Staff findByIdAndUpdate(UUID id, StaffUpdateDTO body){
        Staff found = this.findById(id);
        found.setName(body.name());
        found.setEmail(body.email());
        return this.staffRepository.save(found);
    }
    public Staff findByIdAndUpdateForMe(UUID id, StaffDTO body){
        Staff found = this.findById(id);
        found.setName(body.name());
        found.setEmail(body.email());
        return this.staffRepository.save(found);
    }

    public Staff findByEmailAndResetPassword(String email, ChangePasswordDTO newPassword){
        Staff staff = this.findFromEmail(email);
        if (staff == null) {
            throw new NotFoundException("Email non trovata");
        }

        staff.setPassword(bcrypt.encode(newPassword.password()));
        Staff updatedStaff = this.staffRepository.save(staff);


        return updatedStaff;
    }

    public void deleteStaff(UUID id){
        Staff found = this.findById(id);
        this.staffRepository.delete(found);
    }
}
