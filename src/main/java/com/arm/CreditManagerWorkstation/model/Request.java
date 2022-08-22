package com.arm.CreditManagerWorkstation.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String first_name;
    @NotNull
    private String last_name;
    private String patronymic;
    @NotNull
    private String passport;
    @ManyToOne
    @JoinColumn(name = "marital_status_id")
    private Type marital_status;
    @NotNull
    private String address;
    @NotNull
    private String phone_number;
    @NotNull
    private String employment_status;
    @Min(1)
    private Integer wish_credit_amount;

}
