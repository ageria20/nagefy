package ageria.nagefy.services;


import ageria.nagefy.dto.StaffDTO;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.UUID;

@Service
public class StaffsService {

    @Autowired
    StaffRepository staffRepository;


    public Staff findById(UUID id){
        return this.staffRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Staff saveStaff(StaffDTO body){
        Staff newStaff = new Staff(
                body.name(),
                body.email()
        );
        return this.staffRepository.save(newStaff);
    }

    public Staff findByIdAndUpdate(UUID id, StaffDTO body){
        Staff found = this.findById(id);
        found.setName(body.name());
        found.setEmail(body.email());
        return this.staffRepository.save(found);
    }
}
