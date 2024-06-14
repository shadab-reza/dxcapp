package com.dxc.app.repository;

import com.dxc.app.model.CustomerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity,String> {
}
