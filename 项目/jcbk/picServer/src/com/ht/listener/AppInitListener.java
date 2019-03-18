package com.ht.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public class AppInitListener implements ApplicationRunner {

	private Logger logger = LoggerFactory.getLogger(AppInitListener.class);
	
//    @Autowired
//    private SysConfigInfoSchedule sysConfigInfoSchedule;
    
	@Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
		
    }
}