/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package top.hjlinfo.base.admin.modules.activiti.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import top.hjlinfo.base.common.utils.SpringContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lengleng
 * @date 2018/9/27
 * 请假流程监听器
 */
@Slf4j
public class LeaveProcessTaskListener implements TaskListener {

	/**
	 * 查询提交人的上级
	 *
	 * @param delegateTask
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		SimpMessagingTemplate simpMessagingTemplate = SpringContextHolder.getBean(SimpMessagingTemplate.class);

		List<String> remindUserList = new ArrayList<>();

		log.info("用户 {} 不存在上级,任务单由当前用户审批", "admin");
		delegateTask.addCandidateUser("admin");
		remindUserList.add("admin");

		remindUserList.forEach(user -> {
			String target = String.format("%s-%s", "admin", "1");
			simpMessagingTemplate.convertAndSendToUser(target, "/remind", delegateTask.getName());
		});
	}
}
