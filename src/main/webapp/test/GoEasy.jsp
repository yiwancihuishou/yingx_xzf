<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.5.js"></script>
<script>
    //初始化goeasy对象
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-39e920b889fe4ba7b7cea59710167de2", //替换为您的应用appkey
    });
    //接收消息
    goEasy.subscribe({
        channel: "xzf_channel", //替换为您自己的channel
        onMessage: function (message) {
            alert("Channel:" + message.channel + " content:" + message.content);
        }
    });
</script>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>GoEasy</title>
</head>
<body>

</body>
</html>