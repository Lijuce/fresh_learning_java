## SpringMVC数据响应之回写数据
- 关键注解@ResponseBody
1. 直接返回字符串形式
2. 返回对象或集合
    - 配置处理器映射器RequestMappingHandlerAdapter，搭配Jackson依赖包；
    - xml文件中进行配置自动注入，可返回json格式的数据
    - 具体地，参考代码(com/controller/HelloController.java)
3. 直接开启MVC注解驱动
   - SpringMVC各组件中，处理器映射器、处理器适配器、视图解析器称为SpringMVC三大组件。
   - 使用<mvc:annotation-driven>可自动加载RequestMappingHandlerAdapter（处理器适配器）和
   RequestMappingHandleMapping（处理器映射器）。
   - 使用<mvc:annotation-driven>默认底层会继承Jackson进行对象或集合的json格式字符串的转换。


