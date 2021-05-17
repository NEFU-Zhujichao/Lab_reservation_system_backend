package com.example.lab_reservation_system_backend_server.controller;


import com.example.lab_reservation_system_backend_server.mapper.UserMapper;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.pojo.User;
import com.example.lab_reservation_system_backend_server.service.IUserService;
import com.example.lab_reservation_system_backend_server.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "UserController")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private String role = "TEACHER";

    @ApiOperation(value = "查询所有教师信息")
    @GetMapping("/")
    public RespBean listUsers(){
        if (UserUtils.hasAdminRole()){
            return userService.getAllUsers(role);
        }
        return RespBean.error(403,"无权限，请联系管理员");
    }

    @ApiOperation(value = "添加教师")
    @PostMapping("/")
    public RespBean addUser(@RequestBody User user){
        if (UserUtils.hasAdminRole()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
           return userService.addUser(user);
        }
        return RespBean.error(403,"无权限，请联系管理员");
    }

    @ApiOperation(value = "教师离职")
    @DeleteMapping("/{id}")
    public RespBean deleteUser(@PathVariable Long id){
        if (UserUtils.hasAdminRole()){
            return userService.deleteUser(id);
        }
        return RespBean.error(403,"无权限，请联系管理员");
    }

    @ApiOperation(value = "更新教师信息")
    @PutMapping("/")
    public RespBean updateUser(@RequestBody User user){
        if (UserUtils.hasAdminRole()){
            if (userService.updateById(user)){
                return RespBean.success("更新成功",null);
            }else return RespBean.error(500,"更新失败");
        }
        return RespBean.error(403,"无权限，请联系管理员");
    }
}
