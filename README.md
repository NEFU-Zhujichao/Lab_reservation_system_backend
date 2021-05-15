# 实验室预约系统后端
***
版本依赖：
- JDK11
- SpringBoot 2.4.5 MySQL 8.0.23
- mybatis-plus 3.4.2 SpringSecurity 2.4.5
- Swagger2 3.0.0 Swagger2第三方ui 1.9.6
---
### 2021/5/12
- 设计数据库表。
- 使用代码生成器快速生成 Pojo、Mapper、Mapper XML、Service、Controller 等各个模块的代码，极大的提升了开发效率。
### 2021/5/15
1. 编写token工具类。
   - 根据用户信息生成token
   - 从token中获取用户名
   - 判断token是否有效
   - 判断token是否可以被刷新
   - 刷新token
2. 创建公共返回对象。
3. 处理登录之后返回token。前端拿到token之后会把token放在请求头中，访问任何api接口都需要后端进行拦截认证，认证成功即可请求。
4. 注销功能。后端返回200的状态码，前端接受到状态码之后，直接在请求头中把token删除，在后续访问接口时将会被拦截器拦截，即可完成注销。
10. 添加swagger2接口文档配置，添加swagger2第三方ui界面。
