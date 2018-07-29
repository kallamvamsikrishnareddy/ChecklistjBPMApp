package com.vamsi.controller;

import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartProcess {
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.vamsi");
	KieSession ksession = context.getBean(KieSession.class,"getWeatherInfo");
	//ksession.startProcess("com.jBPM.bpmn.checklist");
	//context.close();
}