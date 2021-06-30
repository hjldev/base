package top.hjlinfo.base.admin.config;

import top.hjlinfo.base.admin.modules.system.domain.SysDept;
import top.hjlinfo.base.admin.modules.system.service.DeptService;
import top.hjlinfo.base.admin.modules.system.service.RoleService;
import top.hjlinfo.base.admin.modules.system.service.UserService;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleSmallDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.UserDTO;
import top.hjlinfo.base.admin.modules.security.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据权限配置
 * @author sting
 * @date 2019-4-1
 */
@Component
public class DataScope {

    private final String[] scopeType = {"全部","本级","自定义"};

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeptService deptService;

    public Set<Long> getDeptIds() {

        UserDTO user = userService.findByName(SecurityUtil.getUsername());

        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();

        // 查询用户角色
        List<RoleSmallDTO> roleSet = roleService.findByUsers_Id(user.getId());

        for (RoleSmallDTO role : roleSet) {

            if (scopeType[0].equals(role.getDataScope())) {
                return new HashSet<>() ;
            }

            // 存储本级的数据权限
            if (scopeType[1].equals(role.getDataScope())) {
                deptIds.add(user.getDeptId());
            }

            // 存储自定义的数据权限
            if (scopeType[2].equals(role.getDataScope())) {
                List<SysDept> depts = deptService.findByRoleIds(role.getId());
                for (SysDept dept : depts) {
                    deptIds.add(dept.getId());
                    List<SysDept> deptChildren = deptService.findByPid(dept.getId());
                    if (deptChildren != null && deptChildren.size() != 0) {
                        deptIds.addAll(getDeptChildren(deptChildren));
                    }
                }
            }
        }
        return deptIds;
    }


    public List<Long> getDeptChildren(List<SysDept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept!=null && dept.getEnabled()){
                        List<SysDept> depts = deptService.findByPid(dept.getId());
                        if(deptList!=null && deptList.size()!=0){
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;
    }
}
