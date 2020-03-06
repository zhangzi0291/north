package com.north.sys.entity;


public class SysUserRole {
    private String id;
    private String userId;
    private String roleId;

    public SysUserRole setId(String id){
        this.id = id;
        return this;
    }
    public String getId(){
        return this.id;
    }

    public SysUserRole setUserId(String userId){
        this.userId = userId;
        return this;
    }
    public String getUserId(){
        return this.userId;
    }

    public SysUserRole setRoleId(String roleId){
        this.roleId = roleId;
        return this;
    }
    public String getRoleId(){
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