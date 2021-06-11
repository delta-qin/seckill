package com.deltaqin.seckill.dataobject;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class StockLog implements Serializable {
    private Integer stockLogId;

    private Integer itemId;

    private Integer itemStockCount;

    @ApiModelProperty(value = "1表示初始状态，2表示下单扣减库存成功，3表示下单回滚")
    private Short itemStatus;

    private static final long serialVersionUID = 1L;

    public Integer getStockLogId() {
        return stockLogId;
    }

    public void setStockLogId(Integer stockLogId) {
        this.stockLogId = stockLogId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemStockCount() {
        return itemStockCount;
    }

    public void setItemStockCount(Integer itemStockCount) {
        this.itemStockCount = itemStockCount;
    }

    public Short getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Short itemStatus) {
        this.itemStatus = itemStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", stockLogId=").append(stockLogId);
        sb.append(", itemId=").append(itemId);
        sb.append(", itemStockCount=").append(itemStockCount);
        sb.append(", itemStatus=").append(itemStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}