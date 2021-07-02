package com.deltaqin.seckill.config;

import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * @author deltaqin
 * @date 2021/6/13 上午9:22
 */
// tomcat 调优

//当Spring容器内没有TomcatEmbeddedServletContainerFactory这个bean时，会此bean加载进spring容器中
//     新建自定义的WebServerFactoryCustomizer类来实现属性配置修改。
public class TomcatWebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        // 使用对应工厂类提供给我们的接口定制化我们的tomcat connector
        ((TomcatServletWebServerFactory)factory).addConnectorCustomizers(connector -> {
            Http11NioProtocol protocolHandler = (Http11NioProtocol)connector.getProtocolHandler();

            // 设置30秒内没有请求则服务端自动断开keepalive链接
            protocolHandler.setKeepAliveTimeout(30000);

            //当客户端发送超过10000个请求则自动断开keepalive链接
            protocolHandler.setMaxKeepAliveRequests(10000);
        });
    }
}
