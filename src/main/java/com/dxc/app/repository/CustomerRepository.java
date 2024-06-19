package com.dxc.app.repository;

import com.dxc.app.model.CustomerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity,String> {
    Iterable<CustomerEntity> findAll(Sort sort);
}
