package edu.kkbdv.pojo.vo;

import edu.kkbdv.pojo.Users;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryPublisherVo {
    private Users publisher;// i am the publisher
    private boolean userLikeVideo; // if the user like this video
}
