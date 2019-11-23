package edu.kkbdv.dao;

import edu.kkbdv.pojo.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(String id);

    int insert(Users record);

    Users selectByPrimaryKey(String id);

    List<Users> selectAll();

    int updateByPrimaryKey(Users record);

    int updateByPrimaryKeySelective(Users record);

    Users selectByUserName(@Param("username") String username );
}