package com.deltaqin.seckill.controller;

import com.deltaqin.seckill.common.constant.GlobalConstant;
import com.deltaqin.seckill.common.entities.ResultType;
import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.common.validator.ValidationResult;
import com.deltaqin.seckill.common.validator.ValidatorUtil;
import com.deltaqin.seckill.model.GoodsModel;
import com.deltaqin.seckill.model.SeckillModel;
import com.deltaqin.seckill.service.GoodsService;
import com.deltaqin.seckill.service.SeckillService;
import com.deltaqin.seckill.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author deltaqin
 * @date 2021/6/12 上午11:07
 */
@Api(tags = "商品接口")
@RestController
@RequestMapping("/item")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private ValidatorUtil validatorUtil;


    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = GlobalConstant.CONTENT_TYPE_FORMED)
    @ApiOperation(value = "创建商品测试接口", notes = "商品接口")
    public ResultType createGoods(@RequestParam(name = "title") String name,
                                  @RequestParam(name = "description") String description,
                                  @RequestParam(name = "price")BigDecimal price,
                                  @RequestParam(name = "stock") Integer stock,
                                  @RequestParam(name = "imgUrl") String imgURL) throws CommonExceptionImpl {
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setGoodName(name);
        goodsModel.setGoodDescription(description);
        goodsModel.setGoodPrice(price);
        goodsModel.setGoodImgUrl(imgURL);
        goodsModel.setStockCount(stock);

        GoodsModel goods = goodsService.createGoods(goodsModel);
        GoodsVo goodsVo = convertGoodsVoFromModel(goods);

        return ResultType.create(goodsVo);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "获取商品详情测试接口", notes = "商品接口")
    public ResultType getGoods(@RequestParam(name = "id") Integer id) throws CommonExceptionImpl {
        GoodsModel goodsModel = goodsService.getById(id);
        if (goodsModel == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "商品不存在");
        }
        GoodsVo goodsVo = convertGoodsVoFromModel(goodsModel);
        return ResultType.create(goodsVo);
    }

    @ApiOperation(value = "获取商品列表测试接口", notes = "商品接口")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultType getGoodsList() throws CommonExceptionImpl {
        List<GoodsModel> goodsList = goodsService.getGoodsList();
        if (goodsList == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.UNKNOW_ERROR, "商品为列表为空");
        }
        List<GoodsVo> collect = goodsList.stream().map(item -> {
            GoodsVo goodsVo = convertGoodsVoFromModel(item);
            return goodsVo;
        }).collect(Collectors.toList());
        return ResultType.create(collect);
    }

    // TODO 活动发布前预热到缓存
    public ResultType publishSecKill(){
        return ResultType.create(null);
    }

    // TODO 增加秒杀活动
    @ApiOperation(value = "创建秒杀活动测试接口", notes = "商品接口")
    @RequestMapping(value = "/createSec", method = RequestMethod.POST)
    public ResultType createSecKill(@RequestParam(name = "name") String name,
                                    @RequestParam(name = "startTime")String startTime,
                                    @RequestParam(name = "endTime") String endTime,
                                    @RequestParam(name = "secPrice") BigDecimal secPrice,
                                    @RequestParam(name = "itemId") Integer itemId) throws CommonExceptionImpl {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime startDateTime= DateTime.parse(startTime, format);
        DateTime endDateTime= DateTime.parse(endTime, format);
        //DateTime startDateTime = new DateTime(startTime);
        //DateTime endDateTime = new DateTime(endTime);

        SeckillModel seckillModel = SeckillModel.builder().seckillName(name)
                .startTime(startDateTime)
                .endTime(endDateTime)
                .seckillPrice(secPrice)
                .itemId(itemId)
                .build();

        ValidationResult validate = validatorUtil.validate(seckillModel);
        if (validate.isHasError()) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, validate.getErrMsgString());
        }

        seckillService.createSecKill(seckillModel);
        return ResultType.create(null);
    }

    // 返回给用户的时候需要设置秒杀的信息如果有的话，没有就设置为0，前端就知道当前商品没有秒杀活动
    private GoodsVo convertGoodsVoFromModel(GoodsModel goods) {
        if (goods == null) return null;

        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goods, goodsVo);

        SeckillModel seckillModel = goods.getSeckillModel();
        if (seckillModel != null) {
            goodsVo.setSecKillStatus(seckillModel.getItemStatus().intValue());
            goodsVo.setSecKillPrice(seckillModel.getSeckillPrice());
            goodsVo.setSecKillId(seckillModel.getSecId());
            goodsVo.setStartDate(seckillModel.getStartTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            goodsVo.setSecKillStatus(0);
        }
        return goodsVo;
    }


}

