package edu.kkbdv.pojo.vo;

import java.util.Date;

public class CommentsVo {

    private String id;

    private String fatherCommentId;

    private String toUserId;

    private String videoId;

    private String fromUserId;

    private Date createTime;

    private String comment;

    private String faceImage;

    private String nickname;

    private String timeAgoStr;

    private String toNickname;

    public String getToNickname() {
        return toNickname;
    }

    public void setToNickname(String toNickname) {
        this.toNickname = toNickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFatherCommentId() {
        return fatherCommentId;
    }

    public void setFatherCommentId(String fatherCommentId) {
        this.fatherCommentId = fatherCommentId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTimeAgoStr() {
        return timeAgoStr;
    }

    public void setTimeAgoStr(String timeAgoStr) {
        this.timeAgoStr = timeAgoStr;
    }

    @Override
    public String toString() {
        return "CommentsVo{" +
                "id='" + id + '\'' +
                ", fatherCommentId='" + fatherCommentId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", videoId='" + videoId + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", createTime=" + createTime +
                ", comment='" + comment + '\'' +
                ", faceImage='" + faceImage + '\'' +
                ", nickname='" + nickname + '\'' +
                ", timeAgoStr='" + timeAgoStr + '\'' +
                ", toNickname='" + toNickname + '\'' +
                '}';
    }
}
