# 实验室预约系统后端
***
版本依赖：
- JDK11
- SpringBoot 2.4.5 MySQL 8.0.23 Redis 2.4.5
- mybatis-plus 3.4.2 SpringSecurity 2.4.5 jjwt 0.9.1
- knife4j-spring-boot-starter 3.0.2 国人编写的漂亮的ui界面的Swagger
- kaptcha 验证码 0.0.9
---
### 2021/6/7
1. 添加分页配置，完成对数据的分页处理。
### 2021/6/6
1. 引入aop包，添加日志切面，记录请求的地址，ip，类名.参数，参数信息。生成日志文件。
### 2021/6/2
1. 修改缓存过期bug，当增删改实验课程时，未更新("CoursesByTeacher:"+UserUtils.getCurrentUser().getId()) 这个缓存，其实是忘记删除这个缓存了。
2. 连接远程数据库，测试项目功能。
3. 添加教师查询指定课程的剩余可选学时，保证教师可以每周都在不同的教室上实验，从而使得预约功能更灵活，更人性化。
4. 在Redis序列化和反序列化 LocalDateTime 类型的对象时会报错，找不到它的类型，加上如下注解即可序列化成功。
```java
   @JsonDeserialize(using = LocalDateTimeDeserializer.class)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @TableField(updateStrategy = FieldStrategy.NEVER)
   private LocalDateTime createTime;

   @JsonDeserialize(using = LocalDateTimeDeserializer.class)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @TableField(updateStrategy = FieldStrategy.NEVER)
   private LocalDateTime updateTime;
```
5. 部署项目时发现bug：  Could not read JSON: Unrecognized field "enabled"。查阅资料发现只要是实体类中所有的有返回值的方法都会将返回的值序列化，但是反序列化时是根据set方法来实现的，所以当实体类中有非get，set方法的方法有返回值时，反序列化时就会出错。 由于整合了SpringSecurity所以实体类需要实现UserDetails接口，而那些属性则不能反序列化，所以在RedisConfig中加入一个配置。
```java
// 反序列化时遇到未知属性就忽略该属性。
om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
```
6. 添加异常统一处理Controller
### 2021/6/1
1. 连接远程Linux服务器Redis，编写Redis配置类，配置key序列化类型为String序列化，配置value序列化类型为Jackson序列化。
2. 重写各种获取信息的方法，加入缓存中间件，缓存信息列表，如果更改信息列表，则删除对应的缓存，下次拉取时再生成缓存。
### 2021/5/31
1. 添加根据课程id查询课程信息方法。
2. 整合前后端接口调用，修改小bug。
3. 根据实验时间可以自由选择(eg: 第一周周一第一节课，如果第二周周一第一节课有教师选择了的话，可以选择第二周除了周一第一节的任意时间)重写基于实验室名字查询实验室预约情况、预约实验室两个功能，使得预约时间更灵活。
### 2021/5/22
1. 完成基于实验室名字查询实验室预约情况。
2. 完成预约实验室功能，加入时间冲突的判断。
### 2021/5/20
1. 疑问？TeacherDTO可以有两个自定义类型的属性吗？一个User一个Course集合吗？sql测试结果是每个user只对应了一门课程，然后有多条数据。
2. 疑问？接口文档切换登录之后为什么要刷新一下 SpringSecurity 的上下文中才能切换用户信息？
3. 修改getCoursesByTeacherId方法，传入指定教师id，使得该登录老师也可以查看其余老师的实验课程。如果该老师只查看自己的实验课程，可不用传递教师id，SpringSecurity 的上下文中存储着登录的用户，可以直接从上下文中获取。
4. 完成基于课程id检索，过滤掉数量小于课程学生数实验室。
### 2021/5/19
1. 当前端传入不存在的用户名时，后端会抛用户名不在的异常，但是前端没有拿到RespBean，没有给出正确的弹窗信息，所以在后端捕获异常给出正确的返回结果，从而使得前端弹窗信息正确。
2. 创建教师的dto类，完成实验课程的CURD。基于教师id查询课程实验，查询所有课程实验。
### 2021/5/17
1. 完成教师信息的CURD。
2. 完成实验室信息的CURD。
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
### 2021/5/15
> 登录成功后返回tokenMap，前端根据tokenMap的tokenHead以及token和约定好的jwt格式来拼接token eg：Bearer token
1. 编写token工具类。
   - 根据用户信息生成token
   - 从token中获取用户名
   - 判断token是否有效
   - 判断token是否可以被刷新
   - 刷新token
2. 创建公共返回对象。
3. 处理登录之后返回token。前端拿到token之后会按照约定好的格式把token放在请求头中，访问任何api接口都需要后端进行拦截认证，认证成功即可请求。
4. 注销功能。后端返回200的状态码，前端接受到状态码之后，直接在请求头中把token删除，在后续访问接口时将会被拦截器拦截，即可完成注销。
### 2021/5/12
- 设计数据库表。
- 使用代码生成器快速生成 Pojo、Mapper、Mapper XML、Service、Controller 等各个模块的代码，极大的提升了开发效率。
