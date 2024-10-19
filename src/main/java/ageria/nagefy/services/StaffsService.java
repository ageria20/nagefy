package ageria.nagefy.services;


import ageria.nagefy.dto.ClientDTO;
import ageria.nagefy.dto.NewStaffDTO;
import ageria.nagefy.dto.StaffDTO;
import ageria.nagefy.dto.UserDTO;
import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.Treatment;
import ageria.nagefy.entities.User;
import ageria.nagefy.enums.Role;
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
        Staff newStaff = new Staff(
                body.name(),
                body.surname(),
                body.telephone(),
                body.email(),
                bcrypt.encode(body.password()),
                Role.EMPLOYEE,
                "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());

        return this.staffRepository.save(newStaff);
    }
    public Staff createNewStaff(NewStaffDTO body) throws MessagingException {
        Staff newStaff = new Staff(
                body.name(),
                body.surname(),
                body.telephone(),
                body.email(),
                Role.EMPLOYEE,
                "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());

        String token = UUID.randomUUID().toString();
        newStaff.setPassword(token);
        Staff savedStaff = this.staffRepository.save(newStaff);
        this.emailService.sendEmail(savedStaff.getEmail());
        return savedStaff;
    }

    public Staff findByIdAndUpdate(UUID id, StaffDTO body){
        Staff found = this.findById(id);
        found.setName(body.name());
        found.setEmail(body.email());
        return this.staffRepository.save(found);
    }

    public Staff findByEmailAndResetPassword(String email, String newPassword){
        Staff staff = this.findFromEmail(email);
        if (staff == null) {
            throw new NotFoundException("Email non trovata");
        }

        // Aggiorno la password del cliente
        staff.setPassword(bcrypt.encode(newPassword));


        return this.staffRepository.save(staff);
    }

    public void deleteStaff(UUID id){
        Staff found = this.findById(id);
        this.staffRepository.delete(found);
    }
}
