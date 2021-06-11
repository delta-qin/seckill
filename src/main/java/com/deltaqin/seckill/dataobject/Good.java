package com.deltaqin.seckill.dataobject;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class Good implements Serializable {
    private Integer goodId;

    private String goodName;

    private BigDecimal goodPrice;

    private String goodDescription;

    private Integer goodSales;

    private String goodImgUrl;

    private static final long serialVersionUID = 1L;

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodDescription() {
        return goodDescription;
    }

    public void setGoodDescription(String goodDescription) {
        this.goodDescription = goodDescription;
    }

    public Integer getGoodSales() {
        return goodSales;
    }

    public void setGoodSales(Integer goodSales) {
        this.goodSales = goodSales;
    }

    public String getGoodImgUrl() {
        return goodImgUrl;
    }

    public void setGoodImgUrl(String goodImgUrl) {
        this.goodImgUrl = goodImgUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodId=").append(goodId);
        sb.append(", goodName=").append(goodName);
        sb.append(", goodPrice=").append(goodPrice);
        sb.append(", goodDescription=").append(goodDescription);
        sb.append(", goodSales=").append(goodSales);
        sb.append(", goodImgUrl=").append(goodImgUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}