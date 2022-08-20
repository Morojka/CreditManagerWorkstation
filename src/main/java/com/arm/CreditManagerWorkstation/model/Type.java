package com.arm.CreditManagerWorkstation.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
}
