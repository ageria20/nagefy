package ageria.nagefy.services;


import ageria.nagefy.dto.StaffDTO;
import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.enums.Role;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientsService {

    @Autowired
    ClientsRepository clientsRepository;


    @Autowired
    PasswordEncoder bcrypt;



    public Page<Client> getAlLClients(int pages, int size, String sortBy) {
        if (pages > 50) pages = 50;
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.clientsRepository.findAll(pageable);
    }

    public Client findFromEmail(String email){
        return this.clientsRepository.findByEmail(email);
    }

    public List<Client> findFromName(String name){ return this.clientsRepository.findByName(name);}

    public Client findById(UUID id){
        return this.clientsRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Client saveClient(StaffDTO body){
        Client newClient = new Client(
                body.name(),
                body.surname(),
                body.telephone(),
                body.email(),
                bcrypt.encode(body.password()),
                Role.USER,
                "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());

        return this.clientsRepository.save(newClient);
    }

    public Client findByIdAndUpdate(UUID id, StaffDTO body){
        Client found = this.findById(id);
        found.setName(body.name());
        found.setEmail(body.email());
        return this.clientsRepository.save(found);
    }

    public void deleteClient(UUID id){
        Client found = this.findById(id);
        this.clientsRepository.delete(found);
    }

}
