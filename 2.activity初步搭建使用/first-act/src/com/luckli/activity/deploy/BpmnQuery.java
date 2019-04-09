package com.luckli.activity.deploy;

import java.io.IOException;
import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;


/**
 * 获取部署的流程文件，并打印出来    
 * @author liyanchao
 *
 */
public class BpmnQuery {
	public static void main(String[] args) throws IOException {
		//创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		//得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		//创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addClasspathResource("bjTalentServiceCenter.bpmn");
		Deployment dep = builder.deploy();
		
		//查询流程定义实体
		ProcessDefinition def = repositoryService.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
		
		//获取部署的流程文件，并打印出来
		InputStream inputStream = repositoryService.getProcessModel(def.getId());
		int count = inputStream.available();
		byte[] contents = new byte[count];
		inputStream.read(contents);
		String result = new String(contents);
		//输出结果
		System.out.println(result);
		engine.close();
	}
}
