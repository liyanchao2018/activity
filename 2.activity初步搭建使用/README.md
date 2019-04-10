# activity
activity搭建与应用

#### 1.activity简单使用

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



#### 2.``activiti.cfg.xml``配置工作流使用的数据库表

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



#### 3.Query api的使用

``list()``,``listPage()``,``count()``,``orderbyXXX().desc()``的用法；

```java
public static void main(String[] args) {
		//处理引擎 生成 activity所需要用的 数据库表
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identityServic = engine.getIdentityService();
		//使用list方法
		List<Group> groupList = identityServic.createGroupQuery().list();
		for(Group group : groupList) {
			System.out.println(group.getId()+"~~~~"+group.getName()+"~~~"+group.getType());;
		}
		System.out.println("*******************神奇的小星星*******************");
		//使用listPage 从第一个位置查询,拿5条数据;
		groupList = identityServic.createGroupQuery().listPage(1, 5);
		for(Group group : groupList) {
			System.out.println(group.getId()+"~~~~"+group.getName()+"~~~"+group.getType());;
		}
		System.out.println("***********神奇的小星星*********************");
		//使用count api 查 库中有多少个组
		long count = identityServic.createGroupQuery().count();
		System.out.println("当前的用户组为:"+count);
		System.out.println("*************神奇的小星星********************");
		//使用降序排列查询,添加条件查询,分页排序
		groupList = identityServic.createGroupQuery().orderByGroupId().desc().listPage(0, 5);
		for(Group group : groupList) {
			System.out.println("条件查询,分页查询,降序排列结果:"+group.getId()+"~~~~"+group.getName()+"~~~"+group.getType());;
		}
	}
```



*** 打印结果 *** 

```xm
0~~~~GroupA0~~~TYPE_0
1~~~~GroupA1~~~TYPE_1
2~~~~GroupA2~~~TYPE_2
3~~~~GroupA3~~~TYPE_3
4~~~~GroupA4~~~TYPE_4
5~~~~GroupA5~~~TYPE_5
6~~~~GroupA6~~~TYPE_6
7~~~~GroupA7~~~TYPE_7
8~~~~GroupA8~~~TYPE_8
9~~~~GroupA9~~~TYPE_9
*******************神奇的小星星*********************************
1~~~~GroupA1~~~TYPE_1
2~~~~GroupA2~~~TYPE_2
3~~~~GroupA3~~~TYPE_3
4~~~~GroupA4~~~TYPE_4
5~~~~GroupA5~~~TYPE_5
*******************神奇的小星星*********************************
当前的用户组为:10
*******************神奇的小星星*********************************
条件查询,分页查询,降序排列结果:9~~~~GroupA9~~~TYPE_9
条件查询,分页查询,降序排列结果:8~~~~GroupA8~~~TYPE_8
条件查询,分页查询,降序排列结果:7~~~~GroupA7~~~TYPE_7
条件查询,分页查询,降序排列结果:6~~~~GroupA6~~~TYPE_6
条件查询,分页查询,降序排列结果:5~~~~GroupA5~~~TYPE_5
```



#### 4.任务权限：

 * 		**任务候选人（组）**
      * 		参考``com.luckli.activity.candidate.TaskUser``
           * 		设置任务的候选用户组，查询这个用户有权限处理的任务列表；
 * 		**任务持有人**
      * 		参考``com.luckli.activity.candidate.OwnerCandidate``
           * 		设置任务的候选用户组，根据用户来查询他所持有的任务列表；
 * 		**任务代理人**
      * 		参考``com.luckli.activity.candidate.ClaimCandidate``
           * 		设置任务的候选用户组，根据用户来查询他所持有的任务列表；