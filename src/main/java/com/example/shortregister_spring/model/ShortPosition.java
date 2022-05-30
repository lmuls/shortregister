package com.example.shortregister_spring.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="short_position")
public class ShortPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "instrument")
    private Instrument instrument;

    @ManyToOne
    @JoinColumn(name = "shorter")
    private Shorter shorter;

    @Column(name="opened")
    private Date opened;

    @Column(name="closed")
    private Date closed;

    @Column(name="active")
    private boolean active;

    @OneToMany(mappedBy = "shortPosition")
    private List<ShortPositionHistory> shortPositionHistories;

    public ShortPosition() {};
}
