package com.deltaqin.seckill.model;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author deltaqin
 * @date 2021/6/12 上午8:54
 */
@Data
public class GoodsModel {
    private Integer goodId;

    @NotBlank(message = "商品名称不能为空")
    private String goodName;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格必须大于0")
    private BigDecimal goodPrice;

    @NotBlank(message = "商品描述信息不能为空")
    private String goodDescription;

    //商品的销量
    private Integer goodSales;

    @NotBlank(message = "商品图片信息不能为空")
    private String goodImgUrl;


    // 来自ItemStock
    @NotNull(message = "库存不能不填")
    private Integer stockCount;

    // 聚合模型，直接包含，如果不为空，说明有秒杀活动
    private SeckillModel seckillModel;

}
