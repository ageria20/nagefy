package ageria.nagefy.services;


import ageria.nagefy.dto.DiscountDTO;
import ageria.nagefy.entities.Discount;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.DiscountsRepoditory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiscountsService {

    @Autowired
    DiscountsRepoditory discountsRepoditory;


    public Discount findById(UUID id){
        return this.discountsRepoditory.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Discount saveDiscount(DiscountDTO body){
        Discount newDiscount = new Discount(
                body.description(),
                body.duration(),
                body.percentage()
        );
        return this.discountsRepoditory.save(newDiscount);
    }

    public Discount findByIdAndUpdate(UUID id, DiscountDTO body){
        Discount found = this.findById(id);
        found.setDescription(body.description());
        found.setDuration(body.duration());
        found.setPercentage(body.percentage());
        return this.discountsRepoditory.save(found);
    }

    public void deleteDiscount(UUID id){
        Discount found = this.findById(id);
        this.discountsRepoditory.save(found);
    }
}
