package com.north.genealogy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 家族
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
@TableName("genealogy_family")
public class GenealogyFamily implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 姓氏
     */
    private String genealogySurname;

    /**
     * 地区
     */
    private String genealogyArea;

    /**
     * 备注
     */
    private String genealogyRemark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getGenealogySurname() {
        return genealogySurname;
    }

    public void setGenealogySurname(String genealogySurname) {
        this.genealogySurname = genealogySurname;
    }
    public String getGenealogyArea() {
        return genealogyArea;
    }

    public void setGenealogyArea(String genealogyArea) {
        this.genealogyArea = genealogyArea;
    }
    public String getGenealogyRemark() {
        return genealogyRemark;
    }

    public void setGenealogyRemark(String genealogyRemark) {
        this.genealogyRemark = genealogyRemark;
    }

    @Override
    public String toString() {
        return "GenealogyFamily{" +
        "id=" + id +
        ", genealogySurname=" + genealogySurname +
        ", genealogyArea=" + genealogyArea +
        ", genealogyRemark=" + genealogyRemark +
        "}";
    }
}
