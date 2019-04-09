package com.luckli.activity.deploy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;

/**
 *      根据部署的流程文件 生成流程图片
 * @author liyanchao
 *
 */
public class ImageQuery {

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
		InputStream inputStream = repositoryService.getProcessDiagram(def.getId());
		//将输入流转换为图片对象
		BufferedImage image = ImageIO.read(inputStream);
		
		File file = new File("resource/bjTalentServiceCenter.png"); 
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		ImageIO.write(image, "png", fos);
		fos.close();
		inputStream.close();
		//查看结果
		System.out.println("查看路径下的图片： resource/bjTalentServiceCenter.png  ");
	}
	
	
}
