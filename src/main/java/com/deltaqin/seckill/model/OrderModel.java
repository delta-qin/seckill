package com.deltaqin.seckill.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author deltaqin
 * @date 2021/6/12 上午9:01
 */
@Data
public class OrderModel {
    private String orderId;

    private Integer userId;

    private Integer itemId;

    @ApiModelProperty(value = "一个时刻的当前价格")
    private BigDecimal itemPrice;

    private Integer itemCount;

    @ApiModelProperty(value = "单价*个数")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "秒杀商品的id")
    private Integer seckillId;

//     不在订单里面维护订单状态是因为异步处理比较麻烦，所以直接只维护状态表即可。
//    用户想要的话可以使用VO来实现订单状态的查看
}
