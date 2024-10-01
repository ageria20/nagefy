package ageria.nagefy.services;

import ageria.nagefy.dto.ScheduleDTO;
import ageria.nagefy.entities.Schedule;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SchedulesService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    StaffsService staffsService;


    public Schedule findById(UUID id){
        return this.scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Schedule saveSchedule(ScheduleDTO body){
        Staff staffMember = this.staffsService.findById(body.staff().getId());
        Schedule newSchedule = new Schedule(
                staffMember,
                body.fromTime(),
                body.toTime()
        );
        return this.scheduleRepository.save(newSchedule);
    }

    public void deleteSchedule(UUID id){
        Schedule found = this.findById(id);
        this.scheduleRepository.delete(found);
    }
}
