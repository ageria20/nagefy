package ageria.nagefy.services;


import ageria.nagefy.dto.TreatmentDTO;
import ageria.nagefy.entities.Treatment;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.TreatmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TreatmentsService {

    @Autowired
    TreatmentsRepository treatmentsRepository;



    public Treatment findById(UUID id){
        return this.treatmentsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Treatment saveTreatment(TreatmentDTO body){
        Treatment newTreatment = new Treatment(
                body.name(),
                body.duration(),
                body.price()
        );
        return this.treatmentsRepository.save(newTreatment);
    }

    public Treatment findByIdAndUpdate(UUID id, TreatmentDTO body){
        Treatment found = this.findById(id);
        found.setName(body.name());
        found.setPrice(body.price());
        found.setDuration(body.duration());
        return this.treatmentsRepository.save(found);
    }

    public void deleteTreatment(UUID id){
        Treatment found = this.findById(id);
        this.treatmentsRepository.delete(found);
    }
}
