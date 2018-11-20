package com.north.genealogy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 家族成员
 * </p>
 *
 * @author NorthZX
 * @since 2018-10-10
 */
public class GenealogyPersonDto extends GenealogyPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<GenealogyPersonDto> child;

    private GenealogyPersonDto spouse;

    public List<GenealogyPersonDto> getChild() {
        if (child == null) {
            synchronized (List.class) {
                if (child == null) {
                    child = new ArrayList<>();
                }
            }
        }
        return child;
    }

    public void setChild(List<GenealogyPersonDto> child) {
        this.child = child;
    }

    public GenealogyPersonDto getSpouse() {
        return spouse;
    }

    public void setSpouse(GenealogyPersonDto spouse) {
        this.spouse = spouse;
    }

    @Override
    public String toString() {

        return super.toString()+" \n GenealogyPersonDto{" +
                "child=" + child +
                '}';
    }
}
