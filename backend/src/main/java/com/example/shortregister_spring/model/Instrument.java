package com.example.shortregister_spring.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="instrument")
public class Instrument {
    @Id
    private String isin;

    @Column(name="issuer_name")
    private String issuerName;

    @OneToMany(mappedBy = "instrument")
    private List<ShortPosition> shortPositions;

    public Instrument(String isin, String name) {
        this.isin = isin;
        this.issuerName = name;
    }

    public Instrument() {

    }

    public List<ShortPosition> getShortPositions() {
        List<ShortPosition> shortPositions = this.shortPositions;
        shortPositions.sort((x ,y) -> y.getOpened().compareTo(x.getOpened()));
        return shortPositions;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public String getIsin() {
        return isin;
    }

    @Override
    public String toString() {
        return this.issuerName;
    }
}
