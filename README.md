<h1 style="text-align: center">后台管理系统</h1>
<div style="text-align: center">


</div>

#### 项目简介

fork自[eladmin](https://github.com/elunez/eladmin) 由jpa改为mybatis

基于 Spring Boot 2.1.0 、 mybatis-plus、 Spring Security、redis、Vue的前后端分离的后台管理系统， 权限控制的方式为RBAC，项目支持数据字典与数据权限管理，支持一键生成前后端代码，支持前端菜单动态路由

**账号密码** ```admin/123456```(默认密码都是123456)

#### 项目源码

|     |   后端源码  |   前端源码  |
|---  |--- | --- |
|  github   |  https://github.com/hjldev/base   |  https://github.com/hjldev/base-ui  |

####  系统功能
- 用户管理：提供用户的相关配置，新增用户后，默认密码为123456
- 角色管理：对权限与菜单进行分配，可根据部门设置角色的数据权限
- 权限管理：权限细化到接口，可以理解成按钮权限
- 菜单管理：已实现菜单动态路由，后端可配置化，支持多级菜单
- 部门管理：可配置系统组织架构，树形表格展示
- 岗位管理：配置各个部门的职位
- 字典管理：应广大码友的要求加入字典管理，可维护常用一些固定的数据，如：状态，性别等
- 操作日志：记录用户操作的日志
- 异常日志：记录异常日志，方便开发人员定位错误
- 系统缓存：使用jedis将缓存操作可视化，并提供对redis的基本操作，可根据需求自行扩展
- SQL监控：采用druid 监控数据库访问性能，默认用户名admin，密码123456
- 代码生成：高灵活度一键生成前后端代码，减少百分之80左右的工作任务
- 免费图床：使用sm.ms图床，用作公共图片上传使用
- 七牛云存储：可同步七牛云存储的数据到系统，无需登录七牛云直接操作云数据

#### 项目结构
项目采用分模块开发方式，将通用的配置放在公共模块，```system```模块为系统核心模块也是项目入口模块，```logging``` 模块为系统的日志模块，```tools``` 为第三方工具模块，包含了图床、邮件、七牛云、支付宝，```generator``` 为系统的代码生成模块
- base-common 公共模块
    - exception 项目统一异常的处理
    - mapper mapstruct的通用mapper
    - redis redis缓存相关配置
    - swagger2 接口文档配置
    - utils 系统通用工具类
- base-admin 后台管理
	- config 配置跨域与静态资源，与数据权限
	- modules 系统相关模块(登录授权、定时任务等)
	    - generator 系统代码生成模块
	    - logging 系统日志模块
	    - monitor redis监控模块
	    - security 账号安全控制模块
	    - system 角色权限模块
	    - tools 系统第三方工具模块

#### 系统预览
<table>
    <tr>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77fa8144d68788.png"/></td>
        <td><img src="https://i.loli.net/2019/05/18/5cdf7763993e361778.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2019/05/18/5cdf7763971d453615.png"/></td>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77632e85a60423.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77632b4b090165.png"/></td>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77639929277783.png"/></td>
    </tr>
    <tr>   
 <td><img src="https://i.loli.net/2019/05/18/5cdf78969adc389599.png"/></td>
    </tr>
</table>
