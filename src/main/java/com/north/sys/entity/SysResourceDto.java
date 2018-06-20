package com.north.sys.entity;


import java.util.List;

public class SysResourceDto extends SysResource{

    private List<SysResourceDto> children;

    private Boolean checked;

    public String getTitle() {
        return this.getResourceName();
    }

    public List<SysResourceDto> getChildren() {
        return children;
    }

    public void setChildren(List<SysResourceDto> children) {
        this.children = children;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
//    public Boolean getLoading() {
//        return this.getChild().size()>0?false:null;
//    }
}