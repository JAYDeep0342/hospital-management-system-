package com.ips.hospitalmanagementsystem.repository;

import com.ips.hospitalmanagementsystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}