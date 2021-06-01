package com.example.lab_reservation_system_backend_server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户对象", description="")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "角色")
    @TableField(exist = false)
    private List<Role> roles;

    @ApiModelProperty(value = "权限")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return authorities;
    }

    @ApiModelProperty(value = "账户是否没过期")
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @ApiModelProperty(value = "账户是否没锁定")
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @ApiModelProperty(value = "凭证是否没过期")
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @ApiModelProperty(value = "账户是否没禁用")
    @Override
    public boolean isEnabled() {
        // 默认情况下所有用户均未被禁用
        return true;
    }
}
