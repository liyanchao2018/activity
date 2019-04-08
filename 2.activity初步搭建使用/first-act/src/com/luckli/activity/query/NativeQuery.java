package com.luckli.activity.query;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

/**
 * 　原生SQL查询
 * @author liyanchao
 *
 */
public class NativeQuery {

	public static void main(String[] args) {
		//处理引擎 生成 activity所需要用的 数据库表
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identityServic = engine.getIdentityService();
		//使用原生SQL查询
		List<Group> groupList = identityServic.createNativeGroupQuery()
				.sql(" select * from act_id_group where name_ = #{name} ")
				.parameter("name", "GroupA0").list();
		for(Group group : groupList) {
			System.out.println("使用原生SQL查询的结果："+group.getId()+"~~~~"+group.getName()+"~~~"+group.getType());;
		}
	}
}
