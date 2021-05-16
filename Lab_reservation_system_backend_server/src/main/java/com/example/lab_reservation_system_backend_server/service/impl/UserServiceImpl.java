package com.example.lab_reservation_system_backend_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lab_reservation_system_backend_server.compoment.JwtTokenUtil;
import com.example.lab_reservation_system_backend_server.mapper.RoleMapper;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.pojo.Role;
import com.example.lab_reservation_system_backend_server.pojo.User;
import com.example.lab_reservation_system_backend_server.mapper.UserMapper;
import com.example.lab_reservation_system_backend_server.pojo.UserLoginObject;
import com.example.lab_reservation_system_backend_server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lab_reservation_system_backend_server.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username,String password,String code,HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)){
            return RespBean.error(500,"验证码错误，请重新输入");
        }
        // 登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error(500,"用户名或密码错误");
        }
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功",tokenMap);
    }

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
       return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }

    /**
     * 根据用户id查询角色列表
     * @param userId
     * @return
     */
    @Override
    public List<Role> getRoles(Long userId) {
        return roleMapper.getRoles(userId);
    }
}
