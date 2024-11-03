package com.gcu.repository;

import com.gcu.model.HourSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HourSheetRepository extends JpaRepository<HourSheet, Integer> 
{
    
}
