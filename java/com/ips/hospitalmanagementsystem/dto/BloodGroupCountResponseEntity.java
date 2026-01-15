package com.ips.hospitalmanagementsystem.dto;

import com.ips.hospitalmanagementsystem.entity.type.Bloodgrouptype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodGroupCountResponseEntity {
    private Bloodgrouptype bloodgrouptype;
    private  Long count;
}
