package com.north.sys.entity;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SysResource {
    private Integer id;
    private String resourceName;
    private String resourceType;
    private String resourceUrl;
    private Integer parentId;
    private String permission;
    private String status;
    private String resourceIcon;
    private Integer resourceOrderNum;

	List<SysResource> child = new ArrayList<>();
	
    public SysResource setId(Integer id){
        this.id = id;
        return this;
    }
    public Integer getId(){
        return this.id;
    }

    public SysResource setResourceName(String resourceName){
        this.resourceName = resourceName;
        return this;
    }
    public String getResourceName(){
        return this.resourceName;
    }

    public SysResource setResourceType(String resourceType){
        this.resourceType = resourceType;
        return this;
    }
    public String getResourceType(){
        return this.resourceType;
    }

    public SysResource setResourceUrl(String resourceUrl){
        this.resourceUrl = resourceUrl;
        return this;
    }
    public String getResourceUrl(){
        return this.resourceUrl;
    }

    public SysResource setParentId(Integer parentId){
        this.parentId = parentId;
        return this;
    }
    public Integer getParentId(){
        return this.parentId;
    }

    public SysResource setPermission(String permission){
        this.permission = permission;
        return this;
    }
    public String getPermission(){
        return this.permission;
    }

    public SysResource setStatus(String status){
        this.status = status;
        return this;
    }
    public String getStatus(){
        return this.status;
    }

    public SysResource setResourceIcon(String resourceIcon){
        this.resourceIcon = resourceIcon;
        return this;
    }
    public String getResourceIcon(){
        return this.resourceIcon;
    }

    public SysResource setResourceOrderNum(Integer resourceOrderNum){
        this.resourceOrderNum = resourceOrderNum;
        return this;
    }
    public Integer getResourceOrderNum(){
        return this.resourceOrderNum;
    }

    public List<SysResource> getChild() {
        return child;
    }

    public void setChild(List<SysResource> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    	sb.append("SysResource[");
        sb.append("id=" + id + ", ");
        sb.append("resourceName=" + resourceName + ", ");
        sb.append("resourceType=" + resourceType + ", ");
        sb.append("resourceUrl=" + resourceUrl + ", ");
        sb.append("parentId=" + parentId + ", ");
        sb.append("permission=" + permission + ", ");
        sb.append("status=" + status + ", ");
        sb.append("resourceIcon=" + resourceIcon + ", ");
        sb.append("resourceOrderNum=" + resourceOrderNum + ", ");
    	sb.append("]");
        return sb.toString();
    }
}