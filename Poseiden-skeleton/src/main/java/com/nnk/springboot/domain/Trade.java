package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;


@Entity
@Table(name = "Trade")
@Data
@NoArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer tradeId;
    @Size(max = 30)
    private String account;
    @Size(max = 30)
    private String type;
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private String benchmark;
    private Timestamp tradeDate;
    @Size(max = 125)
    private String security;
    @Size(max = 10)
    private String status;
    @Size(max = 125)
    private String trader;
    @Size(max = 125)
    private String book;
    @Size(max = 125)
    private String creationName;
    private Timestamp creationDate;
    @Size(max = 125)
    private String revisionName;
    private Timestamp revisionDate;
    @Size(max = 125)
    private String dealName;
    @Size(max = 125)
    private String dealType;
    @Size(max = 125)
    private String sourceListId;
    @Size(max = 125)
    private String side;

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}
