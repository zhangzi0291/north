package com.north.genealogy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 个人大事件时间线
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@TableName("genealogy_person_timeline")
public class GenealogyPersonTimeline implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type=IdType.ID_WORKER_STR)
    private String id;

    /**
     * 人id
     */
    private String personId;

    /**
     * 事件内容
     */
    private String eventContent;

    /**
     * 事件时间
     */
    private Date eventTime;

    /**
     * 时间备注
     */
    private String eventRemark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }
    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
    public String getEventRemark() {
        return eventRemark;
    }

    public void setEventRemark(String eventRemark) {
        this.eventRemark = eventRemark;
    }

    @Override
    public String toString() {
        return "GenealogyPersonTimeline{" +
        "id=" + id +
        ", personId=" + personId +
        ", eventContent=" + eventContent +
        ", eventTime=" + eventTime +
        ", eventRemark=" + eventRemark +
        "}";
    }
}
