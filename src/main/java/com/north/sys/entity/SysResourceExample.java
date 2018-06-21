package com.north.sys.entity;

import com.north.base.Example;
import com.north.base.Page;

import java.util.ArrayList;
import java.util.List;

public class SysResourceExample implements Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    public SysResourceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
    
    public String getOrderByClause() {
        return orderByClause;
    }
    
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
    
    public boolean isDistinct() {
        return distinct;
    }
    
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }
    
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }
    
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }
    
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }
    
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }
    
    public void setPage(Page page) {
        this.page=page;
    }
    
    public Page getPage() {
        return page;
    }

	protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }
        
        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }
        
        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }
        
        public Criteria andidIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }
        public Criteria andidIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }
        public Criteria andidEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andidNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andidGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andidGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andidLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andidLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andidIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andidNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andidBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andidNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }
        
        public Criteria andidLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andidNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }
        public Criteria andResourceNameIsNull() {
            addCriterion("resource_name is null");
            return (Criteria) this;
        }
        public Criteria andResourceNameIsNotNull() {
            addCriterion("resource_name is not null");
            return (Criteria) this;
        }
        public Criteria andResourceNameEqualTo(String value) {
            addCriterion("resource_name =", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotEqualTo(String value) {
            addCriterion("resource_name <>", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThan(String value) {
            addCriterion("resource_name >", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThanOrEqualTo(String value) {
            addCriterion("resource_name >=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThan(String value) {
            addCriterion("resource_name <", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThanOrEqualTo(String value) {
            addCriterion("resource_name <=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameIn(List<String> values) {
            addCriterion("resource_name in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotIn(List<String> values) {
            addCriterion("resource_name not in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameBetween(String value1, String value2) {
            addCriterion("resource_name between", value1, value2, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotBetween(String value1, String value2) {
            addCriterion("resource_name not between", value1, value2, "resourceName");
            return (Criteria) this;
        }
        
        public Criteria andResourceNameLike(String value) {
            addCriterion("resource_name like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotLike(String value) {
            addCriterion("resource_name not like", value, "resourceName");
            return (Criteria) this;
        }
        public Criteria andResourceTypeIsNull() {
            addCriterion("resource_type is null");
            return (Criteria) this;
        }
        public Criteria andResourceTypeIsNotNull() {
            addCriterion("resource_type is not null");
            return (Criteria) this;
        }
        public Criteria andResourceTypeEqualTo(String value) {
            addCriterion("resource_type =", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotEqualTo(String value) {
            addCriterion("resource_type <>", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThan(String value) {
            addCriterion("resource_type >", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("resource_type >=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThan(String value) {
            addCriterion("resource_type <", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThanOrEqualTo(String value) {
            addCriterion("resource_type <=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIn(List<String> values) {
            addCriterion("resource_type in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotIn(List<String> values) {
            addCriterion("resource_type not in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeBetween(String value1, String value2) {
            addCriterion("resource_type between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotBetween(String value1, String value2) {
            addCriterion("resource_type not between", value1, value2, "resourceType");
            return (Criteria) this;
        }
        
        public Criteria andResourceTypeLike(String value) {
            addCriterion("resource_type like", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotLike(String value) {
            addCriterion("resource_type not like", value, "resourceType");
            return (Criteria) this;
        }
        public Criteria andResourceUrlIsNull() {
            addCriterion("resource_url is null");
            return (Criteria) this;
        }
        public Criteria andResourceUrlIsNotNull() {
            addCriterion("resource_url is not null");
            return (Criteria) this;
        }
        public Criteria andResourceUrlEqualTo(String value) {
            addCriterion("resource_url =", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotEqualTo(String value) {
            addCriterion("resource_url <>", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlGreaterThan(String value) {
            addCriterion("resource_url >", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlGreaterThanOrEqualTo(String value) {
            addCriterion("resource_url >=", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlLessThan(String value) {
            addCriterion("resource_url <", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlLessThanOrEqualTo(String value) {
            addCriterion("resource_url <=", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlIn(List<String> values) {
            addCriterion("resource_url in", values, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotIn(List<String> values) {
            addCriterion("resource_url not in", values, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlBetween(String value1, String value2) {
            addCriterion("resource_url between", value1, value2, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotBetween(String value1, String value2) {
            addCriterion("resource_url not between", value1, value2, "resourceUrl");
            return (Criteria) this;
        }
        
        public Criteria andResourceUrlLike(String value) {
            addCriterion("resource_url like", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotLike(String value) {
            addCriterion("resource_url not like", value, "resourceUrl");
            return (Criteria) this;
        }
        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }
        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }
        public Criteria andParentIdEqualTo(Integer value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Integer value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Integer value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Integer value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Integer value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Integer> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Integer> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Integer value1, Integer value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }
        
        public Criteria andParentIdLike(String value) {
            addCriterion("parent_id like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotLike(String value) {
            addCriterion("parent_id not like", value, "parentId");
            return (Criteria) this;
        }
        public Criteria andpermissionIsNull() {
            addCriterion("permission is null");
            return (Criteria) this;
        }
        public Criteria andpermissionIsNotNull() {
            addCriterion("permission is not null");
            return (Criteria) this;
        }
        public Criteria andpermissionEqualTo(String value) {
            addCriterion("permission =", value, "permission");
            return (Criteria) this;
        }

        public Criteria andpermissionNotEqualTo(String value) {
            addCriterion("permission <>", value, "permission");
            return (Criteria) this;
        }

        public Criteria andpermissionGreaterThan(String value) {
            addCriterion("permission >", value, "permission");
            return (Criteria) this;
        }

        public Criteria andpermissionGreaterThanOrEqualTo(String value) {
            addCriterion("permission >=", value, "permission");
            return (Criteria) this;
        }

        public Criteria andpermissionLessThan(String value) {
            addCriterion("permission <", value, "permission");
            return (Criteria) this;
        }

        public Criteria andpermissionLessThanOrEqualTo(String value) {
            addCriterion("permission <=", value, "permission");
            return (Criteria) this;
        }

        public Criteria andpermissionIn(List<String> values) {
            addCriterion("permission in", values, "permission");
            return (Criteria) this;
        }

        public Criteria andpermissionNotIn(List<String> values) {
            addCriterion("permission not in", values, "permission");
            return (Criteria) this;
        }

        public Criteria andpermissionBetween(String value1, String value2) {
            addCriterion("permission between", value1, value2, "permission");
            return (Criteria) this;
        }

        public Criteria andpermissionNotBetween(String value1, String value2) {
            addCriterion("permission not between", value1, value2, "permission");
            return (Criteria) this;
        }
        
        public Criteria andpermissionLike(String value) {
            addCriterion("permission like", value, "permission");
            return (Criteria) this;
        }

        public Criteria andpermissionNotLike(String value) {
            addCriterion("permission not like", value, "permission");
            return (Criteria) this;
        }
        public Criteria andstatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }
        public Criteria andstatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }
        public Criteria andstatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andstatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andstatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andstatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andstatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andstatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andstatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andstatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andstatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andstatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
        
        public Criteria andstatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andstatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }
        public Criteria andResourceIconIsNull() {
            addCriterion("resource_icon is null");
            return (Criteria) this;
        }
        public Criteria andResourceIconIsNotNull() {
            addCriterion("resource_icon is not null");
            return (Criteria) this;
        }
        public Criteria andResourceIconEqualTo(String value) {
            addCriterion("resource_icon =", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconNotEqualTo(String value) {
            addCriterion("resource_icon <>", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconGreaterThan(String value) {
            addCriterion("resource_icon >", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconGreaterThanOrEqualTo(String value) {
            addCriterion("resource_icon >=", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconLessThan(String value) {
            addCriterion("resource_icon <", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconLessThanOrEqualTo(String value) {
            addCriterion("resource_icon <=", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconIn(List<String> values) {
            addCriterion("resource_icon in", values, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconNotIn(List<String> values) {
            addCriterion("resource_icon not in", values, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconBetween(String value1, String value2) {
            addCriterion("resource_icon between", value1, value2, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconNotBetween(String value1, String value2) {
            addCriterion("resource_icon not between", value1, value2, "resourceIcon");
            return (Criteria) this;
        }
        
        public Criteria andResourceIconLike(String value) {
            addCriterion("resource_icon like", value, "resourceIcon");
            return (Criteria) this;
        }

        public Criteria andResourceIconNotLike(String value) {
            addCriterion("resource_icon not like", value, "resourceIcon");
            return (Criteria) this;
        }
        public Criteria andResourceOrderNumIsNull() {
            addCriterion("resource_order_num is null");
            return (Criteria) this;
        }
        public Criteria andResourceOrderNumIsNotNull() {
            addCriterion("resource_order_num is not null");
            return (Criteria) this;
        }
        public Criteria andResourceOrderNumEqualTo(Integer value) {
            addCriterion("resource_order_num =", value, "resourceOrderNum");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNumNotEqualTo(Integer value) {
            addCriterion("resource_order_num <>", value, "resourceOrderNum");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNumGreaterThan(Integer value) {
            addCriterion("resource_order_num >", value, "resourceOrderNum");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_order_num >=", value, "resourceOrderNum");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNumLessThan(Integer value) {
            addCriterion("resource_order_num <", value, "resourceOrderNum");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNumLessThanOrEqualTo(Integer value) {
            addCriterion("resource_order_num <=", value, "resourceOrderNum");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNumIn(List<Integer> values) {
            addCriterion("resource_order_num in", values, "resourceOrderNum");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNumNotIn(List<Integer> values) {
            addCriterion("resource_order_num not in", values, "resourceOrderNum");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNumBetween(Integer value1, Integer value2) {
            addCriterion("resource_order_num between", value1, value2, "resourceOrderNum");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNumNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_order_num not between", value1, value2, "resourceOrderNum");
            return (Criteria) this;
        }
        
        public Criteria andResourceOrderNumLike(String value) {
            addCriterion("resource_order_num like", value, "resourceOrderNum");
            return (Criteria) this;
        }

        public Criteria andResourceOrderNumNotLike(String value) {
            addCriterion("resource_order_num not like", value, "resourceOrderNum");
            return (Criteria) this;
        }
	}



	public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}