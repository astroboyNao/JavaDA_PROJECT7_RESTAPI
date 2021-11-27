package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@Data
@NoArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Size(max = 30)
    private String account;
    @Size(max = 30)
    private String type;
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
    @Size(max = 125)
    private String benchmark;
    private Timestamp bidListDate;
    @Size(max = 125)
    private String commentary;
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

    public Bid(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
