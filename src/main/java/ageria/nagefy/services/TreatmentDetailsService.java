package ageria.nagefy.services;

import ageria.nagefy.dto.TreatmentScheduleDTO;
import ageria.nagefy.entities.TreatmentDetail;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.TreatmentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TreatmentDetailsService {


    @Autowired
    TreatmentDetailsRepository treatmentScheduleRepository;



    public TreatmentDetail findById(UUID id){
        return this.treatmentScheduleRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public TreatmentDetail saveTreatmentschedule(TreatmentScheduleDTO body){
        TreatmentDetail newTreatmentSchedule = new TreatmentDetail(
                body.treatment(),
                body.appointment()
        );
        return this.treatmentScheduleRepository.save(newTreatmentSchedule);
    }

    public void deleteTreatmentSchedule(UUID id){
        TreatmentDetail found = this.findById(id);
        this.treatmentScheduleRepository.delete(found);
    }
}
