package com.vamsi;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jbpm.test.JBPMHelper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.task.TaskService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * @author Vamsi.Kallam
 * this is the main class to create Spring boot application
 * this is will also create kie container
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class ProcessMain {
	
	RuntimeEngine engine;
	public static void main(String[] args) {
		SpringApplication.run(ProcessMain.class, args);
	}
	
	@Bean
	public RuntimeEngine runtimeEngine(){
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase");

		RuntimeManager manager = createRuntimeManager(kbase);
		this.engine = manager.getRuntimeEngine(null);
		
/*		ksession.startProcess("com.jBPM.bpmn.checklist");

		// let john execute Task 1
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		TaskSummary task = list.get(0);
		System.out.println("John is executing task " + task.getName());
		taskService.start(task.getId(), "john");
		taskService.complete(task.getId(), "john", null);

		// let mary execute Task 2
		list = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
		task = list.get(0);
		System.out.println("Mary is executing task " + task.getName());
		taskService.start(task.getId(), "mary");
		taskService.complete(task.getId(), "mary", null);*/

/*		manager.disposeRuntimeEngine(engine);
		System.exit(0);*/
		return this.engine;
	}
	
	@Bean
	public KieSession kieSession(){		
		KieSession ksession = this.engine.getKieSession();
		return ksession;		
	}
	
	@Bean
	public TaskService taskService(){
		TaskService taskService = this.engine.getTaskService();
		return taskService;
	}
	private static RuntimeManager createRuntimeManager(KieBase kbase) {
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get()
			.newDefaultBuilder().entityManagerFactory(emf)
			.knowledgeBase(kbase);
		return RuntimeManagerFactory.Factory.get()
			.newSingletonRuntimeManager(builder.get(), "com.jBPM:example:1.0");
	}

}