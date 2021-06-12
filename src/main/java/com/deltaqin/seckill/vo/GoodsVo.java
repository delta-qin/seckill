package com.deltaqin.seckill.vo;

import com.deltaqin.seckill.model.SeckillModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author deltaqin
 * @date 2021/6/12 上午11:14
 */
@Data
public class GoodsVo {
    private Integer goodId;

    private String goodName;

    private BigDecimal goodPrice;

    private String goodDescription;

    private Integer goodSales;

    private String goodImgUrl;

    private Integer stockCount;

    //private SeckillModel seckillModel;

    private Integer secKillId;

    private String startDate;

    private BigDecimal secKillPrice;

    //记录商品是否在秒杀活动中，以及对应的状态0：表示没有秒杀活动，1表示秒杀活动待开始，2表示秒杀活动进行中
    // 0 的话是没有秒杀活动
    private Integer secKillStatus;
}
