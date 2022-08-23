package com.arm.CreditManagerWorkstation.model;


import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
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

    @OneToOne(mappedBy = "request")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Solution solution;

    public String getFullName() {
        return this.last_name + " " + this.first_name + " " + this.patronymic;
    }

}
