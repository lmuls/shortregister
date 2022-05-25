package com.example.shortregister_spring.model;

import javax.persistence.*;


@Entity
@Table(name="shorter")
public class Shorter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="company_name")
    private String companyName;

    public Shorter(String companyName) {
        this.companyName = companyName;
    }

    public Shorter() {
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String toString() {
        return this.companyName;
    }
}
