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

package top.hjlinfo.base.admin.modules.activiti.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.hjlinfo.base.admin.modules.activiti.entity.LeaveBill;
import top.hjlinfo.base.admin.modules.activiti.enums.TaskStatusEnum;
import top.hjlinfo.base.admin.modules.activiti.service.LeaveBillService;
import top.hjlinfo.base.admin.modules.activiti.service.ProcessService;
import top.hjlinfo.base.admin.modules.activiti.utils.R;


/**
 * 请假流程
 *
 * @author 冷冷
 * @date 2018-09-27 15:20:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/act/leave-bill")
public class LeaveBillController {
	private final LeaveBillService leaveBillService;
	private final ProcessService processService;

	/**
	 * 请假审批单简单分页查询
	 *
	 * @param page      分页对象
	 * @param leaveBill 请假审批单
	 * @return
	 */
	@GetMapping("/page")
	public R getLeaveBillPage(Page page, LeaveBill leaveBill) {
		return new R<>(leaveBillService.page(page, Wrappers.query(leaveBill)));
	}


	/**
	 * 信息
	 *
	 * @param leaveId
	 * @return R
	 */
	@GetMapping("/{leaveId}")
	public R getById(@PathVariable("leaveId") Integer leaveId) {
		return new R<>(leaveBillService.getById(leaveId));
	}

	/**
	 * 保存
	 *
	 * @param leaveBill
	 * @return R
	 */
	@PostMapping
	public R save(@RequestBody LeaveBill leaveBill) {
		leaveBill.setUsername("admin");
		leaveBill.setState(TaskStatusEnum.UNSUBMIT.getStatus());
		return new R<>(leaveBillService.save(leaveBill));
	}

	/**
	 * 修改
	 *
	 * @param leaveBill
	 * @return R
	 */
	@PutMapping
	public R updateById(@RequestBody LeaveBill leaveBill) {
		return new R<>(leaveBillService.updateById(leaveBill));
	}

	/**
	 * 删除
	 *
	 * @param leaveId
	 * @return R
	 */
	@DeleteMapping("/{leaveId}")
	public R removeById(@PathVariable Integer leaveId) {
		return new R<>(leaveBillService.removeById(leaveId));
	}

	/**
	 * 提交请假流程
	 *
	 * @param leaveId
	 * @return R
	 */
	@GetMapping("/submit/{leaveId}")
	public R submit(@PathVariable("leaveId") Integer leaveId) {
		return new R<>(processService.saveStartProcess(leaveId));
	}
}
