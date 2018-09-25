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
@TableName("chat_msg")
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String chatMsg;

    private Long chatFrom;

    private Long chatTo;

    private Date updateTime;

    private String extras;

    /**
     * 1单聊2群聊
     */
    private String chatType;

    /**
     * 0:text、1:image、2:voice、3:vedio、4:music、5:news
     */
    private String chatMsgType;

    /**
     * 0:离线消息,1:历史消息
     */
    private String readType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(String chatMsg) {
        this.chatMsg = chatMsg;
    }
    public Long getChatFrom() {
        return chatFrom;
    }

    public void setChatFrom(Long chatFrom) {
        this.chatFrom = chatFrom;
    }
    public Long getChatTo() {
        return chatTo;
    }

    public void setChatTo(Long chatTo) {
        this.chatTo = chatTo;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }
    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }
    public String getChatMsgType() {
        return chatMsgType;
    }

    public void setChatMsgType(String chatMsgType) {
        this.chatMsgType = chatMsgType;
    }
    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
        "id=" + id +
        ", chatMsg=" + chatMsg +
        ", chatFrom=" + chatFrom +
        ", chatTo=" + chatTo +
        ", updateTime=" + updateTime +
        ", extras=" + extras +
        ", chatType=" + chatType +
        ", chatMsgType=" + chatMsgType +
        ", readType=" + readType +
        "}";
    }
}
