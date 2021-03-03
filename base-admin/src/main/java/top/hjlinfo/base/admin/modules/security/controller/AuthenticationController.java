package top.hjlinfo.base.admin.modules.security.controller;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.hjlinfo.base.common.annotation.Log;
import top.hjlinfo.base.admin.modules.security.domain.AuthenticationInfo;
import top.hjlinfo.base.admin.modules.security.domain.AuthorizationUser;
import top.hjlinfo.base.admin.modules.security.domain.JwtUser;
import top.hjlinfo.base.admin.modules.security.utils.JwtTokenUtil;
import top.hjlinfo.base.common.utils.SecurityUtil;

/**
 * @author sting
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    private final JwtTokenUtil jwtTokenUtil;

    @Qualifier("jwtUserDetailsService")
    private final UserDetailsService userDetailsService;

    /**
     * 登录授权
     * @param authorizationUser
     * @return
     */
    @Log("用户登录")
    @PostMapping(value = "${jwt.auth.path}")
    public ResponseEntity login(@Validated @RequestBody AuthorizationUser authorizationUser){

        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authorizationUser.getUsername());

        if(!jwtUser.getPassword().equals(DigestUtil.md5Hex(authorizationUser.getPassword()))){
            throw new AccountExpiredException("密码错误");
        }

        if(!jwtUser.isEnabled()){
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }

        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);

        // 返回 token
        return ResponseEntity.ok(new AuthenticationInfo(token,jwtUser));
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping(value = "${jwt.auth.account}")
    public ResponseEntity getUserInfo(){
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(SecurityUtil.getUsername());
        return ResponseEntity.ok(jwtUser);
    }
}
