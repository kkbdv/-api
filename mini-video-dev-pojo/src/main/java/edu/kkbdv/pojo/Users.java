package edu.kkbdv.pojo;

import java.io.Serializable;

public class Users implements Serializable {
    private String id;

    private String username;

    private String password;

    private String faceImage;

    private String nickname;

    private Integer fansCounts;

    private Integer followCounts;

    private Integer receiveLikeCounts;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage == null ? null : faceImage.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getFansCounts() {
        return fansCounts;
    }

    public void setFansCounts(Integer fansCounts) {
        this.fansCounts = fansCounts;
    }

    public Integer getFollowCounts() {
        return followCounts;
    }

    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    public Integer getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    public void setReceiveLikeCounts(Integer receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", faceImage=").append(faceImage);
        sb.append(", nickname=").append(nickname);
        sb.append(", fansCounts=").append(fansCounts);
        sb.append(", followCounts=").append(followCounts);
        sb.append(", receiveLikeCounts=").append(receiveLikeCounts);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}