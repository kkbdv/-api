package edu.kkbdv.pojo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class ForumVo {

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

    private String endTime;

    private String nickname;

    private String faceImage;

}
