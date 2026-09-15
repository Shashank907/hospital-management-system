package com.shashankcdr.hospitalSystem.insurance.repository;

import com.shashankcdr.hospitalSystem.insurance.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance,Long> {

}
