/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : base

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 18/09/2019 11:15:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_config`;
CREATE TABLE `gen_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `author` varchar(255) NOT NULL DEFAULT '' COMMENT '作者',
  `cover` bit(1) NOT NULL COMMENT '是否覆盖',
  `module_name` varchar(255) NOT NULL DEFAULT '' COMMENT '模块名称',
  `pack` varchar(255) NOT NULL DEFAULT '' COMMENT '至于哪个包下',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '前端代码生成的路径',
  `api_path` varchar(255) NOT NULL DEFAULT '',
  `prefix` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of gen_config
-- ----------------------------
BEGIN;
INSERT INTO `gen_config` VALUES (1, 'sting', b'0', 'base-admin', 'top.hjlinfo.base.admin.modules.test', '/Users/fastabler/Desktop/IdeaProjects/project/base-ui/src/views/system/alipay', '/Users/fastabler/Desktop/IdeaProjects/project/base-ui/src/api', '');
COMMIT;

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传日期',
  `delete_url` varchar(255) NOT NULL DEFAULT '' COMMENT '删除的URL',
  `filename` varchar(255) NOT NULL DEFAULT '' COMMENT '图片名称',
  `height` varchar(255) NOT NULL DEFAULT '' COMMENT '图片高度',
  `size` varchar(255) NOT NULL DEFAULT '' COMMENT '图片大小',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '图片地址',
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名称',
  `width` varchar(255) NOT NULL DEFAULT '' COMMENT '图片宽度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qiniu_config
-- ----------------------------
DROP TABLE IF EXISTS `qiniu_config`;
CREATE TABLE `qiniu_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `access_key` text NOT NULL COMMENT 'accessKey',
  `bucket` varchar(255) NOT NULL DEFAULT '' COMMENT 'Bucket 识别符',
  `host` varchar(255) NOT NULL COMMENT '外链域名',
  `secret_key` text NOT NULL COMMENT 'secretKey',
  `type` varchar(255) NOT NULL DEFAULT '' COMMENT '空间类型',
  `zone` varchar(255) NOT NULL DEFAULT '' COMMENT '机房',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qiniu_content
-- ----------------------------
DROP TABLE IF EXISTS `qiniu_content`;
CREATE TABLE `qiniu_content` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bucket` varchar(255) NOT NULL DEFAULT '' COMMENT 'Bucket 识别符',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '文件名称',
  `size` varchar(255) NOT NULL DEFAULT '' COMMENT '文件大小',
  `type` varchar(255) NOT NULL DEFAULT '' COMMENT '文件类型：私有或公开',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传或同步的时间',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '文件url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `pid` bigint(20) NOT NULL COMMENT '上级部门',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (1, 'eladmin', 0, '2019-03-25 09:14:05', b'1');
INSERT INTO `sys_dept` VALUES (2, '研发部', 7, '2019-03-25 09:15:32', b'1');
INSERT INTO `sys_dept` VALUES (5, '运维部', 7, '2019-03-25 09:20:44', b'1');
INSERT INTO `sys_dept` VALUES (6, '测试部', 8, '2019-03-25 09:52:18', b'1');
INSERT INTO `sys_dept` VALUES (7, '华南分部', 1, '2019-03-25 11:04:50', b'1');
INSERT INTO `sys_dept` VALUES (8, '华北分部', 1, '2019-03-25 11:04:53', b'1');
INSERT INTO `sys_dept` VALUES (9, '财务部', 7, '2019-03-25 11:05:34', b'1');
INSERT INTO `sys_dept` VALUES (10, '行政部', 8, '2019-03-25 11:05:58', b'1');
INSERT INTO `sys_dept` VALUES (11, '人事部', 8, '2019-03-25 11:07:58', b'1');
INSERT INTO `sys_dept` VALUES (12, '市场部', 7, '2019-03-25 11:10:24', b'0');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '字典名称',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (1, 'user_status', '用户状态');
INSERT INTO `sys_dict` VALUES (4, 'dept_status', '部门状态');
INSERT INTO `sys_dict` VALUES (5, 'job_status', '岗位状态');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL COMMENT '字典标签',
  `value` varchar(255) NOT NULL COMMENT '字典值',
  `sort` varchar(255) NOT NULL DEFAULT '' COMMENT '排序',
  `dict_id` bigint(11) NOT NULL COMMENT '字典id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_detail` VALUES (1, '激活', 'true', '1', 1);
INSERT INTO `sys_dict_detail` VALUES (2, '锁定', 'false', '2', 1);
INSERT INTO `sys_dict_detail` VALUES (11, '正常', 'true', '1', 4);
INSERT INTO `sys_dict_detail` VALUES (12, '停用', 'false', '2', 4);
INSERT INTO `sys_dict_detail` VALUES (13, '正常', 'true', '1', 5);
INSERT INTO `sys_dict_detail` VALUES (14, '停用', 'false', '2', 5);
COMMIT;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sort` bigint(20) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_job` VALUES (2, '董事长秘书', b'1', '2019-03-29 14:01:30', 2, 1);
INSERT INTO `sys_job` VALUES (8, '人事专员', b'1', '2019-03-29 14:52:28', 3, 11);
INSERT INTO `sys_job` VALUES (10, '产品经理', b'0', '2019-03-29 14:55:51', 4, 2);
INSERT INTO `sys_job` VALUES (11, '全栈开发', b'1', '2019-03-31 13:39:30', 6, 2);
INSERT INTO `sys_job` VALUES (12, '软件测试', b'1', '2019-03-31 13:39:43', 5, 2);
INSERT INTO `sys_job` VALUES (19, '董事长', b'1', '2019-03-31 14:58:15', 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) NOT NULL DEFAULT '',
  `exception_detail` text NOT NULL,
  `log_type` varchar(255) NOT NULL DEFAULT '',
  `method` varchar(255) NOT NULL DEFAULT '',
  `params` text NOT NULL,
  `request_ip` varchar(255) NOT NULL DEFAULT '',
  `time` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=760 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `i_frame` bit(1) NOT NULL COMMENT '是否外链',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `component` varchar(255) NOT NULL DEFAULT '' COMMENT '组件',
  `pid` bigint(20) NOT NULL COMMENT '上级菜单ID',
  `sort` bigint(20) NOT NULL COMMENT '排序',
  `icon` varchar(255) NOT NULL DEFAULT '' COMMENT '图标',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '链接地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '2018-12-18 15:11:29', b'0', '系统管理', '', 0, 1, 'system', 'system');
INSERT INTO `sys_menu` VALUES (2, '2018-12-18 15:14:44', b'0', '用户管理', 'system/user/index', 1, 2, 'peoples', 'user');
INSERT INTO `sys_menu` VALUES (3, '2018-12-18 15:16:07', b'0', '角色管理', 'system/role/index', 1, 3, 'role', 'role');
INSERT INTO `sys_menu` VALUES (4, '2018-12-18 15:16:45', b'0', '权限管理', 'system/permission/index', 1, 4, 'permission', 'permission');
INSERT INTO `sys_menu` VALUES (5, '2018-12-18 15:17:28', b'0', '菜单管理', 'system/menu/index', 1, 5, 'menu', 'menu');
INSERT INTO `sys_menu` VALUES (6, '2018-12-18 15:17:48', b'0', '系统监控', '', 0, 10, 'monitor', 'monitor');
INSERT INTO `sys_menu` VALUES (7, '2018-12-18 15:18:26', b'0', '操作日志', 'monitor/log/index', 6, 11, 'log', 'logs');
INSERT INTO `sys_menu` VALUES (8, '2018-12-18 15:19:01', b'0', '系统缓存', 'monitor/redis/index', 6, 13, 'redis', 'redis');
INSERT INTO `sys_menu` VALUES (9, '2018-12-18 15:19:34', b'0', 'SQL监控', 'monitor/sql/index', 6, 14, 'sqlMonitor', 'druid');
INSERT INTO `sys_menu` VALUES (10, '2018-12-19 13:38:16', b'0', '组件管理', '', 0, 50, 'zujian', 'components');
INSERT INTO `sys_menu` VALUES (11, '2018-12-19 13:38:49', b'0', '图标库', 'components/IconSelect', 10, 51, 'icon', 'icon');
INSERT INTO `sys_menu` VALUES (15, '2018-12-27 11:58:25', b'0', '富文本', 'components/Editor', 10, 52, 'fwb', 'tinymce');
INSERT INTO `sys_menu` VALUES (16, '2018-12-28 09:36:53', b'0', '图床管理', 'tools/picture/index', 36, 25, 'image', 'pictures');
INSERT INTO `sys_menu` VALUES (17, '2018-12-28 15:09:49', b'1', '项目地址', '', 0, 0, 'github', 'https://github.com/hjldev/base');
INSERT INTO `sys_menu` VALUES (18, '2018-12-31 11:12:15', b'0', '七牛云存储', 'tools/qiniu/index', 36, 26, 'qiniu', 'qiniu');
INSERT INTO `sys_menu` VALUES (21, '2019-01-04 16:22:03', b'0', '多级菜单', '', 0, 900, 'menu', 'nested');
INSERT INTO `sys_menu` VALUES (22, '2019-01-04 16:23:29', b'0', '二级菜单1', 'nested/menu1/index', 21, 999, 'menu', 'menu1');
INSERT INTO `sys_menu` VALUES (23, '2019-01-04 16:23:57', b'0', '二级菜单2', 'nested/menu2/index', 21, 999, 'menu', 'menu2');
INSERT INTO `sys_menu` VALUES (24, '2019-01-04 16:24:48', b'0', '三级菜单1', 'nested/menu1/menu1-1', 22, 999, 'menu', 'menu1-1');
INSERT INTO `sys_menu` VALUES (27, '2019-01-07 17:27:32', b'0', '三级菜单2', 'nested/menu1/menu1-2', 22, 999, 'menu', 'menu1-2');
INSERT INTO `sys_menu` VALUES (30, '2019-01-11 15:45:55', b'0', '代码生成', 'generator/index', 36, 22, 'dev', 'generator');
INSERT INTO `sys_menu` VALUES (32, '2019-01-13 13:49:03', b'0', '异常日志', 'monitor/log/errorLog', 6, 12, 'error', 'errorLog');
INSERT INTO `sys_menu` VALUES (33, '2019-03-08 13:46:44', b'0', 'Markdown', 'components/MarkDown', 10, 53, 'markdown', 'markdown');
INSERT INTO `sys_menu` VALUES (34, '2019-03-08 15:49:40', b'0', 'Yaml编辑器', 'components/YamlEdit', 10, 54, 'dev', 'yaml');
INSERT INTO `sys_menu` VALUES (35, '2019-03-25 09:46:00', b'0', '部门管理', 'system/dept/index', 1, 6, 'dept', 'dept');
INSERT INTO `sys_menu` VALUES (36, '2019-03-29 10:57:35', b'0', '系统工具', '', 0, 20, 'sys-tools', 'sys-tools');
INSERT INTO `sys_menu` VALUES (37, '2019-03-29 13:51:18', b'0', '岗位管理', 'system/job/index', 1, 7, 'Steve-Jobs', 'job');
INSERT INTO `sys_menu` VALUES (38, '2019-03-29 19:57:53', b'0', '接口文档', 'tools/swagger/index', 36, 23, 'swagger', 'swagger2');
INSERT INTO `sys_menu` VALUES (39, '2019-04-10 11:49:04', b'0', '字典管理', 'system/dict/index', 1, 8, 'dictionary', 'dict');
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `alias` varchar(255) NOT NULL DEFAULT '' COMMENT '别名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `pid` int(11) NOT NULL COMMENT '上级权限',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, '超级管理员', '2018-12-03 12:27:48', 'ADMIN', 0);
INSERT INTO `sys_permission` VALUES (2, '用户管理', '2018-12-03 12:28:19', 'USER_ALL', 0);
INSERT INTO `sys_permission` VALUES (3, '用户查询', '2018-12-03 12:31:35', 'USER_SELECT', 2);
INSERT INTO `sys_permission` VALUES (4, '用户创建', '2018-12-03 12:31:35', 'USER_CREATE', 2);
INSERT INTO `sys_permission` VALUES (5, '用户编辑', '2018-12-03 12:31:35', 'USER_EDIT', 2);
INSERT INTO `sys_permission` VALUES (6, '用户删除', '2018-12-03 12:31:35', 'USER_DELETE', 2);
INSERT INTO `sys_permission` VALUES (7, '角色管理', '2018-12-03 12:28:19', 'ROLES_ALL', 0);
INSERT INTO `sys_permission` VALUES (8, '角色查询', '2018-12-03 12:31:35', 'ROLES_SELECT', 7);
INSERT INTO `sys_permission` VALUES (10, '角色创建', '2018-12-09 20:10:16', 'ROLES_CREATE', 7);
INSERT INTO `sys_permission` VALUES (11, '角色编辑', '2018-12-09 20:10:42', 'ROLES_EDIT', 7);
INSERT INTO `sys_permission` VALUES (12, '角色删除', '2018-12-09 20:11:07', 'ROLES_DELETE', 7);
INSERT INTO `sys_permission` VALUES (13, '权限管理', '2018-12-09 20:11:37', 'PERMISSION_ALL', 0);
INSERT INTO `sys_permission` VALUES (14, '权限查询', '2018-12-09 20:11:55', 'PERMISSION_SELECT', 13);
INSERT INTO `sys_permission` VALUES (15, '权限创建', '2018-12-09 20:14:10', 'PERMISSION_CREATE', 13);
INSERT INTO `sys_permission` VALUES (16, '权限编辑', '2018-12-09 20:15:44', 'PERMISSION_EDIT', 13);
INSERT INTO `sys_permission` VALUES (17, '权限删除', '2018-12-09 20:15:59', 'PERMISSION_DELETE', 13);
INSERT INTO `sys_permission` VALUES (18, '缓存管理', '2018-12-17 13:53:25', 'REDIS_ALL', 0);
INSERT INTO `sys_permission` VALUES (19, '新增缓存', '2018-12-17 13:53:44', 'REDIS_CREATE', 18);
INSERT INTO `sys_permission` VALUES (20, '缓存查询', '2018-12-17 13:54:07', 'REDIS_SELECT', 18);
INSERT INTO `sys_permission` VALUES (21, '缓存编辑', '2018-12-17 13:54:26', 'REDIS_EDIT', 18);
INSERT INTO `sys_permission` VALUES (22, '缓存删除', '2018-12-17 13:55:04', 'REDIS_DELETE', 18);
INSERT INTO `sys_permission` VALUES (23, '图床管理', '2018-12-27 20:31:49', 'PICTURE_ALL', 0);
INSERT INTO `sys_permission` VALUES (24, '查询图片', '2018-12-27 20:32:04', 'PICTURE_SELECT', 23);
INSERT INTO `sys_permission` VALUES (25, '上传图片', '2018-12-27 20:32:24', 'PICTURE_UPLOAD', 23);
INSERT INTO `sys_permission` VALUES (26, '删除图片', '2018-12-27 20:32:45', 'PICTURE_DELETE', 23);
INSERT INTO `sys_permission` VALUES (29, '菜单管理', '2018-12-28 17:34:31', 'MENU_ALL', 0);
INSERT INTO `sys_permission` VALUES (30, '菜单查询', '2018-12-28 17:34:41', 'MENU_SELECT', 29);
INSERT INTO `sys_permission` VALUES (31, '菜单创建', '2018-12-28 17:34:52', 'MENU_CREATE', 29);
INSERT INTO `sys_permission` VALUES (32, '菜单编辑', '2018-12-28 17:35:20', 'MENU_EDIT', 29);
INSERT INTO `sys_permission` VALUES (33, '菜单删除', '2018-12-28 17:35:29', 'MENU_DELETE', 29);
INSERT INTO `sys_permission` VALUES (40, '部门管理', '2019-03-29 17:06:55', 'DEPT_ALL', 0);
INSERT INTO `sys_permission` VALUES (41, '部门查询', '2019-03-29 17:07:09', 'DEPT_SELECT', 40);
INSERT INTO `sys_permission` VALUES (42, '部门创建', '2019-03-29 17:07:29', 'DEPT_CREATE', 40);
INSERT INTO `sys_permission` VALUES (43, '部门编辑', '2019-03-29 17:07:52', 'DEPT_EDIT', 40);
INSERT INTO `sys_permission` VALUES (44, '部门删除', '2019-03-29 17:08:14', 'DEPT_DELETE', 40);
INSERT INTO `sys_permission` VALUES (45, '岗位管理', '2019-03-29 17:08:52', 'USERJOB_ALL', 0);
INSERT INTO `sys_permission` VALUES (46, '岗位查询', '2019-03-29 17:10:27', 'USERJOB_SELECT', 45);
INSERT INTO `sys_permission` VALUES (47, '岗位创建', '2019-03-29 17:10:55', 'USERJOB_CREATE', 45);
INSERT INTO `sys_permission` VALUES (48, '岗位编辑', '2019-03-29 17:11:08', 'USERJOB_EDIT', 45);
INSERT INTO `sys_permission` VALUES (49, '岗位删除', '2019-03-29 17:11:19', 'USERJOB_DELETE', 45);
INSERT INTO `sys_permission` VALUES (50, '字典管理', '2019-04-10 16:24:51', 'DICT_ALL', 0);
INSERT INTO `sys_permission` VALUES (51, '字典查询', '2019-04-10 16:25:16', 'DICT_SELECT', 50);
INSERT INTO `sys_permission` VALUES (52, '字典创建', '2019-04-10 16:25:29', 'DICT_CREATE', 50);
INSERT INTO `sys_permission` VALUES (53, '字典编辑', '2019-04-10 16:27:19', 'DICT_EDIT', 50);
INSERT INTO `sys_permission` VALUES (54, '字典删除', '2019-04-10 16:27:30', 'DICT_DELETE', 50);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `data_scope` varchar(255) NOT NULL DEFAULT '',
  `level` int(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '2018-11-23 11:04:37', '超级管理员', '系统所有权', '全部', 1);
INSERT INTO `sys_role` VALUES (2, '2018-11-23 13:09:06', '普通用户', '用于测试菜单与权限', '自定义', 3);
INSERT INTO `sys_role` VALUES (4, '2019-05-13 14:16:15', '普通管理员', '普通管理员级别为2，使用该角色新增用户时只能赋予比普通管理员级别低的角色', '全部', 2);
INSERT INTO `sys_role` VALUES (5, '2019-09-17 19:33:40', '测试管理员一', '测试角色', '自定义', 3);
COMMIT;

-- ----------------------------
-- Table structure for sys_roles_depts
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_depts`;
CREATE TABLE `sys_roles_depts` (
  `role_id` bigint(20) unsigned NOT NULL,
  `dept_id` bigint(20) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_roles_depts
-- ----------------------------
BEGIN;
INSERT INTO `sys_roles_depts` VALUES (2, 5);
INSERT INTO `sys_roles_depts` VALUES (2, 8);
INSERT INTO `sys_roles_depts` VALUES (5, 10);
INSERT INTO `sys_roles_depts` VALUES (5, 11);
COMMIT;

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus` (
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_roles_menus
-- ----------------------------
BEGIN;
INSERT INTO `sys_roles_menus` VALUES (1, 1);
INSERT INTO `sys_roles_menus` VALUES (2, 1);
INSERT INTO `sys_roles_menus` VALUES (4, 1);
INSERT INTO `sys_roles_menus` VALUES (1, 2);
INSERT INTO `sys_roles_menus` VALUES (2, 2);
INSERT INTO `sys_roles_menus` VALUES (4, 2);
INSERT INTO `sys_roles_menus` VALUES (1, 3);
INSERT INTO `sys_roles_menus` VALUES (2, 3);
INSERT INTO `sys_roles_menus` VALUES (1, 4);
INSERT INTO `sys_roles_menus` VALUES (2, 4);
INSERT INTO `sys_roles_menus` VALUES (1, 5);
INSERT INTO `sys_roles_menus` VALUES (2, 5);
INSERT INTO `sys_roles_menus` VALUES (1, 6);
INSERT INTO `sys_roles_menus` VALUES (2, 6);
INSERT INTO `sys_roles_menus` VALUES (1, 7);
INSERT INTO `sys_roles_menus` VALUES (1, 8);
INSERT INTO `sys_roles_menus` VALUES (2, 8);
INSERT INTO `sys_roles_menus` VALUES (1, 9);
INSERT INTO `sys_roles_menus` VALUES (2, 9);
INSERT INTO `sys_roles_menus` VALUES (1, 10);
INSERT INTO `sys_roles_menus` VALUES (2, 10);
INSERT INTO `sys_roles_menus` VALUES (1, 11);
INSERT INTO `sys_roles_menus` VALUES (2, 11);
INSERT INTO `sys_roles_menus` VALUES (1, 15);
INSERT INTO `sys_roles_menus` VALUES (2, 15);
INSERT INTO `sys_roles_menus` VALUES (1, 16);
INSERT INTO `sys_roles_menus` VALUES (2, 16);
INSERT INTO `sys_roles_menus` VALUES (1, 17);
INSERT INTO `sys_roles_menus` VALUES (2, 17);
INSERT INTO `sys_roles_menus` VALUES (1, 18);
INSERT INTO `sys_roles_menus` VALUES (2, 18);
INSERT INTO `sys_roles_menus` VALUES (1, 21);
INSERT INTO `sys_roles_menus` VALUES (2, 21);
INSERT INTO `sys_roles_menus` VALUES (1, 22);
INSERT INTO `sys_roles_menus` VALUES (2, 22);
INSERT INTO `sys_roles_menus` VALUES (1, 23);
INSERT INTO `sys_roles_menus` VALUES (2, 23);
INSERT INTO `sys_roles_menus` VALUES (1, 24);
INSERT INTO `sys_roles_menus` VALUES (2, 24);
INSERT INTO `sys_roles_menus` VALUES (1, 27);
INSERT INTO `sys_roles_menus` VALUES (2, 27);
INSERT INTO `sys_roles_menus` VALUES (1, 30);
INSERT INTO `sys_roles_menus` VALUES (2, 30);
INSERT INTO `sys_roles_menus` VALUES (1, 32);
INSERT INTO `sys_roles_menus` VALUES (1, 33);
INSERT INTO `sys_roles_menus` VALUES (2, 33);
INSERT INTO `sys_roles_menus` VALUES (1, 34);
INSERT INTO `sys_roles_menus` VALUES (2, 34);
INSERT INTO `sys_roles_menus` VALUES (1, 35);
INSERT INTO `sys_roles_menus` VALUES (2, 35);
INSERT INTO `sys_roles_menus` VALUES (1, 36);
INSERT INTO `sys_roles_menus` VALUES (2, 36);
INSERT INTO `sys_roles_menus` VALUES (1, 37);
INSERT INTO `sys_roles_menus` VALUES (2, 37);
INSERT INTO `sys_roles_menus` VALUES (1, 38);
INSERT INTO `sys_roles_menus` VALUES (2, 38);
INSERT INTO `sys_roles_menus` VALUES (1, 39);
INSERT INTO `sys_roles_menus` VALUES (2, 39);
INSERT INTO `sys_roles_menus` VALUES (5, 1);
INSERT INTO `sys_roles_menus` VALUES (5, 2);
INSERT INTO `sys_roles_menus` VALUES (5, 3);
COMMIT;

-- ----------------------------
-- Table structure for sys_roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_permissions`;
CREATE TABLE `sys_roles_permissions` (
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) unsigned NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_roles_permissions
-- ----------------------------
BEGIN;
INSERT INTO `sys_roles_permissions` VALUES (1, 1);
INSERT INTO `sys_roles_permissions` VALUES (2, 3);
INSERT INTO `sys_roles_permissions` VALUES (2, 8);
INSERT INTO `sys_roles_permissions` VALUES (2, 14);
INSERT INTO `sys_roles_permissions` VALUES (2, 20);
INSERT INTO `sys_roles_permissions` VALUES (2, 23);
INSERT INTO `sys_roles_permissions` VALUES (2, 24);
INSERT INTO `sys_roles_permissions` VALUES (2, 25);
INSERT INTO `sys_roles_permissions` VALUES (2, 26);
INSERT INTO `sys_roles_permissions` VALUES (2, 30);
INSERT INTO `sys_roles_permissions` VALUES (2, 41);
INSERT INTO `sys_roles_permissions` VALUES (2, 46);
INSERT INTO `sys_roles_permissions` VALUES (2, 51);
INSERT INTO `sys_roles_permissions` VALUES (4, 2);
INSERT INTO `sys_roles_permissions` VALUES (4, 3);
INSERT INTO `sys_roles_permissions` VALUES (4, 4);
INSERT INTO `sys_roles_permissions` VALUES (4, 5);
INSERT INTO `sys_roles_permissions` VALUES (4, 6);
INSERT INTO `sys_roles_permissions` VALUES (5, 2);
INSERT INTO `sys_roles_permissions` VALUES (5, 3);
INSERT INTO `sys_roles_permissions` VALUES (5, 4);
INSERT INTO `sys_roles_permissions` VALUES (5, 5);
INSERT INTO `sys_roles_permissions` VALUES (5, 6);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱',
  `enabled` bigint(20) NOT NULL COMMENT '状态：1启用、0禁用',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `last_password_reset_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改密码的日期',
  `dept_id` bigint(20) NOT NULL,
  `phone` varchar(255) NOT NULL DEFAULT '',
  `job_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `USERNAME` (`username`),
  UNIQUE KEY `EMAIL` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg', '2018-08-23 09:11:56', 'admin@eladmin.net', 1, 'e10adc3949ba59abbe56e057f20f883e', 'admin', '2019-05-18 17:34:21', 2, '18888888888', 11);
INSERT INTO `sys_user` VALUES (3, 'https://aurora-1255840532.cos.ap-chengdu.myqcloud.com/8918a306ea314404835a9196585c4b75.jpeg', '2018-12-27 20:05:26', 'test@eladmin.net', 1, 'e10adc3949ba59abbe56e057f20f883e', 'test', '2019-04-01 09:15:24', 2, '17777777777', 12);
INSERT INTO `sys_user` VALUES (5, 'https://aurora-1255840532.cos.ap-chengdu.myqcloud.com/8918a306ea314404835a9196585c4b75.jpeg', '2019-04-02 10:07:12', 'hr@eladmin.net', 1, 'e10adc3949ba59abbe56e057f20f883e', 'hr', '2019-04-01 09:15:24', 11, '15555555555', 8);
INSERT INTO `sys_user` VALUES (6, 'https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg', '2019-04-02 10:07:12', '123@qq.com', 1, 'e10adc3949ba59abbe56e057f20f883e', 'test2', '2019-04-01 09:15:24', 2, '17000000000', 10);
COMMIT;

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
BEGIN;
INSERT INTO `sys_users_roles` VALUES (1, 1);
INSERT INTO `sys_users_roles` VALUES (3, 2);
INSERT INTO `sys_users_roles` VALUES (5, 4);
INSERT INTO `sys_users_roles` VALUES (6, 5);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
