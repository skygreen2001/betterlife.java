package com.zyp.bb.respository;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zyp.bb.domain.Customer;

@RepositoryRestResource
@Lazy
public interface CustomerRespository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByName(@Param("name") String name);
}
