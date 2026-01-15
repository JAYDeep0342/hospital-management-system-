package com.ips.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private  String specialization;
    @Column(nullable = false,length = 50)
    private String name;
    @Column(unique = true,length = 100)
    private  String email;
@OneToOne
@MapsId
     private  User user ;
    @ManyToMany (mappedBy = "doctors")
    private Set<Department> departments =new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

}
