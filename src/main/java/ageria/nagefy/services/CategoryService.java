package ageria.nagefy.services;


import ageria.nagefy.dto.CategoryDTO;
import ageria.nagefy.entities.Catogory;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    CategoriesRepository categoriesRepository;


    public Catogory findById(UUID id){
        return this.categoriesRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Catogory saveCategory(CategoryDTO body){
        Catogory newCategory = new Catogory(
                body.name()
        );
        return this.categoriesRepository.save(newCategory);
    }

    public Catogory findByIdAndUpdate(UUID id, CategoryDTO body){
        Catogory found = this.findById(id);
        found.setName(body.name());
        return this.categoriesRepository.save(found);
    }
    
    public void deleteCategory(UUID id){
        Catogory found = this.findById(id);
        this.categoriesRepository.delete(found);
    }
}
