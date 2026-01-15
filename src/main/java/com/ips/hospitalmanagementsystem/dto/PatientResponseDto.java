package com.ips.hospitalmanagementsystem.dto;
import lombok.Data;
import com.ips.hospitalmanagementsystem.entity.type.Bloodgrouptype;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private Bloodgrouptype bloodGroup;
}
