package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/3/25 17:47
 * 邮箱：771548229@qq..com
 */
public class WashOrderComment {

    /**
     * answer : string
     * content : string
     * createTime : 2019-03-25T09:48:14.553Z
     * headPortrait : string
     * id : string
     * imgs : string
     * nickName : string
     * score : 0
     * userId : string
     */

    private String answer;
    private String content;
    private String createTime;
    private String headPortrait;
    private String id;
    private String imgs;
    private String nickName;
    private float score;
    private String userId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
