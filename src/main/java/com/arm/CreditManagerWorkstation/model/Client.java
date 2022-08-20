package com.arm.CreditManagerWorkstation.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private String patronymic;
    private String passport;
    @ManyToOne
    @JoinColumn(name = "marital_status_id")
    private Type marital_status;
    private String address;
    private String phone_number;
    private String employment_status;
    private Integer wish_credit_amount;
}