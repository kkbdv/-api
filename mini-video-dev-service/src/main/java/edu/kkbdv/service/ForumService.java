package edu.kkbdv.service;

import edu.kkbdv.common.util.PagedResult;
import edu.kkbdv.pojo.Forum;
import edu.kkbdv.pojo.vo.ForumVo;
import org.springframework.data.domain.PageRequest;

import java.text.ParseException;

public interface ForumService {
    /**
     * 保存交流表
     * @param userId
     * @param forum
     */
    public void saveForum(String userId , Forum forum);

    /**
     * 获取所有论坛
     * @return
     */
    public PagedResult getAll(Integer page,Integer pageSize)throws ParseException;

    /**
     * 通过id获取所有讲座
     * @param userId
     * @param page
     * @return
     */
    public PagedResult getMyForum(String userId ,Integer page,Integer pageSie) throws ParseException;

    /**
     * 删除一个表单
     */
    public void deleteForum(String forumId,Forum forum);

    /**
     * 获取详细信息
     * @param forumId
     * @return
     * @throws ParseException
     */
    public ForumVo getForumDetail(String forumId)throws ParseException;

    /**
     * 增加参与人数
     * @param forumId
     * @param userId
     */
    public void saveJoin(String forumId,String userId);

    /**
     * 减少参与人数
     * @param forumId
     * @param userId
     */
    public void saveunJoin(String forumId,String userId);

    /**
     * 查询是否参与
     * @param forumId
     * @param userId
     * @return
     */
    public boolean checkIsJoin(String forumId,String userId);

    /**
     * 获取当前讲座的参与人数
     * @param forumId
     * @return
     */
    public int getCountsByForumId(String forumId);

}
