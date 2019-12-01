package edu.kkbdv.dao;

import edu.kkbdv.pojo.SearchRecords;
import java.util.List;

public interface SearchRecordsMapper {
    int deleteByPrimaryKey(String id);

    int insert(SearchRecords record);

    SearchRecords selectByPrimaryKey(String id);

    List<SearchRecords> selectAll();

    int updateByPrimaryKey(SearchRecords record);

    List<String> selectAllContents();
}