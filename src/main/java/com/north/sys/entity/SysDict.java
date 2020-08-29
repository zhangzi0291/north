package com.north.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author NorthZX
 * @since 2020-07-01
 */
@TableName("sys_dict")
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * Key
     */
    private String dictKey;

    /**
     * Value
     */
    private String dictValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }
    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }
    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    @Override
    public String toString() {
        return "SysDict{" +
            "id=" + id +
            ", dictName=" + dictName +
            ", dictKey=" + dictKey +
            ", dictValue=" + dictValue +
        "}";
    }
}
