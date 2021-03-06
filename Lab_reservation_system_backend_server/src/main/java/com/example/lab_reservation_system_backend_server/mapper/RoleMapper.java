package com.example.lab_reservation_system_backend_server.mapper;

import com.example.lab_reservation_system_backend_server.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询角色列表
     * @param userId
     * @return
     */
    List<Role> getRoles(Long userId);
}
