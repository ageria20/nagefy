package ageria.nagefy.services;

import ageria.nagefy.dto.UserDTO;
import ageria.nagefy.entities.User;
import ageria.nagefy.exceptions.BadRequestException;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;



    public User findById(UUID id){
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User saveUser(UserDTO body){
        if(this.userRepository.existsByEmail(body.email())){
            throw new BadRequestException("EMAIL ALREADY PRESENT IN DATABASE");
        }
        User newUser = new User(
                body.name(),
                body.surname(),
                body.
        );
    }
}
