package com.ht.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations={"classpath:application-schedule.xml"})
public class ScheduleConfig {

}