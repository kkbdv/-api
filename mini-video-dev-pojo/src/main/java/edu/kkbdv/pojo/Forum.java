package edu.kkbdv.pojo;

import java.io.Serializable;
import java.util.Date;

public class Forum implements Serializable {
    private String id;

    private String forumTitle;

    private String forumAddress;

    private String forumDesc;

    private Integer joinCounts;

    private String forumDate;

    private String forumStart;

    private Integer forumDuration;

    private Integer status;

    private String userId;

    private Date createTime;

    private String forumCoverpath;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getForumTitle() {
        return forumTitle;
    }

    public void setForumTitle(String forumTitle) {
        this.forumTitle = forumTitle == null ? null : forumTitle.trim();
    }

    public String getForumAddress() {
        return forumAddress;
    }

    public void setForumAddress(String forumAddress) {
        this.forumAddress = forumAddress == null ? null : forumAddress.trim();
    }

    public String getForumDesc() {
        return forumDesc;
    }

    public void setForumDesc(String forumDesc) {
        this.forumDesc = forumDesc == null ? null : forumDesc.trim();
    }

    public Integer getJoinCounts() {
        return joinCounts;
    }

    public void setJoinCounts(Integer joinCounts) {
        this.joinCounts = joinCounts;
    }

    public String getForumDate() {
        return forumDate;
    }

    public void setForumDate(String forumDate) {
        this.forumDate = forumDate == null ? null : forumDate.trim();
    }

    public String getForumStart() {
        return forumStart;
    }

    public void setForumStart(String forumStart) {
        this.forumStart = forumStart == null ? null : forumStart.trim();
    }

    public Integer getForumDuration() {
        return forumDuration;
    }

    public void setForumDuration(Integer forumDuration) {
        this.forumDuration = forumDuration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getForumCoverpath() {
        return forumCoverpath;
    }

    public void setForumCoverpath(String forumCoverpath) {
        this.forumCoverpath = forumCoverpath == null ? null : forumCoverpath.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", forumTitle=").append(forumTitle);
        sb.append(", forumAddress=").append(forumAddress);
        sb.append(", forumDesc=").append(forumDesc);
        sb.append(", joinCounts=").append(joinCounts);
        sb.append(", forumDate=").append(forumDate);
        sb.append(", forumStart=").append(forumStart);
        sb.append(", forumDuration=").append(forumDuration);
        sb.append(", status=").append(status);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", forumCoverpath=").append(forumCoverpath);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}