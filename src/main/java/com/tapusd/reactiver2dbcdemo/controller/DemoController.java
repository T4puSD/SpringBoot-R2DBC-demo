package com.tapusd.reactiver2dbcdemo.controller;

import com.tapusd.reactiver2dbcdemo.domain.Customer;
import com.tapusd.reactiver2dbcdemo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/customers")
    public Mono<Customer> saveCustomer(@RequestParam(name = "email") String email) {
        return customerRepository.save(new Customer(email));
    }

    @GetMapping("/customers")
    public Flux<Customer> getAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{email}")
    public Mono<Customer> findByEmail(@PathVariable String email) {
        return customerRepository.findByEmail(email);
    }

    /**
     * @param customerId
     * @param email
     * @return mono of customer after updating in the database. if not found then save it as a new record
     */
    @PutMapping("/customers/{customerId}/{email}")
    public Mono<Customer> updateCustomer(@PathVariable(name = "customerId", required = true) Long customerId, @PathVariable(name = "email", required = true) String email) {
        return customerRepository.findById(customerId)
                .map(c -> {
                    c.setEmail(email);
                    return c;
                }).or(Mono.just(new Customer(email)))
                .flatMap(c -> customerRepository.save(c));
    }

    @DeleteMapping("/customers/{customerId}")
    public Mono<Void> deleteCustomer(@PathVariable(name = "customerId", required = true) Long id) {
        // until and unless an publisher data is consumed by consumer; database action is not performed
        return customerRepository.deleteById(id);
//        customerRepository.deleteById(id)
//                .subscribe(null,null,()-> System.out.println("Deletion is completed!!"));
    }


}
