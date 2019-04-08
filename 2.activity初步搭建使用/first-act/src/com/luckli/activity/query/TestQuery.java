package com.luckli.activity.query;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

/**
 * 使用query的相关查询方法
 * @author liyanchao
 *
 */
public class TestQuery {
	public static void main(String[] args) {
		//处理引擎 生成 activity所需要用的 数据库表
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identityServic = engine.getIdentityService();
		//使用list方法
		List<Group> groupList = identityServic.createGroupQuery().list();
		for(Group group : groupList) {
			System.out.println(group.getId()+"~~~~"+group.getName()+"~~~"+group.getType());;
		}
		System.out.println("*******************神奇的小星星*********************************");
		//使用listPage 从第一个位置查询,拿5条数据;
		groupList = identityServic.createGroupQuery().listPage(1, 5);
		for(Group group : groupList) {
			System.out.println(group.getId()+"~~~~"+group.getName()+"~~~"+group.getType());;
		}
		System.out.println("*******************神奇的小星星*********************************");
		//使用count api 查 库中有多少个组
		long count = identityServic.createGroupQuery().count();
		System.out.println("当前的用户组为:"+count);
		System.out.println("*******************神奇的小星星*********************************");
		//使用降序排列查询,添加条件查询,分页排序
		groupList = identityServic.createGroupQuery().orderByGroupId().desc().listPage(0, 5);
		for(Group group : groupList) {
			System.out.println("条件查询,分页查询,降序排列结果:"+group.getId()+"~~~~"+group.getName()+"~~~"+group.getType());;
		}
	}
}
