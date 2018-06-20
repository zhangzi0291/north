package com.north.base;

import java.util.List;

public class Page {
    private Integer limit = 10;
    private Integer offset = 0;
    private String order;
    private String search;
    private String sortCol;
    private List<String> sort;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public void setOrderBy(StringBuffer sql) {
        if (sort != null && sort.size() > 0) {
            sql.append(" order by ");
            for (int i = 0; i < sort.size(); i++) {
                sql.append(sort.get(i));
                if ((i + 1) < sort.size()) {
                    sql.append(",");
                } else {
                    sql.append(" ");
                }
            }
            sql.append(order);
        }
    }

    public void setPagination(StringBuffer sql) {
        if (limit != null && offset != null) {
            sql.append(" limit ? offset ?");
        }
    }

    public Integer getPageSize() {
        return this.limit;
    }

    public Integer getBegin() {
        return this.offset;
    }

    public String getSortCol() {
        return sortCol;
    }

    public void setSortCol(String sortCol) {
        this.sortCol = sortCol;
    }
}