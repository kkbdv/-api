package edu.kkbdv.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.kkbdv.common.util.PagedResult;
import edu.kkbdv.dao.ForumMapper;
import edu.kkbdv.dao.UsersForumMapper;
import edu.kkbdv.pojo.Forum;
import edu.kkbdv.pojo.UsersForum;
import edu.kkbdv.pojo.vo.ForumVo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumMapper forumMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private UsersForumMapper usersForumMapper;

    @Override
    public void saveForum(String userId , Forum forum) {
        forum.setUserId(userId);
        forum.setCreateTime(new Date());
        forum.setId(sid.nextShort());
        forumMapper.insert(forum);
    }

    @Override
    public PagedResult getAll(Integer page,Integer pageSize)throws ParseException {
        PageHelper.startPage(page,pageSize);
        List<ForumVo> forumVos = forumMapper.selectAllForum();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        for(ForumVo forumVo:forumVos){
            Date startTime= simpleDateFormat.parse(forumVo.getForumStart());
            long endTime = startTime.getTime()+ (long)forumVo.getForumDuration()*1000*60;
            Date end = new Date(endTime);
            forumVo.setEndTime(simpleDateFormat.format(end));
        }

        PageInfo<ForumVo> pageInfo = new PageInfo<>(forumVos);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setRows(forumVos);
        pagedResult.setPage(page);
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRecords(pageInfo.getTotal());

        return pagedResult;
    }

    @Override
    public PagedResult getMyForum(String userId, Integer page,Integer pageSie)throws ParseException {
        PageHelper.startPage(page,pageSie);
        List<ForumVo> forumVos = forumMapper.selectMyForum(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        for(ForumVo forumVo:forumVos){
            Date startTime= simpleDateFormat.parse(forumVo.getForumStart());
            long endTime = startTime.getTime()+ (long)forumVo.getForumDuration()*1000*60;
            Date end = new Date(endTime);
            forumVo.setEndTime(simpleDateFormat.format(end));
        }

        PageInfo<ForumVo> pageInfo = new PageInfo<>(forumVos);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setRows(forumVos);
        pagedResult.setPage(page);
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRecords(pageInfo.getTotal());

        return pagedResult;
    }

    @Override
    public void deleteForum(String forumId, Forum forum) {
        //删除记录
        int i = forumMapper.deleteByPrimaryKey(forumId);

        //删除图片
            File coverFile = new File(forum.getForumCoverpath());
            deleteMethod(coverFile);

    }

    @Override
    public ForumVo getForumDetail(String forumId) throws ParseException{
        ForumVo forumVo = forumMapper.selecForumDetail(forumId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        Date startTime= simpleDateFormat.parse(forumVo.getForumStart());
        long endTime = startTime.getTime()+ (long)forumVo.getForumDuration()*1000*60;
        Date end = new Date(endTime);
        forumVo.setEndTime(simpleDateFormat.format(end));
        return forumVo;
    }

    @Override
    public void saveJoin(String forumId, String userId) {
        //新增加记录
        UsersForum uf = new UsersForum();
        uf.setId(sid.nextShort());
        uf.setForumId(forumId);
        uf.setUserId(userId);
        usersForumMapper.insert(uf);
        //字段+1
        forumMapper.addJoinCount(forumId);
    }

    @Override
    public void saveunJoin(String forumId, String userId) {
        //减少记录
        usersForumMapper.deleteByUserIdAndForumId(userId,forumId);
        //字段-1
        forumMapper.redJoinCount(forumId);
    }

    @Override
    public boolean checkIsJoin(String forumId, String userId) {
        UsersForum usersForum = usersForumMapper.selectByUserIdAndForumId(forumId, userId);
        if(usersForum==null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public int getCountsByForumId(String forumId) {

        int i = forumMapper.selectJoinCount(forumId);
        return i;
    }

    private void deleteMethod(File file){
        if(file.exists()){
            file.delete();
            System.out.println(file.getAbsolutePath()+"  文件已被删除");
        }
    }

}
