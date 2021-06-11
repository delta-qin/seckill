package com.deltaqin.seckill.dataobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GoodExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GoodExample() {
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

        public Criteria andGoodIdIsNull() {
            addCriterion("good_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodIdIsNotNull() {
            addCriterion("good_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodIdEqualTo(Integer value) {
            addCriterion("good_id =", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdNotEqualTo(Integer value) {
            addCriterion("good_id <>", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdGreaterThan(Integer value) {
            addCriterion("good_id >", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("good_id >=", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdLessThan(Integer value) {
            addCriterion("good_id <", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdLessThanOrEqualTo(Integer value) {
            addCriterion("good_id <=", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdIn(List<Integer> values) {
            addCriterion("good_id in", values, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdNotIn(List<Integer> values) {
            addCriterion("good_id not in", values, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdBetween(Integer value1, Integer value2) {
            addCriterion("good_id between", value1, value2, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdNotBetween(Integer value1, Integer value2) {
            addCriterion("good_id not between", value1, value2, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodNameIsNull() {
            addCriterion("good_name is null");
            return (Criteria) this;
        }

        public Criteria andGoodNameIsNotNull() {
            addCriterion("good_name is not null");
            return (Criteria) this;
        }

        public Criteria andGoodNameEqualTo(String value) {
            addCriterion("good_name =", value, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameNotEqualTo(String value) {
            addCriterion("good_name <>", value, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameGreaterThan(String value) {
            addCriterion("good_name >", value, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameGreaterThanOrEqualTo(String value) {
            addCriterion("good_name >=", value, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameLessThan(String value) {
            addCriterion("good_name <", value, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameLessThanOrEqualTo(String value) {
            addCriterion("good_name <=", value, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameLike(String value) {
            addCriterion("good_name like", value, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameNotLike(String value) {
            addCriterion("good_name not like", value, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameIn(List<String> values) {
            addCriterion("good_name in", values, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameNotIn(List<String> values) {
            addCriterion("good_name not in", values, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameBetween(String value1, String value2) {
            addCriterion("good_name between", value1, value2, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodNameNotBetween(String value1, String value2) {
            addCriterion("good_name not between", value1, value2, "goodName");
            return (Criteria) this;
        }

        public Criteria andGoodPriceIsNull() {
            addCriterion("good_price is null");
            return (Criteria) this;
        }

        public Criteria andGoodPriceIsNotNull() {
            addCriterion("good_price is not null");
            return (Criteria) this;
        }

        public Criteria andGoodPriceEqualTo(BigDecimal value) {
            addCriterion("good_price =", value, "goodPrice");
            return (Criteria) this;
        }

        public Criteria andGoodPriceNotEqualTo(BigDecimal value) {
            addCriterion("good_price <>", value, "goodPrice");
            return (Criteria) this;
        }

        public Criteria andGoodPriceGreaterThan(BigDecimal value) {
            addCriterion("good_price >", value, "goodPrice");
            return (Criteria) this;
        }

        public Criteria andGoodPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("good_price >=", value, "goodPrice");
            return (Criteria) this;
        }

        public Criteria andGoodPriceLessThan(BigDecimal value) {
            addCriterion("good_price <", value, "goodPrice");
            return (Criteria) this;
        }

        public Criteria andGoodPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("good_price <=", value, "goodPrice");
            return (Criteria) this;
        }

        public Criteria andGoodPriceIn(List<BigDecimal> values) {
            addCriterion("good_price in", values, "goodPrice");
            return (Criteria) this;
        }

        public Criteria andGoodPriceNotIn(List<BigDecimal> values) {
            addCriterion("good_price not in", values, "goodPrice");
            return (Criteria) this;
        }

        public Criteria andGoodPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("good_price between", value1, value2, "goodPrice");
            return (Criteria) this;
        }

        public Criteria andGoodPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("good_price not between", value1, value2, "goodPrice");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionIsNull() {
            addCriterion("good_description is null");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionIsNotNull() {
            addCriterion("good_description is not null");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionEqualTo(String value) {
            addCriterion("good_description =", value, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionNotEqualTo(String value) {
            addCriterion("good_description <>", value, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionGreaterThan(String value) {
            addCriterion("good_description >", value, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("good_description >=", value, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionLessThan(String value) {
            addCriterion("good_description <", value, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionLessThanOrEqualTo(String value) {
            addCriterion("good_description <=", value, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionLike(String value) {
            addCriterion("good_description like", value, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionNotLike(String value) {
            addCriterion("good_description not like", value, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionIn(List<String> values) {
            addCriterion("good_description in", values, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionNotIn(List<String> values) {
            addCriterion("good_description not in", values, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionBetween(String value1, String value2) {
            addCriterion("good_description between", value1, value2, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodDescriptionNotBetween(String value1, String value2) {
            addCriterion("good_description not between", value1, value2, "goodDescription");
            return (Criteria) this;
        }

        public Criteria andGoodSalesIsNull() {
            addCriterion("good_sales is null");
            return (Criteria) this;
        }

        public Criteria andGoodSalesIsNotNull() {
            addCriterion("good_sales is not null");
            return (Criteria) this;
        }

        public Criteria andGoodSalesEqualTo(Integer value) {
            addCriterion("good_sales =", value, "goodSales");
            return (Criteria) this;
        }

        public Criteria andGoodSalesNotEqualTo(Integer value) {
            addCriterion("good_sales <>", value, "goodSales");
            return (Criteria) this;
        }

        public Criteria andGoodSalesGreaterThan(Integer value) {
            addCriterion("good_sales >", value, "goodSales");
            return (Criteria) this;
        }

        public Criteria andGoodSalesGreaterThanOrEqualTo(Integer value) {
            addCriterion("good_sales >=", value, "goodSales");
            return (Criteria) this;
        }

        public Criteria andGoodSalesLessThan(Integer value) {
            addCriterion("good_sales <", value, "goodSales");
            return (Criteria) this;
        }

        public Criteria andGoodSalesLessThanOrEqualTo(Integer value) {
            addCriterion("good_sales <=", value, "goodSales");
            return (Criteria) this;
        }

        public Criteria andGoodSalesIn(List<Integer> values) {
            addCriterion("good_sales in", values, "goodSales");
            return (Criteria) this;
        }

        public Criteria andGoodSalesNotIn(List<Integer> values) {
            addCriterion("good_sales not in", values, "goodSales");
            return (Criteria) this;
        }

        public Criteria andGoodSalesBetween(Integer value1, Integer value2) {
            addCriterion("good_sales between", value1, value2, "goodSales");
            return (Criteria) this;
        }

        public Criteria andGoodSalesNotBetween(Integer value1, Integer value2) {
            addCriterion("good_sales not between", value1, value2, "goodSales");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlIsNull() {
            addCriterion("good_img_url is null");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlIsNotNull() {
            addCriterion("good_img_url is not null");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlEqualTo(String value) {
            addCriterion("good_img_url =", value, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlNotEqualTo(String value) {
            addCriterion("good_img_url <>", value, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlGreaterThan(String value) {
            addCriterion("good_img_url >", value, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("good_img_url >=", value, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlLessThan(String value) {
            addCriterion("good_img_url <", value, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlLessThanOrEqualTo(String value) {
            addCriterion("good_img_url <=", value, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlLike(String value) {
            addCriterion("good_img_url like", value, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlNotLike(String value) {
            addCriterion("good_img_url not like", value, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlIn(List<String> values) {
            addCriterion("good_img_url in", values, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlNotIn(List<String> values) {
            addCriterion("good_img_url not in", values, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlBetween(String value1, String value2) {
            addCriterion("good_img_url between", value1, value2, "goodImgUrl");
            return (Criteria) this;
        }

        public Criteria andGoodImgUrlNotBetween(String value1, String value2) {
            addCriterion("good_img_url not between", value1, value2, "goodImgUrl");
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