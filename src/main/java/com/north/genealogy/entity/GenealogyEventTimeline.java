package com.north.genealogy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 大事件时间线
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@TableName("genealogy_event_timeline")
public class GenealogyEventTimeline implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type=IdType.ID_WORKER_STR)
    private String id;

    /**
     * 家族id
     */
    private String genealogyId;

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
    public String getGenealogyId() {
        return genealogyId;
    }

    public void setGenealogyId(String genealogyId) {
        this.genealogyId = genealogyId;
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
        return "GenealogyEventTimeline{" +
        "id=" + id +
        ", genealogyId=" + genealogyId +
        ", eventContent=" + eventContent +
        ", eventTime=" + eventTime +
        ", eventRemark=" + eventRemark +
        "}";
    }
}
