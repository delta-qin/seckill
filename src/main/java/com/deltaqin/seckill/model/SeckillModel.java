package com.deltaqin.seckill.model;


import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import java.math.BigDecimal;


/**
 * @author deltaqin
 * @date 2021/6/12 上午8:59
 */
@Data
@Builder
public class SeckillModel {
    private Integer secId;

    @NotBlank(message = "活动名字不能为空")
    private String seckillName;

    //@NotBlank(message = "开始时间不能为空")
    private DateTime startTime;

    private DateTime endTime;

    private BigDecimal seckillPrice;

    private Integer itemId;

    // 这个不在数据库里面保存，是业务逻辑相关，直接使用当前时间计算之后返回秒杀的状态
    // 1 是未开始
    // 2 是正在进行
    // 3 是已经结束
    private Short itemStatus;
}
