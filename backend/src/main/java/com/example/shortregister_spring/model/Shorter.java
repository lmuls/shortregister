package com.example.shortregister_spring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Table(name="shorter")
public class Shorter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="company_name", unique = true)
    private String companyName;

    @OneToMany(mappedBy = "shorter")
    @JsonManagedReference
    private List<ShortPosition> shortPositions;

    public Shorter(String companyName) {
        this.companyName = companyName;
    }

    public Shorter() {
    }

    @Override
    public String toString() {
        return this.companyName;
    }
}
