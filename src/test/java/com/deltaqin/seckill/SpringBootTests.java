package com.deltaqin.seckill;

import com.deltaqin.seckill.common.entities.ResultType;
import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.utils.CodeUtil;
import com.deltaqin.seckill.common.utils.Encode;
import com.deltaqin.seckill.common.utils.JodaTimeUtil;
import com.deltaqin.seckill.common.utils.MD5Util;
import com.deltaqin.seckill.controller.GoodsController;
import com.deltaqin.seckill.controller.OrderController;
import com.deltaqin.seckill.controller.UserController;
import com.deltaqin.seckill.dataobject.Good;
import com.deltaqin.seckill.service.GoodsService;
import com.deltaqin.seckill.service.OrderService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SecKillApplcation.class)
public class SpringBootTests {

    @Autowired
    private GoodsController goodsController;

    @Autowired
    private UserController userController;

    @Autowired
    private OrderController orderController;

    // 需要是静态方法
    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeClass");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass");
    }

    // 每次都要执行，不是静态的
    // 每次都要初始一个新数据
    @Before
    public void before() {
        System.out.println("before");

        // 初始化测试数据

    }

    @After
    public void after() {
        System.out.println("after");

        // 删除测试数据，不会有自己的垃圾数据

    }

    /**
     * 测试新增商品
     * @throws CommonExceptionImpl
     */
    @Test
    public void testGoodsAdd() throws CommonExceptionImpl {
        ResultType goods = goodsController.createGoods("小米手机", "超低价格", new BigDecimal(199),
                200, "https://img14.360buyimg.com/n7/jfs/t1/191284/33/6957/105187/60bdfe80Ebf1e331d/99189500b070a909.jpg");
        Assert.assertEquals(goods.getStatus(), "success");
    }

    @Test
    public void testGoodsSecKillAdd() throws CommonExceptionImpl {
        ResultType resultType = goodsController.createSecKill("小米降价", "2021-06-26 19:12:37", "2021-06-30 19:12:46",
                new BigDecimal(900), 20);
        Assert.assertEquals(resultType.getStatus(), "success");
    }

    @Test
    public void testGoodsListGet() throws CommonExceptionImpl {
        ResultType resultType = goodsController.getGoodsList();
        Assert.assertEquals(resultType.getStatus(), "success");
    }

    @Test
    public void testPublishSecKill() throws CommonExceptionImpl {
        ResultType resultType = goodsController.publishSecKill(20);
        Assert.assertEquals(resultType.getStatus(), "success");
    }

    @Test
    public void testGetUserByPhone() throws CommonExceptionImpl {
        ResultType resultType = userController.getUserByPhone("18534020960");
        Assert.assertEquals(resultType.getStatus(), "success");
    }

    @Test
    public void testGetUserById() throws CommonExceptionImpl {
        ResultType resultType = userController.getUserById(3);
        Assert.assertEquals(resultType.getStatus(), "success");
    }


    @Test
    public void testGenerateCodeAndPic()  {
        Map<String, Object> stringObjectMap = CodeUtil.generateCodeAndPic();
        Assert.assertNotNull(stringObjectMap);
    }

    @Test
    public void testPassEncode() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pass = Encode.encodeByMd5("deltaqin");
        Assert.assertNotNull(pass);
    }

    @Test
    public void testMD5Util() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pass = MD5Util.inputPassToDbPass("deltaqin", "salt11");
        Assert.assertNotNull(pass);
    }

    @Test
    public void JodaUtil() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String time = JodaTimeUtil.dateToStr(new Date());
        Assert.assertNotNull(time);
    }

}
