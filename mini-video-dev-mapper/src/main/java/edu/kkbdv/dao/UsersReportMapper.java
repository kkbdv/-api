package edu.kkbdv.dao;

import edu.kkbdv.pojo.UsersReport;
import java.util.List;

public interface UsersReportMapper {
    int deleteByPrimaryKey(String id);

    int insert(UsersReport record);

    UsersReport selectByPrimaryKey(String id);

    List<UsersReport> selectAll();

    int updateByPrimaryKey(UsersReport record);
}