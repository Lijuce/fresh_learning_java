## SSM框架整合
> 服务器开发，三层：
> - 表现层(SpringMVC)-负责与浏览器交互
> - 业务层(Spring)-负责业务逻辑编码
> - 持久层(MyBatis)-负责数据库系列操作

1. 单独编写Spring业务层框架代码及进行测试；
2. 单独编写SpringMVC表现层代码、页面及进行测试；
3. Spring整合SpringMVC
4. 单独编写Mybatis数据操作层代码，进行测试；
5. Spring整合Mybatis
    - 在applicationContext.xml文件配置
    - 而后，继续配置事务管理