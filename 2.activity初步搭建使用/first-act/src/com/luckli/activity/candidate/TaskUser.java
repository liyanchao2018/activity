package com.luckli.activity.candidate;

import java.util.List;
import java.util.UUID;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;

/**
 * 任务权限：
 * 		任务候选人（组）
 * 		任务持有人
 * 		任务代理人
 * 此处描述：任务候选人（组）
 * 		设置任务的候选用户组，查询这个用户有权限处理的任务列表；
 * 
 * @author liyanchao
 *
 */
public class TaskUser {
	
	public static void main(String[] args) {
		
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		TaskService ts = engine.getTaskService();
		IdentityService identityService = engine.getIdentityService();
		
		String taskId = UUID.randomUUID().toString();
		//创建任务
		Task task = ts.newTask(taskId);
		task.setName("测试任务");
		ts.saveTask(task);
		
		//创建用户组
		String userId = UUID.randomUUID().toString();
		User user = identityService.newUser(userId);
		user.setId("userid");
		user.setFirstName("测试用户liyanchao");
		identityService.saveUser(user);
		
		//设置任务的候选用户组
		ts.addCandidateUser(taskId, userId);
		
		//查询这个用户有权限处理的任务列表；
		List<Task> tasks = ts.createTaskQuery().taskCandidateUser(userId).list();
		System.out.println(userId + " 这个用户有权限处理的任务有：");
		
		for(Task t : tasks) {
			System.out.println(t.getName());
		}
		
		
	}
}
