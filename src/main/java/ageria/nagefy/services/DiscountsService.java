package ageria.nagefy.services;


import ageria.nagefy.dto.DiscountDTO;
import ageria.nagefy.entities.Discount;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.DiscountsRepoditory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiscountsService {

    @Autowired
    DiscountsRepoditory discountsRepoditory;


    public Page<Discount> getAlLDiscount(int pages, int size, String sortBy) {
        if (pages > 50) pages = 50;
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.discountsRepoditory.findAll(pageable);
    }


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
