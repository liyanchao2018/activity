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
 * 此处描述：任务持有人
 * 		设置任务的候选用户组，根据用户来查询他所持有的任务列表；
 * 
 * @author liyanchao
 *
 */
public class OwnerCandidate {

    public static void main(String[] args) {
    	
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        TaskService ts = engine.getTaskService();
        IdentityService is = engine.getIdentityService();
        // 创建任务
        String taskId = UUID.randomUUID().toString();
        Task task = ts.newTask(taskId);
        task.setName("Owner测试任务");
        ts.saveTask(task);
        //  创建用户
        String userId = UUID.randomUUID().toString();
        User user = is.newUser(userId);
        user.setFirstName("Owner");
        is.saveUser(user);
        // 设置任务的候选用户组
        ts.setOwner(taskId, userId);
        
        // 根据用户来查询他所持有的任务
        List<Task> tasks = ts.createTaskQuery().taskOwner(userId).list();
        for(Task t : tasks) {
            System.out.println(t.getName());
        }
        
    }

}
