package com.luckli.activity.query;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

/**
 * 	保存10个用户组
 * @author liyanchao
 *
 */
public class SaveGroup {
	public static void main(String[] args) {
		//处理引擎 生成 activity所需要用的 数据库表
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		//保存10个用户组
		IdentityService identityService = engine.getIdentityService();		
		for(int i=0;i<10;i++) {
			Group group = identityService.newGroup(i+"");
			group.setName("GroupA"+i);
			group.setType("TYPE_"+i);
			identityService.saveGroup(group);
		}
		engine.close();
		System.exit(0);
	}

}
