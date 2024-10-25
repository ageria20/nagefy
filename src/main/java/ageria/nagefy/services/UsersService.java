package ageria.nagefy.services;

import ageria.nagefy.dto.UserDTO;
import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.User;
import ageria.nagefy.enums.Role;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder bcrypt;


    public Page<User> getAllUsers(int pages, int size, String sortBy) {
        if (pages > 50) pages = 50;
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.userRepository.findAll(pageable);
    }

    public User findById(UUID id){
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findFromEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public User saveUser(UserDTO body) throws MessagingException {
        if(this.userRepository.existsByEmail(body.email())){
            throw new BadRequestException("EMAIL ALREADY PRESENT IN DATABASE");
        }
        User newUser = new User(
                body.name(),
                body.surname(),
                body.telephone(),
                body.email(),
                bcrypt.encode(body.password()),
                false,
                Role.ADMIN,
                "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());

        User savedUser = this.userRepository.save(newUser);
        this.emailService.sendEmailVerificationAdmin(savedUser.getEmail());
        return this.userRepository.save(newUser);
    }

    public User findByIdAndUpdate(UUID id, UserDTO body){
        User found = this.findById(id);
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setTelephone(body.telephone());
        found.setEmail(body.email());
        return this.userRepository.save(found);
    }


    public User findByEmailAndVerify(String email){
        User user = this.findFromEmail(email);
        if (user == null) {
            throw new NotFoundException("Email non trovata");
        }

        user.setVerified(true);
        User updatedClient = this.userRepository.save(user);

        return updatedClient;
    }


    public void deleteUser(UUID id){
        User found = this.findById(id);
        this.userRepository.delete(found);
    }
}
