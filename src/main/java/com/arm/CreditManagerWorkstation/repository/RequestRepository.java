package com.arm.CreditManagerWorkstation.repository;

import com.arm.CreditManagerWorkstation.model.Request;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

}