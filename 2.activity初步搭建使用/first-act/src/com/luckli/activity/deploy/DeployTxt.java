package com.luckli.activity.deploy;

import java.io.IOException;
import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;


/**
    *   流程文件部署：部署txt文件,并输出打印部署流程文件内容;
 * @author liyanchao
 *
 */
public class DeployTxt {
	public static void main(String[] args) throws IOException {
		//创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		//得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		//创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addClasspathResource("my_text.txt");
		Deployment dep = builder.deploy();
		
		InputStream inputStream = repositoryService.getResourceAsStream(dep.getId(), "my_text.txt");
		int count = inputStream.available();
		byte[] contents = new byte[count];
		inputStream.read(contents);
		String result = new String(contents);
		//输出结果
		System.out.println(result);
		
	}
}
