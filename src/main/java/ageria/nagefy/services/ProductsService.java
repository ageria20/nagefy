package ageria.nagefy.services;

import ageria.nagefy.dto.ProductDTO;
import ageria.nagefy.entities.Product;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductsService {

    @Autowired
    ProductsRepository productsRepository;


    public Product findById(UUID id){
        return this.productsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Product saveProduct(ProductDTO body){
        Product newProduct = new Product(
                body.name(),
                body.price(),
                body.availableQuantity(),
                body.category(),
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.cigb.edu.cu%2Fcigb_history%2F2011-2%2F&psig=AOvVaw3XSaolfxDfGIu7G-3eqOke&ust=1727815549725000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPCQhdTE64gDFQAAAAAdAAAAABAJ"
        );
        return this.productsRepository.save(newProduct);
    }

    public Product findByIdAndUpdate(UUID id, ProductDTO body){
        Product found = this.findById(id);
        found.setName(body.name());
        found.setPrice(body.price());
        found.setAvailableQuantity(body.availableQuantity());
        found.setCategory(body.category());
        return this.productsRepository.save(found);
    }

    public void deleteProduct(UUID id){
        Product found = this.findById(id);
        this.productsRepository.delete(found);
    }
}
