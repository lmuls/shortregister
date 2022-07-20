package com.example.shortregister_spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name="short_position", uniqueConstraints = @UniqueConstraint(columnNames = {"instrument","shorter", "opened"}))
public class ShortPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "instrument")
    private Instrument instrument;

    @ManyToOne
    @JsonBackReference
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

    public ShortPosition() {}

    public ShortPosition(Instrument instrument, Shorter shorter, Date date) {
            this.instrument = instrument;
            this.shorter = shorter;
            this.opened = date;
            this.active = true;
    }

    public String getShorterCompanyName() {
        return this.shorter.getCompanyName();
    }

    public List<ShortPositionHistory> getShortPositionHistories() {
        List<ShortPositionHistory> shortPositionsHistory = this.shortPositionHistories;
        shortPositionsHistory.sort((x, y) -> y.getDate().compareTo(x.getDate()));
        return shortPositionHistories;
    }

    //    public Date getClosed() {
//        return this.closed;
//    }
//
//    public List<ShortPositionHistory> getShortPositionHistories() {
//        return this.shortPositionHistories;
//    }

    public ShortPosition close(Date date) {
        this.active = false;
        this.closed = date;
        return this;
    }

    public String toString() {
        return "(" + this.id + " " + this.instrument + " " + this.shorter + " " + this.opened + " " + this.closed+ ")";
    }
}
