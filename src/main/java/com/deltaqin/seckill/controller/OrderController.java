package com.deltaqin.seckill.controller;

import com.deltaqin.seckill.common.constant.GlobalConstant;
import com.deltaqin.seckill.common.entities.ResultType;
import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.model.UserModel;
import com.deltaqin.seckill.service.GoodsService;
import com.deltaqin.seckill.service.OrderService;
import com.deltaqin.seckill.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author deltaqin
 * @date 2021/6/12 上午9:34
 */
@Api(tags = "订单接口")
@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "{*}", allowCredentials = "true")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @ApiOperation(value = "创建订单测试接口", notes = "订单接口")
    @RequestMapping(value = "/createorder", method = RequestMethod.POST, consumes = {GlobalConstant.CONTENT_TYPE_FORMED})
    public ResultType createOrder(@RequestParam(name = "itemId") Integer goodsId,
                                  @RequestParam(name = "amount") Integer count,
                                  @RequestParam(name = "promoId", required = false) Integer secKillId
                                  ) throws CommonExceptionImpl {
        // TODO 实现令牌校验

        // 获取到用户的登录信息
        boolean isLogin = (boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (!isLogin) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN, "请先登录再下单");
        }

        // 获取用户的登录信息
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if (userModel == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN, "请先登录再下单");
        }

        if (secKillId != null) {
            // TODO 校验秒杀令牌，v2.0实现
        }

        // 初始化流水
        // value = "1表示初始状态，2表示下单扣减库存成功，3表示下单回滚"
        // 在订单落库之后使用流水的id流水设置为2
        String s = goodsService.initStockLog(goodsId, count);

        // 下单
        orderService.createOrder(userModel.getId(), goodsId, secKillId, count, s);

        return ResultType.create(null);
    }


}
