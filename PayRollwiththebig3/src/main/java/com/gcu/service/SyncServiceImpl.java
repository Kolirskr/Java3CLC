package com.gcu.service;

import java.util.logging.Logger;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.gcu.repository.HourSheetRepository;
import com.gcu.repository.UserRepository;

@Service
public class SyncServiceImpl implements SyncService, ApplicationListener<ContextRefreshedEvent> 
{
    private final UserRepository userRepository;
    private final HourSheetRepository hourSheetRepository;
    private static final Logger logger = Logger.getLogger(SyncServiceImpl.class.getName());

    public SyncServiceImpl(UserRepository userRepository, HourSheetRepository hourSheetRepository) 
    {
        this.userRepository = userRepository;
        this.hourSheetRepository = hourSheetRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) 
    {
        logger.info("Application initialized. Database is ready for use.");
    }

    @Override
    public void syncDatabaseToJson() 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'syncDatabaseToJson'");
    }

    @Override
    public void syncJsonToDatabase() 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'syncJsonToDatabase'");
    }
}