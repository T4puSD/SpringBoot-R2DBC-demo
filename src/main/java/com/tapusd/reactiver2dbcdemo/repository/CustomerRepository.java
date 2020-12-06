package com.tapusd.reactiver2dbcdemo.repository;

import com.tapusd.reactiver2dbcdemo.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer,Long> {

    Mono<Customer> findByEmail(String email);
}
