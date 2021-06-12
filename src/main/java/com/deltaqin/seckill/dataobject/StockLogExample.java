package com.deltaqin.seckill.dataobject;

import java.util.ArrayList;
import java.util.List;

public class StockLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StockLogExample() {
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

        public Criteria andStockLogIdIsNull() {
            addCriterion("stock_log_id is null");
            return (Criteria) this;
        }

        public Criteria andStockLogIdIsNotNull() {
            addCriterion("stock_log_id is not null");
            return (Criteria) this;
        }

        public Criteria andStockLogIdEqualTo(String value) {
            addCriterion("stock_log_id =", value, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdNotEqualTo(String value) {
            addCriterion("stock_log_id <>", value, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdGreaterThan(String value) {
            addCriterion("stock_log_id >", value, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdGreaterThanOrEqualTo(String value) {
            addCriterion("stock_log_id >=", value, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdLessThan(String value) {
            addCriterion("stock_log_id <", value, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdLessThanOrEqualTo(String value) {
            addCriterion("stock_log_id <=", value, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdLike(String value) {
            addCriterion("stock_log_id like", value, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdNotLike(String value) {
            addCriterion("stock_log_id not like", value, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdIn(List<String> values) {
            addCriterion("stock_log_id in", values, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdNotIn(List<String> values) {
            addCriterion("stock_log_id not in", values, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdBetween(String value1, String value2) {
            addCriterion("stock_log_id between", value1, value2, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andStockLogIdNotBetween(String value1, String value2) {
            addCriterion("stock_log_id not between", value1, value2, "stockLogId");
            return (Criteria) this;
        }

        public Criteria andItemIdIsNull() {
            addCriterion("item_id is null");
            return (Criteria) this;
        }

        public Criteria andItemIdIsNotNull() {
            addCriterion("item_id is not null");
            return (Criteria) this;
        }

        public Criteria andItemIdEqualTo(Integer value) {
            addCriterion("item_id =", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotEqualTo(Integer value) {
            addCriterion("item_id <>", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThan(Integer value) {
            addCriterion("item_id >", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_id >=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThan(Integer value) {
            addCriterion("item_id <", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThanOrEqualTo(Integer value) {
            addCriterion("item_id <=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdIn(List<Integer> values) {
            addCriterion("item_id in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotIn(List<Integer> values) {
            addCriterion("item_id not in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdBetween(Integer value1, Integer value2) {
            addCriterion("item_id between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotBetween(Integer value1, Integer value2) {
            addCriterion("item_id not between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemStockCountIsNull() {
            addCriterion("item_stock_count is null");
            return (Criteria) this;
        }

        public Criteria andItemStockCountIsNotNull() {
            addCriterion("item_stock_count is not null");
            return (Criteria) this;
        }

        public Criteria andItemStockCountEqualTo(Integer value) {
            addCriterion("item_stock_count =", value, "itemStockCount");
            return (Criteria) this;
        }

        public Criteria andItemStockCountNotEqualTo(Integer value) {
            addCriterion("item_stock_count <>", value, "itemStockCount");
            return (Criteria) this;
        }

        public Criteria andItemStockCountGreaterThan(Integer value) {
            addCriterion("item_stock_count >", value, "itemStockCount");
            return (Criteria) this;
        }

        public Criteria andItemStockCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_stock_count >=", value, "itemStockCount");
            return (Criteria) this;
        }

        public Criteria andItemStockCountLessThan(Integer value) {
            addCriterion("item_stock_count <", value, "itemStockCount");
            return (Criteria) this;
        }

        public Criteria andItemStockCountLessThanOrEqualTo(Integer value) {
            addCriterion("item_stock_count <=", value, "itemStockCount");
            return (Criteria) this;
        }

        public Criteria andItemStockCountIn(List<Integer> values) {
            addCriterion("item_stock_count in", values, "itemStockCount");
            return (Criteria) this;
        }

        public Criteria andItemStockCountNotIn(List<Integer> values) {
            addCriterion("item_stock_count not in", values, "itemStockCount");
            return (Criteria) this;
        }

        public Criteria andItemStockCountBetween(Integer value1, Integer value2) {
            addCriterion("item_stock_count between", value1, value2, "itemStockCount");
            return (Criteria) this;
        }

        public Criteria andItemStockCountNotBetween(Integer value1, Integer value2) {
            addCriterion("item_stock_count not between", value1, value2, "itemStockCount");
            return (Criteria) this;
        }

        public Criteria andItemStatusIsNull() {
            addCriterion("item_status is null");
            return (Criteria) this;
        }

        public Criteria andItemStatusIsNotNull() {
            addCriterion("item_status is not null");
            return (Criteria) this;
        }

        public Criteria andItemStatusEqualTo(Short value) {
            addCriterion("item_status =", value, "itemStatus");
            return (Criteria) this;
        }

        public Criteria andItemStatusNotEqualTo(Short value) {
            addCriterion("item_status <>", value, "itemStatus");
            return (Criteria) this;
        }

        public Criteria andItemStatusGreaterThan(Short value) {
            addCriterion("item_status >", value, "itemStatus");
            return (Criteria) this;
        }

        public Criteria andItemStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("item_status >=", value, "itemStatus");
            return (Criteria) this;
        }

        public Criteria andItemStatusLessThan(Short value) {
            addCriterion("item_status <", value, "itemStatus");
            return (Criteria) this;
        }

        public Criteria andItemStatusLessThanOrEqualTo(Short value) {
            addCriterion("item_status <=", value, "itemStatus");
            return (Criteria) this;
        }

        public Criteria andItemStatusIn(List<Short> values) {
            addCriterion("item_status in", values, "itemStatus");
            return (Criteria) this;
        }

        public Criteria andItemStatusNotIn(List<Short> values) {
            addCriterion("item_status not in", values, "itemStatus");
            return (Criteria) this;
        }

        public Criteria andItemStatusBetween(Short value1, Short value2) {
            addCriterion("item_status between", value1, value2, "itemStatus");
            return (Criteria) this;
        }

        public Criteria andItemStatusNotBetween(Short value1, Short value2) {
            addCriterion("item_status not between", value1, value2, "itemStatus");
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