## 解决springboot配置@ControllerAdvice不能捕获 NoHandlerFoundException 问题（也就是错误的路径不会显示自定义的异常）
不显示自定义的异常是因为没有抛出异常，而是处理器默认直接返回了404（看下面的源码，所以没有捕获的机会）

springboot的WebMvcAutoConfiguration会默认注册ResourceHttpRequestHandler。会在最后会匹配如下资源映射：/**
，找到了处理器不会抛出找不到异常

ResourceHttpRequestHandler的源码
``` 
public class ResourceHttpRequestHandler extends WebContentGenerator
		implements HttpRequestHandler, EmbeddedValueResolverAware, InitializingBean, CorsConfigurationSource {
    ...

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        // For very general mappings (e.g. "/") we need to check 404 first
        Resource resource = getResource(request);
        if (resource == null) {
            logger.debug("Resource not found");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
```
https://stackoverflow.com/questions/51048707/spring-boot-handling-nohandlerfoundexception


## 找不到文档的favicon.ico
添加一个资源处理器，告知资源路径和位置的对应关系
```
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        // https://blog.csdn.net/qq_38380025/article/details/84936466
        // 解决找不到图标，添加一个资源处理器来处理文件和位置的映射
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
```
https://blog.csdn.net/qq_38380025/article/details/84936466

## knife4j想要显示返回的验证码

默认返回的是乱码

1. @RequestMapping 位置添加 , produces = "image/jpeg")
```
httpServletResponse.setContentType("image/jpeg");
ImageIO.write((RenderedImage) map.get("pic"), "jpeg", httpServletResponse.getOutputStream());
```

## @Autowired HttpServletResponse报错
spring团队只让 HttpServletRequest 有自动装配功能，排除 HttpServletResponse。spring团队倡导把reqeust、response与其他实例分开。

将其作为方法参数，public void test(HttpServletResponse response){

## SpringBoot + Mybatis 控制台查看执行的SQL日志
配置文件新增以下配置
```
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

## 启动的时候使用外部的配置文件
```
# 不推荐，会覆盖jar里面的
--spring.config.location=“D:/xxx/system.properties”

# 推荐是互补，注意不可以写配置文件的名字，
--spring.config.additional-location=“D:/xxx/conf/”

# 实际使用
 --spring.profiles.active=dev --spring.config.additional-location=/home/xxx/conf/  >nohup.output  2>&1 &
```
https://blog.csdn.net/inthat/article/details/105240468



