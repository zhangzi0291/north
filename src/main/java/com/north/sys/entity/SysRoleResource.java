package com.north.sys.entity;


public class SysRoleResource {
    private Integer id;
    private Integer roleId;
    private Integer resourceId;

    public SysRoleResource setId(Integer id){
        this.id = id;
        return this;
    }
    public Integer getId(){
        return this.id;
    }

    public SysRoleResource setRoleId(Integer roleId){
        this.roleId = roleId;
        return this;
    }
    public Integer getRoleId(){
        return this.roleId;
    }

    public SysRoleResource setResourceId(Integer resourceId){
        this.resourceId = resourceId;
        return this;
    }
    public Integer getResourceId(){
        return this.resourceId;
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    	sb.append("SysRoleResource[");
        sb.append("id=" + id + ", ");
        sb.append("roleId=" + roleId + ", ");
        sb.append("resourceId=" + resourceId + ", ");
    	sb.append("]");
        return sb.toString();
    }
}