<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script src="${path}/js/echarts.min.js"></script>
<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.5.js"></script>
<script type="text/javascript">
    //初始化goeasy对象
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-39e920b889fe4ba7b7cea59710167de2", //替换为您的应用appkey
    });


    $(function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // $.get("${path}/json/user.json",function (data) {
        //接收消息
        goEasy.subscribe({
            channel: "xzf_channel", //替换为您自己的channel
            onMessage: function (message) {
                //alert("Channel:" + message.channel + " content:" + message.content);

                //获取goeasy提供的数据
                var datas=message.content;
                //将json的字符串转成JavaScript对象
                var data=JSON.parse(datas);

                var option = {
                    title: {
                        text: 'ECharts 入门示例',  //标题
                        subtext: '测试'   //副标题
                    },
                    tooltip: {}, //鼠标提示
                    legend: {
                        data:['小男孩','小姑娘']//选项卡
                    },
                    xAxis: {
                        data: data.month
                    },
                    yAxis: {},//纵轴  自适应
                    series: [{
                        name: '小男孩',
                        type: 'bar',
                        data: data.boys
                    },{
                        name: '小姑娘',
                        type: 'bar',
                        data: data.girls
                    }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);


            }
        });



       // },"json");

    });

</script>


<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div align="center">
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>



</div>

</body>

</html>