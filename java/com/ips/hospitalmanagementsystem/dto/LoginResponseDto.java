package com.ips.hospitalmanagementsystem.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    String Jwt ;
    Long userid ;
}
