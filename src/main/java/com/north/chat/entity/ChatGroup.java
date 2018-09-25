package com.north.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mht
 * @since 2018-09-18
 */
@TableName("chat_group")
public class ChatGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String groupName;

    private Integer groupOwnuserId;

    private Date updateTime;

    private Integer chatAppId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public Integer getGroupOwnuserId() {
        return groupOwnuserId;
    }

    public void setGroupOwnuserId(Integer groupOwnuserId) {
        this.groupOwnuserId = groupOwnuserId;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getChatAppId() {
        return chatAppId;
    }

    public void setChatAppId(Integer chatAppId) {
        this.chatAppId = chatAppId;
    }

    @Override
    public String toString() {
        return "ChatGroup{" +
        "id=" + id +
        ", groupName=" + groupName +
        ", groupOwnuserId=" + groupOwnuserId +
        ", updateTime=" + updateTime +
        ", chatAppId=" + chatAppId +
        "}";
    }
}
