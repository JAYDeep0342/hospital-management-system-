package com.ips.hospitalmanagementsystem.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OnBoardDoctorRequestDto {

    private Long userId;
    private String name;
    private String specialization;
}
