# 实验室预约系统后端
***
版本依赖：
- JDK11
- SpringBoot 2.4.5 MySQL 8.0.23
- mybatis-plus 3.4.2 SpringSecurity 2.4.5
- knife4j-spring-boot-starter 3.0.0 国人编写的漂亮的ui界面的Swagger
---
### 2021/5/12
- 设计数据库表。
- 使用代码生成器快速生成 Pojo、Mapper、Mapper XML、Service、Controller 等各个模块的代码，极大的提升了开发效率。
### 2021/5/15
> 前后端约定好jwt格式 Bearer token
1. 编写token工具类。
   - 根据用户信息生成token
   - 从token中获取用户名
   - 判断token是否有效
   - 判断token是否可以被刷新
   - 刷新token
2. 创建公共返回对象。
3. 处理登录之后返回token。前端拿到token之后会按照约定好的格式把token放在请求头中，访问任何api接口都需要后端进行拦截认证，认证成功即可请求。
4. 注销功能。后端返回200的状态码，前端接受到状态码之后，直接在请求头中把token删除，在后续访问接口时将会被拦截器拦截，即可完成注销。
### 2021/5/16
1. 添加jwt登录授权过滤器。
2. 添加自定义未授权和未登录结果返回
3. 添加swagger2接口文档配置，添加swagger2第三方ui界面。
4. 处理在Swagger2中携带Token的方法，赋予Authorization
5. 编写验证码配置，生成图形验证码，配置 produces = "image/jpeg" 接口文档不再是乱码。
6. try with resources 自动关闭资源。将要关闭的资源放在try语句内，在try语句执行完后自动关闭资源。

~~**由于在swagger文档中勾选了使用bootstrap增强功能，导致1.9.6下的文档不能正确显示api接口信息。找了网上很多博客也还没有解决这个问题。暂时先把版本降到1.8.3，随之而来的问题就是验证码controller又变成了乱码，不能正确显示图片。不过在后台日志中还可以看到验证码，暂时不影响后续功能实现，等后期再解决一下这个问题。**~~
7. 实现根据用户id查询用户角色功能，完善登录逻辑。
8. 通过原生作者的gitee解决了上述问题。现在该项目已经和springboot整合，有了自己的启动器(starter)：knife4j-spring-boot-starter。引入新依赖后，问题全部解决。**通过此次排错的过程深深体会到去官网的重要性！**  
[下面贴一下他的gitee](https://gitee.com/xiaoym/swagger-bootstrap-ui-demo/tree/master)
```xml
<dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-spring-boot-starter</artifactId>
            <version>3.0.2</version>
        </dependency>
```