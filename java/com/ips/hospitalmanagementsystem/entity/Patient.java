package com.ips.hospitalmanagementsystem.entity;

import com.ips.hospitalmanagementsystem.entity.type.Bloodgrouptype;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString(exclude = {"appointments", "insurance"})
@Entity
@Getter
@Setter
@Table(
        name = "patient100",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "birth_date"}),
                @UniqueConstraint(columnNames = "email")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false, unique = true)
    private String email;

    //@Column(nullable = false)
    private String gender;
    @OneToOne
    @MapsId
    private User user ;
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private Bloodgrouptype bloodGroup;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "patient_insurance_id")
    private Insurance insurance;

    @OneToMany(mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();
}
