package com.lmuls.shortregister_spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "short_position_history", uniqueConstraints = @UniqueConstraint(columnNames = {"short_position", "date"}))
public class ShortPositionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "short_position", referencedColumnName = "id")
    private ShortPosition shortPosition;

    @Column(name = "date")
    private OffsetDateTime date;

    @Column(name = "shares")
    private int shares;

    @Column(name = "short_percent")
    private double shortPercent;

    public OffsetDateTime getDate() {
        return this.date;
    }

    public ShortPositionHistory(ShortPosition shortPosition, OffsetDateTime date, int shares, double shortPercent) {
        this.shortPosition = shortPosition;
        this.date = date;
        this.shares = shares;
        this.shortPercent = shortPercent;
    }

    public String toString() {
        return this.id + " " + this.shortPosition + " " + this.date + " " + this.shares;
    }
}
