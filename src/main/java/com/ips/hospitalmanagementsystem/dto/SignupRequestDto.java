package com.ips.hospitalmanagementsystem.dto;

import com.ips.hospitalmanagementsystem.entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    private  String username;
    private  String password;
    private  String name;
    private  Set<RoleType> roles = new HashSet<>();

}
