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
@TableName("chat_app")
public class ChatApp implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String chatAppName;

    private String chatAppId;

    private String chatAppSecret;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getChatAppName() {
        return chatAppName;
    }

    public void setChatAppName(String chatAppName) {
        this.chatAppName = chatAppName;
    }
    public String getChatAppId() {
        return chatAppId;
    }

    public void setChatAppId(String chatAppId) {
        this.chatAppId = chatAppId;
    }
    public String getChatAppSecret() {
        return chatAppSecret;
    }

    public void setChatAppSecret(String chatAppSecret) {
        this.chatAppSecret = chatAppSecret;
    }

    @Override
    public String toString() {
        return "ChatApp{" +
        "id=" + id +
        ", chatAppName=" + chatAppName +
        ", chatAppId=" + chatAppId +
        ", chatAppSecret=" + chatAppSecret +
        "}";
    }
}
