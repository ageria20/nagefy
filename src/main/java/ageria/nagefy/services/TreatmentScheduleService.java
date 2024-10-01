package ageria.nagefy.services;

import ageria.nagefy.dto.TreatmentScheduleDTO;
import ageria.nagefy.entities.TreatmentSchedule;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.TreatmentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TreatmentScheduleService {


    @Autowired
    TreatmentScheduleRepository treatmentScheduleRepository;



    public TreatmentSchedule findById(UUID id){
        return this.treatmentScheduleRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public TreatmentSchedule saveTreatmentschedule(TreatmentScheduleDTO body){
        TreatmentSchedule newTreatmentSchedule = new TreatmentSchedule(
                body.treatment(),
                body.appointment()
        );
        return this.treatmentScheduleRepository.save(newTreatmentSchedule);
    }

    public void deleteTreatmentSchedule(UUID id){
        TreatmentSchedule found = this.findById(id);
        this.treatmentScheduleRepository.delete(found);
    }
}
