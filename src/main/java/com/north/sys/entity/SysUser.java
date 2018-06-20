package com.north.sys.entity;

import java.util.Date;
import java.util.List;

public class SysUser {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String mobile;
    private String email;
    private String status;
    private String salt;
    private Date expiredTime;
    private Date createTime;

	private List<SysRole> roleList;
	
    public SysUser setId(Integer id){
        this.id = id;
        return this;
    }
    public Integer getId(){
        return this.id;
    }

    public SysUser setUsername(String username){
        this.username = username;
        return this;
    }
    public String getUsername(){
        return this.username;
    }

    public SysUser setPassword(String password){
        this.password = password;
        return this;
    }
    public String getPassword(){
        return this.password;
    }

    public SysUser setName(String name){
        this.name = name;
        return this;
    }
    public String getName(){
        return this.name;
    }

    public SysUser setMobile(String mobile){
        this.mobile = mobile;
        return this;
    }
    public String getMobile(){
        return this.mobile;
    }

    public SysUser setEmail(String email){
        this.email = email;
        return this;
    }
    public String getEmail(){
        return this.email;
    }

    public SysUser setStatus(String status){
        this.status = status;
        return this;
    }
    public String getStatus(){
        return this.status;
    }

    public SysUser setSalt(String salt){
        this.salt = salt;
        return this;
    }
    public String getSalt(){
        return this.salt;
    }

    public SysUser setExpiredTime(Date expiredTime){
        this.expiredTime = expiredTime;
        return this;
    }
    public Date getExpiredTime(){
        return this.expiredTime;
    }

    public SysUser setCreateTime(Date createTime){
        this.createTime = createTime;
        return this;
    }
    public Date getCreateTime(){
        return this.createTime;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    	sb.append("SysUser[");
        sb.append("id=" + id + ", ");
        sb.append("username=" + username + ", ");
        sb.append("password=" + password + ", ");
        sb.append("name=" + name + ", ");
        sb.append("mobile=" + mobile + ", ");
        sb.append("email=" + email + ", ");
        sb.append("status=" + status + ", ");
        sb.append("salt=" + salt + ", ");
        sb.append("expiredTime=" + expiredTime + ", ");
        sb.append("createTime=" + createTime + ", ");
    	sb.append("]");
        return sb.toString();
    }
}