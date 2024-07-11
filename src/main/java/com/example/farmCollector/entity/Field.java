package com.example.farmCollector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double plantingArea;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @JsonIgnore
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Crop> crops;

}

