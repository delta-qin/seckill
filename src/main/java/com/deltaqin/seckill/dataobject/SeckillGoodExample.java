package com.deltaqin.seckill.dataobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeckillGoodExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SeckillGoodExample() {
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

        public Criteria andSecIdIsNull() {
            addCriterion("sec_id is null");
            return (Criteria) this;
        }

        public Criteria andSecIdIsNotNull() {
            addCriterion("sec_id is not null");
            return (Criteria) this;
        }

        public Criteria andSecIdEqualTo(Integer value) {
            addCriterion("sec_id =", value, "secId");
            return (Criteria) this;
        }

        public Criteria andSecIdNotEqualTo(Integer value) {
            addCriterion("sec_id <>", value, "secId");
            return (Criteria) this;
        }

        public Criteria andSecIdGreaterThan(Integer value) {
            addCriterion("sec_id >", value, "secId");
            return (Criteria) this;
        }

        public Criteria andSecIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sec_id >=", value, "secId");
            return (Criteria) this;
        }

        public Criteria andSecIdLessThan(Integer value) {
            addCriterion("sec_id <", value, "secId");
            return (Criteria) this;
        }

        public Criteria andSecIdLessThanOrEqualTo(Integer value) {
            addCriterion("sec_id <=", value, "secId");
            return (Criteria) this;
        }

        public Criteria andSecIdIn(List<Integer> values) {
            addCriterion("sec_id in", values, "secId");
            return (Criteria) this;
        }

        public Criteria andSecIdNotIn(List<Integer> values) {
            addCriterion("sec_id not in", values, "secId");
            return (Criteria) this;
        }

        public Criteria andSecIdBetween(Integer value1, Integer value2) {
            addCriterion("sec_id between", value1, value2, "secId");
            return (Criteria) this;
        }

        public Criteria andSecIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sec_id not between", value1, value2, "secId");
            return (Criteria) this;
        }

        public Criteria andSeckillNameIsNull() {
            addCriterion("seckill_name is null");
            return (Criteria) this;
        }

        public Criteria andSeckillNameIsNotNull() {
            addCriterion("seckill_name is not null");
            return (Criteria) this;
        }

        public Criteria andSeckillNameEqualTo(String value) {
            addCriterion("seckill_name =", value, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameNotEqualTo(String value) {
            addCriterion("seckill_name <>", value, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameGreaterThan(String value) {
            addCriterion("seckill_name >", value, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameGreaterThanOrEqualTo(String value) {
            addCriterion("seckill_name >=", value, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameLessThan(String value) {
            addCriterion("seckill_name <", value, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameLessThanOrEqualTo(String value) {
            addCriterion("seckill_name <=", value, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameLike(String value) {
            addCriterion("seckill_name like", value, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameNotLike(String value) {
            addCriterion("seckill_name not like", value, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameIn(List<String> values) {
            addCriterion("seckill_name in", values, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameNotIn(List<String> values) {
            addCriterion("seckill_name not in", values, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameBetween(String value1, String value2) {
            addCriterion("seckill_name between", value1, value2, "seckillName");
            return (Criteria) this;
        }

        public Criteria andSeckillNameNotBetween(String value1, String value2) {
            addCriterion("seckill_name not between", value1, value2, "seckillName");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceIsNull() {
            addCriterion("seckill_price is null");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceIsNotNull() {
            addCriterion("seckill_price is not null");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceEqualTo(BigDecimal value) {
            addCriterion("seckill_price =", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceNotEqualTo(BigDecimal value) {
            addCriterion("seckill_price <>", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceGreaterThan(BigDecimal value) {
            addCriterion("seckill_price >", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("seckill_price >=", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceLessThan(BigDecimal value) {
            addCriterion("seckill_price <", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("seckill_price <=", value, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceIn(List<BigDecimal> values) {
            addCriterion("seckill_price in", values, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceNotIn(List<BigDecimal> values) {
            addCriterion("seckill_price not in", values, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("seckill_price between", value1, value2, "seckillPrice");
            return (Criteria) this;
        }

        public Criteria andSeckillPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("seckill_price not between", value1, value2, "seckillPrice");
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