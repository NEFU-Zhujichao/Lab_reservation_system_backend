package com.example.lab_reservation_system_backend_server.mapper;

import com.example.lab_reservation_system_backend_server.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
public interface UserMapper extends BaseMapper<User> {

}
