package com.north.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author NorthZX
 * @since 2018-09-18
 */
@TableName("chat_user")
public class ChatUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    /**
     * 1：在线 0：离线
     */
    private String onlineType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getOnlineType() {
        return onlineType;
    }

    public void setOnlineType(String onlineType) {
        this.onlineType = onlineType;
    }

    @Override
    public String toString() {
        return "ChatUser{" +
        "id=" + id +
        ", userId=" + userId +
        ", onlineType=" + onlineType +
        "}";
    }
}
