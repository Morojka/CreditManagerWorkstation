package com.arm.CreditManagerWorkstation.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity
@Table(name = "solutions")
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @OneToOne
    @JoinColumn(name = "request_id")
    private Request request;
    private Boolean approved;
    @Min(1)
    @Max(366)
    private Integer days_amount;
    @Min(1)
    private Integer credit_amount;
    private Date sign_date;
    private Boolean signed;
}
