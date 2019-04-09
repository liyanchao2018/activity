package com.luckli.activity.deploy;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;

public class BpmnError {
	public static void main(String[] args) {
		
		//创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		//得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		//创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addClasspathResource("error/bpmn_error.bpmn");
//		builder.disableBpmnValidation();//关闭bpmn验证的话 就不会验证bpmn文件的流程画的是否正确执行；
		builder.deploy();
				
		
		//builder.disableBpmnValidation();  如果去掉这个 代表 开启bpmn验证，流程图画的不正确的在部署的时候 就会报错
		//org.activiti.engine.ActivitiException: Errors while parsing:
//		[Validation set: 'activiti-executable-process' | Problem: 'activiti-start-event-multiple-found'] : Multiple none start events are not supported - [Extra info : processDefinitionId = myProcess | processDefinitionName = My process |  | id = startevent1 |  | activityName = Start | ] ( line: 5, column: 47)
//		org.activiti.engine.ActivitiException: Errors while parsing:
//		[Validation set: 'activiti-executable-process' | Problem: 'activiti-start-event-multiple-found'] : Multiple none start events are not supported - [Extra info : processDefinitionId = myProcess | processDefinitionName = My process |  | id = startevent1 |  | activityName = Start | ] ( line: 5, column: 47)
//		[Validation set: 'activiti-executable-process' | Problem: 'activiti-start-event-multiple-found'] : Multiple none start events are not supported - [Extra info : processDefinitionId = myProcess | processDefinitionName = My process |  | id = startevent2 |  | activityName = Start | ] ( line: 7, column: 47)[Validation set: 'activiti-executable-process' | Problem: 'activiti-start-event-multiple-found'] : Multiple none start events are not supported - [Extra info : processDefinitionId = myProcess | processDefinitionName = My process |  | id = startevent2 |  | activityName = Start | ] ( line: 7, column: 47)
		
		
	}
}
