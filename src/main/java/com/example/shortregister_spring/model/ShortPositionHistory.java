package com.example.shortregister_spring.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "short_position_history")
public class ShortPositionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "short_position", referencedColumnName = "id")
    private ShortPosition shortPosition;

    @Column(name = "date")
    private Date date;

    @Column(name = "shares")
    private int shares;

    @Column(name = "short_percent")
    private double shortPercent;

    public ShortPositionHistory() {}
}
