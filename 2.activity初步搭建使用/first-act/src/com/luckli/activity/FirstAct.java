package com.luckli.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class FirstAct {

	public static void main(String[] args) {
		//处理引擎 生成 activity所需要用的 数据库表
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		//存储服务
		RepositoryService repositoryService = engine.getRepositoryService();
		//运行时服务
		RuntimeService runtimeservice = engine.getRuntimeService();
		//任务服务
		TaskService taskService = engine.getTaskService();
		//部署流程
		repositoryService.createDeployment().addClasspathResource("first.bpmn").deploy();
		//这个实例 是多例的 可以多用户来启动流程实例 进行流程开启
		ProcessInstance processInstance = runtimeservice.startProcessInstanceByKey("myProcess");
		
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("当前流程节点："+task.getName());
		taskService.complete(task.getId());//提交第一个流程节点，那么再次查询这个流程节点 就应该流转到 下一节点名词 usertask
		
		//再次查询
		task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("当前流程节点："+task.getName());
		taskService.complete(task.getId());//经理结束审核流程任务
		
		
		task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("流程结束："+task);
		
		engine.close();
		System.out.println("000");
		
	}
}
