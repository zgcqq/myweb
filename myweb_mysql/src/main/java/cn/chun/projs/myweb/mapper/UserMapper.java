package cn.chun.projs.myweb.mapper;

import cn.chun.projs.myweb.pojo.User;

import java.util.List;

/**
 * @author test@email

 * @date 2022/8/25
 */
public interface UserMapper {
    User findById(Long id);
    List<User> findAll();
}
