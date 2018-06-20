package com.north.sys.entity;


public class SysUserRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;

    public SysUserRole setId(Integer id){
        this.id = id;
        return this;
    }
    public Integer getId(){
        return this.id;
    }

    public SysUserRole setUserId(Integer userId){
        this.userId = userId;
        return this;
    }
    public Integer getUserId(){
        return this.userId;
    }

    public SysUserRole setRoleId(Integer roleId){
        this.roleId = roleId;
        return this;
    }
    public Integer getRoleId(){
        return this.roleId;
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    	sb.append("SysUserRole[");
        sb.append("id=" + id + ", ");
        sb.append("userId=" + userId + ", ");
        sb.append("roleId=" + roleId + ", ");
    	sb.append("]");
        return sb.toString();
    }
}