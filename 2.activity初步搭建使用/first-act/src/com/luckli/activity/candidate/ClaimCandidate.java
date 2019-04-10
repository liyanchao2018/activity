package com.luckli.activity.candidate;

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
import java.util.List;
import java.util.UUID;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;

public class ClaimCandidate {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        TaskService ts = engine.getTaskService();
        IdentityService is = engine.getIdentityService();
        // 创建任务
        String taskId = "taskId123";
        Task task = ts.newTask(taskId);
        task.setName("Claim测试任务");
        ts.saveTask(task);
        //  创建用户
        String userId = "ClaimID";
        User user = is.newUser(userId);
        user.setFirstName("Claim");
        is.saveUser(user);
        
        ts.claim(taskId, userId);
//        ts.claim(taskId, "ClaimIDX");//如果给任务设置过代理人，再次设置代理人 设置会失败报错；
        
        List<Task> tasks = ts.createTaskQuery().taskAssignee(userId).list();
        for(Task t : tasks) {
            System.out.println(t.getName());
        }
    }

}
