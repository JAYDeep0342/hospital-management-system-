package com.ips.hospitalmanagementsystem.repository;

import com.ips.hospitalmanagementsystem.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}