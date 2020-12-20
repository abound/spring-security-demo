package org.example.mapper;

import org.example.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @Author: MaiYu
 * @Date: Create in 10:37 2020/12/20
 */
@Repository
public interface UserMapper {

    User selectById(int id);

    User selectByName(String username);

    int insertUser(User user);
}
