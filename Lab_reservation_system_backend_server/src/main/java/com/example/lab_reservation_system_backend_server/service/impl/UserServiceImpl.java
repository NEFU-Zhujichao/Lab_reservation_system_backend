package com.example.lab_reservation_system_backend_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lab_reservation_system_backend_server.compoment.JwtTokenUtil;
import com.example.lab_reservation_system_backend_server.mapper.RoleMapper;
import com.example.lab_reservation_system_backend_server.mapper.UserRoleMapper;
import com.example.lab_reservation_system_backend_server.pojo.*;
import com.example.lab_reservation_system_backend_server.mapper.UserMapper;
import com.example.lab_reservation_system_backend_server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

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
        UserDetails userDetails;
        try {
            // 登录
            userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails == null || !passwordEncoder.matches(password,userDetails.getPassword())){
                return RespBean.error(500,"用户名或密码错误");
            }
        } catch (Exception e){
            return RespBean.error(500,"用户名或密码错误");
        }
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,Object> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        tokenMap.put("tokenHeader",tokenHeader);
        // 把用户拥有的角色一同返回给前端，供前端使用
        tokenMap.put("roles",userDetails.getAuthorities());
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

    /**
     * 添加教师信息
     * @param user
     * @return
     */
    @Override
    public RespBean addUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.insert(user);
            user.getRoles().forEach(role -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRid(role.getId());
                userRoleMapper.insert(userRole);
            });
            redisTemplate.delete("users");
            return RespBean.success("添加成功",null);
        }catch (Exception e){
            throw new RuntimeException("添加失败");
        }
    }

    /**
     * 教师离职
     * @param id
     * @return
     */
    @Override
    public RespBean deleteUser(Long id) {
        try {
            userMapper.deleteById(id);
            userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id",id));
            redisTemplate.delete("users");
            return RespBean.success("离职成功",null);
        }catch (Exception e){
            throw new RuntimeException("离职失败");
        }
    }

    /**
     * 获取所有用户列表
     * @return
     */
    @Override
    public RespBean getAllUsers(String role) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        List<User> users = (List<User>) valueOperations.get("users");
        if (CollectionUtils.isEmpty(users)){
            users = userMapper.getAllUsers(role);
        }
        if (!CollectionUtils.isEmpty(users)){
            valueOperations.set("users",users);
            return RespBean.success(null,users);
        }
        return RespBean.error(500,"查询失败");
    }
}
