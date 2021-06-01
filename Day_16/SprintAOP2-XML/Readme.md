## Spring AOP的XML配置
#### 意义：
> 主要学习如何通过XML方式对AOP进行配置

#### 具体配置过程：
1. 把通知bean交给spring管理；
2. 使用aop:config标签表开始AOP配置；
3. 在aop:config内部，使用aop:aspect表开始切面配置
    - id属性：为 切面 提供唯一标识
    -  ref属性： 指定通知类bean的ID
4. 在aop:aspect内部，使用对应标签配置通知类型；
    
    - 以下为示例：
        - aop:before 表前置通知类型
            - method属性：用于指定类bean中哪个方法是前置通知
            - pointcut属性：指定 切入点 表达式，该表达式含义即对业务层指定方法进行增强

            > 切入点表达式写法：
            >   - 关键字：execution
            >   - 格式：访问修饰符  返回值  包名.包名.包名...类名.方法名(参数列表)
            >   - 标准示例(注：该写法过于死板)：
            >       > public void com.service.impl.AccountServiceImpl.findAccount()
            >   - 一般示例(实际开发的通常写法)：
            >       > \* com.service.impl.\*.\*(..)