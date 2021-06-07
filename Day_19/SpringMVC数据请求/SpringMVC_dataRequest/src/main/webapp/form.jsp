<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/hello4" method="post">
        <input type="text" name="userList[0].name"><br/>
        <input type="text" name="userList[0].age"><br/>
        <input type="text" name="userList[1].name"><br/>
        <input type="text" name="userList[1].age"><br/>
        <input type="submit" value="提交">
    </form>
</body>
</html>
