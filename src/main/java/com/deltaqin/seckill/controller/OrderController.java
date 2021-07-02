package com.deltaqin.seckill.controller;

import com.deltaqin.seckill.common.constant.GlobalConstant;
import com.deltaqin.seckill.common.entities.ResultType;
import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.common.utils.CodeUtil;
import com.deltaqin.seckill.model.UserModel;
import com.deltaqin.seckill.mqutils.MqProducer;
import com.deltaqin.seckill.redisutils.jedis.RedisService;
import com.deltaqin.seckill.redisutils.keyprefix.SeckillKeyPrefix;
import com.deltaqin.seckill.redisutils.keyprefix.UserKeyPrefix;
import com.deltaqin.seckill.service.GoodsService;
import com.deltaqin.seckill.service.OrderService;
import com.deltaqin.seckill.service.SeckillService;
import com.google.common.util.concurrent.RateLimiter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

import static com.deltaqin.seckill.common.constant.GlobalConstant.CONTENT_TYPE_FORMED;

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

    @Autowired
    private RedisService redisService;

    @Autowired
    private MqProducer mqProducer;

    private ExecutorService executorService;

    private RateLimiter orderCreateRateLimiter;

    // 流控
    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(20);
        orderCreateRateLimiter = RateLimiter.create(300);
    }


    @ApiOperation(value = "创建订单测试接口", notes = "订单接口")
    @RequestMapping(value = "/createorder", method = RequestMethod.POST, consumes = {CONTENT_TYPE_FORMED})
    public ResultType createOrder(@RequestParam(name = "itemId") Integer goodsId,
                                  @RequestParam(name = "amount") Integer count,
                                  @RequestParam(name = "promoId", required = false) Integer secKillId
                                  , @RequestParam(name="promoToken",required = false)String secKillToken
                                  ) throws CommonExceptionImpl {
        // 实现令牌校验
        if (!orderCreateRateLimiter.tryAcquire()) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.RATE_LIMIT);
        }

        //// 获取到用户的登录信息（v1.0）session
        //boolean isLogin = (boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        //if (!isLogin) {
        //    throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN, "请先登录再下单");
        //}
        //
        //// 获取用户的登录信息（v1.0）session
        //UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");
        //if (userModel == null) {
        //    throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN, "请先登录再下单");
        //}

        // 获取到用户的登录信息（v2.0）redis
        String token = httpServletRequest.getHeader("token");
        //String token = httpServletRequest.getParameterMap().get("token")[0];
        //System.out.println(token);
        if(StringUtils.isEmpty(token)){
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }

        // 获取用户的登录信息（v2.0）redis
        UserModel userModel = redisService.get(UserKeyPrefix.getToken, token, UserModel.class);

        if (userModel == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN, "请先登录再下单");
        }

        if (secKillId != null) {
            //  校验秒杀令牌，v2.0实现
            String seckillTokenInRedis = redisService.get(SeckillKeyPrefix.getSeckillToken, token, String.class);
            if (seckillTokenInRedis == null) {
                throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "秒杀令牌有误");
            }

            if (!org.apache.commons.lang3.StringUtils.equals(seckillTokenInRedis, secKillToken)) {
                throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "秒杀令牌有误");
            }
        }

        // v3.0不需要这个了
        //// 队列化泄洪，只有20个线程可以执行，其余的都在线程池的阻塞队列里面。
        //Future<Object> future = executorService.submit(new Callable<Object>() {
        //    @Override
        //    public Object call() throws Exception {
        //        // 初始化流水
        //        // value = "1表示初始状态，2表示下单扣减库存成功，3表示下单回滚"
        //        // 在订单落库之后使用流水的id流水设置为2
        //        String stockLogId = goodsService.initStockLog(goodsId, count);
        //
        //        // 同步下单 +预减库存+ 异步减库存 v2.0
        //        //orderService.createOrder(userModel.getId(), goodsId, secKillId, count, s);
        //
        //        //  同步下单 并行 事务消息 + 预减库存 + 异步减库存 v3.0
        //        // 同步下单的时候是在创建订单之后发送一个扣减数据库消息，是同步执行的。
        //        // 但是事务消息可以实现 减数据库库存发消息 和 执行本地创建订单事务 并行 执行。更加节省时间
        //        boolean mqRes = mqProducer.transactionAsyncDecreaseStock(userModel.getId(), goodsId, secKillId, count, stockLogId);
        //        if (!mqRes){
        //            throw new CommonExceptionImpl(ExceptionTypeEnum.MQ_SEND_FAIL);
        //        }
        //        return null;
        //
        //    }
        //});
        //
        //try {
        //    future.get();
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //    throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR);
        //} catch (ExecutionException e) {
        //    e.printStackTrace();
        //    throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR);
        //}

        return ResultType.create(null);
    }

    @ApiOperation(value = "生成秒杀下单验证码测试接口", notes = "订单接口")
    @RequestMapping(value = "/generateverifycode", method = RequestMethod.GET)
    public void generateVerifyCode(HttpServletResponse httpServletResponse) throws CommonExceptionImpl, IOException {
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN);
        }
        UserModel userModel = redisService.get(UserKeyPrefix.getToken, token, UserModel.class);
        if (userModel == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN);
        }

        Map<String, Object> stringObjectMap = CodeUtil.generateCodeAndPic();
        redisService.set(SeckillKeyPrefix.getSeckillCode, String.valueOf(userModel.getId()),
                stringObjectMap.get("code"));
        ImageIO.write((RenderedImage) stringObjectMap.get("pic"), "jpeg", httpServletResponse.getOutputStream());

    }

    @ApiOperation(value = "生成秒杀下单令牌测试接口", notes = "订单接口")
    @RequestMapping(value = "/generatetoken",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    public ResultType generatetoken(@RequestParam(name="itemId")Integer goodsId,
                  @RequestParam(name="promoId")Integer seckillId,
                  @RequestParam(name="verifyCode")String verifyCode) throws CommonExceptionImpl {
        //获取用户的信息
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN);
        }
        // 验证用户的信息
        UserModel userModel = redisService.get(UserKeyPrefix.getToken, token, UserModel.class);
        if (userModel == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN);
        }

        String codeInRedis = redisService.get(SeckillKeyPrefix.getSeckillCode, token, String.class);
        if (StringUtils.isEmpty(codeInRedis)) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "非法请求，都没获取过验证码");
        }

        if (!codeInRedis.equalsIgnoreCase(verifyCode)) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "非法请求，验证码错误");
        }

        String newToken = seckillService.generateSecKillToken(seckillId, goodsId, userModel.getId());
        if (newToken == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "令牌生成失败");
        }

        return ResultType.create(token);

    }

}
