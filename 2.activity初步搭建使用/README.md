# activity
activity搭建与应用

1.

```first-act```通过main方法 来执行一个流程的发布、提交、审核；

``FirstAct.java``

```java

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

```



2.``activiti.cfg.xml``配置工作流使用的数据库表

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
	<!-- 流程引擎配置的bean -->
	<bean id="processEngineConfiguration"
		class="org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration">
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/activity" />
		<property name="jdbcDriver" value="com.mysql.jdbc.Driver" />
		<property name="jdbcUsername" value="root" />
		<property name="jdbcPassword" value="root" />
		<!-- 自动补全创建数据库中不存在activity相关业务的表 -->
		<property name="databaseSchemaUpdate" value="true" />
	</bean>
</beans>
```



