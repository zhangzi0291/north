package com.north.genealogy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 家族成员
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@TableName("genealogy_person")
public class GenealogyPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String genealogyId;

    /**
     * 名称
     */
    private String genealogyName;

    /**
     * 性别
     */
    private String genealogySex;

    /**
     * 生日
     */
    private Date genealogyBirthday;

    /**
     * 死日
     */
    private Date genealogyDeadtime;

    /**
     * 人生经历
     */
    private String genealogyExperience;

    /**
     * 父辈id
     */
    private String parentId;

    private String spouseId;

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
    public String getGenealogyName() {
        return genealogyName;
    }

    public void setGenealogyName(String genealogyName) {
        this.genealogyName = genealogyName;
    }
    public String getGenealogySex() {
        return genealogySex;
    }

    public void setGenealogySex(String genealogySex) {
        this.genealogySex = genealogySex;
    }
    public Date getGenealogyBirthday() {
        return genealogyBirthday;
    }

    public void setGenealogyBirthday(Date genealogyBirthday) {
        this.genealogyBirthday = genealogyBirthday;
    }
    public Date getGenealogyDeadtime() {
        return genealogyDeadtime;
    }

    public void setGenealogyDeadtime(Date genealogyDeadtime) {
        this.genealogyDeadtime = genealogyDeadtime;
    }
    public String getGenealogyExperience() {
        return genealogyExperience;
    }

    public void setGenealogyExperience(String genealogyExperience) {
        this.genealogyExperience = genealogyExperience;
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }

    @Override
    public String toString() {
        return "GenealogyPerson{" +
        "id=" + id +
        ", genealogyId=" + genealogyId +
        ", genealogyName=" + genealogyName +
        ", genealogySex=" + genealogySex +
        ", genealogyBirthday=" + genealogyBirthday +
        ", genealogyDeadtime=" + genealogyDeadtime +
        ", genealogyExperience=" + genealogyExperience +
        ", parentId=" + parentId +
        ", spouseId=" + spouseId +
        "}";
    }
}
