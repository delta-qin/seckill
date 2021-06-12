package com.deltaqin.seckill.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author deltaqin
 * @date 2021/6/12 上午10:44
 */
@Data
public class OrderWithStatusVO {
    private Integer orderId;

    private Integer userId;

    private Integer itemId;

    @ApiModelProperty(value = "一个时刻的当前价格")
    private BigDecimal itemPrice;

    private Integer itemCount;

    @ApiModelProperty(value = "单价*个数")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "秒杀商品的id")
    private Integer seckillId;

    //1表示初始状态，2表示下单扣减库存成功，3表示下单回滚
    private Integer orderStatus;
}
