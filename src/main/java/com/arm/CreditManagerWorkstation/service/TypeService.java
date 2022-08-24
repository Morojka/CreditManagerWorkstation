package com.arm.CreditManagerWorkstation.service;

import com.arm.CreditManagerWorkstation.model.Type;
import com.arm.CreditManagerWorkstation.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {

    @Autowired
    TypeRepository typeRepository;

    public List<Type> getTypesByType(String type) {
        return typeRepository.findByType(type);
    }

}
