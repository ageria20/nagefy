package ageria.nagefy.services;

import ageria.nagefy.dto.OrderDetailDTO;
import ageria.nagefy.entities.Admin;
import ageria.nagefy.entities.OrderDetail;
import ageria.nagefy.entities.Product;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.repositories.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class OrderDetailsService {

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    ProductsService productsService;


    public OrderDetail findById(UUID id){
        return this.orderDetailsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public OrderDetail saveOrderDetail(OrderDetailDTO body){
        Admin userFromDB = this.usersService.findById(body.user().getId());
        Product productFromDB = this.productsService.findById(body.product().getId());
        OrderDetail newOrderDetail = new OrderDetail(
                userFromDB,
                productFromDB,
                body.paymentMethod(), // creare un'entità separata(?)
                LocalDate.now(),
                body.quantity(),
                body.total()
        );
        return this.orderDetailsRepository.save(newOrderDetail);
    }

    public void deleteOrderDetail(UUID id){
        OrderDetail found = this.findById(id);
        this.orderDetailsRepository.delete(found);
    }
}
