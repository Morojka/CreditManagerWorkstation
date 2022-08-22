package com.arm.CreditManagerWorkstation.controller;

import com.arm.CreditManagerWorkstation.model.Type;
import com.arm.CreditManagerWorkstation.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeController {

    @Autowired
    TypeRepository repository;

    @GetMapping("/types/{type}")
    public ResponseEntity<List<Type>> getTypesByType(@PathVariable String type) {
        return new ResponseEntity<List<Type>>(repository.findByType(type), HttpStatus.OK);
    }
}
