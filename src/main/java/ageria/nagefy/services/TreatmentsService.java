package ageria.nagefy.services;


import ageria.nagefy.dto.TreatmentDTO;
import ageria.nagefy.entities.Treatment;
import ageria.nagefy.entities.User;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.TreatmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TreatmentsService {

    @Autowired
    TreatmentsRepository treatmentsRepository;


    public Page<Treatment> getAlLTreatments(int pages, int size, String sortBy) {
        if (pages > 50) pages = 50;
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.treatmentsRepository.findAll(pageable);
    }

    public Treatment findById(UUID id){
        return this.treatmentsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public List<Treatment> findTreatmentFromName(String name){
        return this.treatmentsRepository.findTreatmentsByName(name);
    }

    public Treatment saveTreatment(TreatmentDTO body){
        Treatment newTreatment = new Treatment(
                body.name(),
                body.price(),
                body.duration()

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
