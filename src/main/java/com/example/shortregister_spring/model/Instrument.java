package com.example.shortregister_spring.model;

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
