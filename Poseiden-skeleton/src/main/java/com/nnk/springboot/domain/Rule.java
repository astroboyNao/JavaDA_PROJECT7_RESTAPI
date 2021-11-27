package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "Rule")
@Data
@NoArgsConstructor
public class Rule {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Size(max = 125)
    private String name;
    @Size(max = 125)
    private String description;
    @Size(max = 125)
    private String json;
    @Size(max = 512)
    private String template;
    @Size(max = 125)
    private String sqlStr;
    @Size(max = 125)
    private String sqlPart;

    public Rule(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
