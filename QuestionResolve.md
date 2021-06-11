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
