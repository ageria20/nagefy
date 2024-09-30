package ageria.nagefy.services;

import ageria.nagefy.dto.UserDTO;
import ageria.nagefy.entities.User;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder bcrypt;



    public User findById(UUID id){
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findFromEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public User saveUser(UserDTO body){
        if(this.userRepository.existsByEmail(body.email())){
            throw new BadRequestException("EMAIL ALREADY PRESENT IN DATABASE");
        }
        User newUser = new User(
                body.name(),
                body.surname(),
                body.telephone(),
                body.email(),
                bcrypt.encode(body.password()),
                "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
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

    public void deleteUser(UUID id){
        User found = this.findById(id);
        this.userRepository.delete(found);
    }
}
