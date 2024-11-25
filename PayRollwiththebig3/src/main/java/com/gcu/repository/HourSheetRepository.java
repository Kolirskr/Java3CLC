package com.gcu.repository;

import com.gcu.model.HourSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HourSheetRepository extends JpaRepository<HourSheet, Integer> 
{
    
}
