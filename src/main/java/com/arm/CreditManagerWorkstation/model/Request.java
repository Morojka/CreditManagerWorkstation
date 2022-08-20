package com.arm.CreditManagerWorkstation.model;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Data
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
