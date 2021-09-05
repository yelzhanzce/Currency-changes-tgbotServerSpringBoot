package com.yelzhan.currencytgbot.CurrencyChanges.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "KZT")
    private double kzt_value;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "changes")
    private double changes;


}
