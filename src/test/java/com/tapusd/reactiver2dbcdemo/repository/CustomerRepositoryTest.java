package com.tapusd.reactiver2dbcdemo.repository;

import com.tapusd.reactiver2dbcdemo.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutionException;

@DataR2dbcTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void delete() throws ExecutionException, InterruptedException {
        Flux<Customer> customerFlux = customerRepository.findAll().flatMap(c -> customerRepository.deleteById(c.getId()))
                .thenMany(Flux.just(
                        new Customer("abc@abc.com"),
                        new Customer("xyz@xyz.com")
                ))
                .flatMap(customerRepository::save);

        StepVerifier.create(customerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
