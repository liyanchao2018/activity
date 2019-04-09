package com.luckli.activity.deploy;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;

/**
 * 	部署格式错误的bpmn的文件，通过配置 是否关闭格式验证，来进行是否保存此bpmn文件
 *	 添加错误的格式标签 <abc> I am schema error , please see here !</abc>
 *	通过 run 查看是否能部署成功。
 *  @author liyanchao
 *
 */
public class SchemaError {
	
	public static void main(String[] args) {
		//创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		//得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		//创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addClasspathResource("error/schema_error.bpmn");
//		sbuilder.disableSchemaValidation();//关闭格式验证的话 就不会验证bpmn文件格式的正确性，就会直接部署成功；
		builder.deploy();
		
		//如果 不关闭disableSchemaValidation(),  则会通过格式验证 不能继续部署成功
		//org.activiti.bpmn.exceptions.XMLException: cvc-complex-type.2.4.a: 发现了以元素 'abc' 开头的无效内容。
		
	}
}
