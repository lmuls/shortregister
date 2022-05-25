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

    @Column(name="opened")
    private Date opened;

    @Column(name="closed")
    private Date closed;

    @Column(name="active")
    private boolean active;
}
