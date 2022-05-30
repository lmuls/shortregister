package com.example.shortregister_spring.model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="shorter")
public class Shorter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="company_name", unique = true)
    private String companyName;

    @OneToMany(mappedBy = "shorter")
    private List<ShortPosition> shortPositions;

    public Shorter(String companyName) {
        this.companyName = companyName;
    }

    public Shorter() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.companyName;
    }
}
