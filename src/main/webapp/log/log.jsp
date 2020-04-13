<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        //初始化表格
        $("#logTable").jqGrid( {
            url : "${path}/log/queryByPage",//前台向后台返回 page 当前页 rows每页展示条数
            datatype : "json",                //后台向前台返回 page；当前页 rows 数据（List集合）total 总页数  records:总条数
            rowNum : 10,
            rowList : [10, 20, 30 ],
            pager : '#logPage',//工具栏
            viewrecords : true,//是否显示总条数
            styleUI:"Bootstrap",
            height:"auto",
            multiselect:true,
            autowidth:true,
            colNames : [ '日志id', '管理员名', '操作时间', '操作类型', '操作结果'],
            colModel : [
                {name : 'id',width : 55,align : "center"},
                {name : 'adminName',width : 50,align : "center"},
                {name : 'date',width : 70,align : "center"},
                {name : 'operation',width : 80,align : "center"},
                {name : 'status',width : 80,align : "center"},


            ]
        });

        //表格工具栏
        $("#logTable").jqGrid('navGrid', '#logPage',
            {edit : false,add : false,del : false}


        );

        $("#btnOut").click(function () {
            $.ajax({
                url:"${path}/log/outPutLogExcel",
                type:"post",
                success:function () {
                    alert("已导出至桌面成功")
                }
            })
        });

    });




</script>
<%--初始化一个面板--%>
<div class="panel panel-info">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>日志信息</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active" ><a href="">用户信息</a></li>
    </div>



        <%--按钮--%>
     <button class="btn btn-info" id="btnOut">导出日志信息</button>

    <%--初始化表单--%>
    <table id="logTable" />
    
    <%--工具栏--%>
    <div id="logPage"></div>
</div>