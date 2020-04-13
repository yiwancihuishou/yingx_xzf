<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        //初始化表格
        pageInit();
    });

    //初始化表格的方法
    function pageInit() {
        //父表格
        $("#cateTable").jqGrid({
            url: "${path}/category/queryByPage?levels=1",
            editurl:"${path}/category/edit",
            datatype: "json",
            rowNum: 3,
            styleUI: "Bootstrap",
            height: "auto",
            autowidth: true,
            rowList: [8, 10, 20, 30],
            pager: '#catePage',
            viewrecords: true,
            multiselect:true,
            colNames: ['ID', '类别名', '级别'],
            colModel: [
                {name: 'id', index: 'id', width: 55},
                {name: 'cateName',editable:true, index: 'invdate', width: 90},
                {name: 'levels', index: 'name', width: 100},
            ],
            subGrid: true, //是否开启子表格
            //subgrid_id  当点击一行时会在父表格中创建一个div，用来容纳子表格的 subgrid_id为这个div的ID
            //row_id       点击行的ID
            subGridRowExpanded: function (subgridId, rowId) {//设置子表格相关的属性
                //调用子表格的方法
                addSubGrid(subgridId, rowId);
            }
        });
        //父表格的分页工具栏
        $("#cateTable").jqGrid('navGrid', '#catePage', {add: true, edit: true, del: true},
            {closeAfterEdit:true},//修改之后关闭
            {closeAfterAdd:true},//添加之后关闭
            {
                closeAfterDel:true,
                //提交之后的操作
                afterSubmit:function (response) {
                    //向警告框中放入内容
                    $("#showMsg").html(response.responseJSON.message);
                    //展示警告框
                    $("#deleteMsg").show();

                    //警告框自动关闭
                    setTimeout(function () {
                        $("#deleteMsg").hide();
                    },3000);
                    return "success";
                }
            }//删除之后关闭

        );

    }
        //子表格的内容
        function addSubGrid(subgridId, rowId){


            var subgridTableId = subgridId+"Table";  //定义子表格Table的Id
            var pagerId =subgridId+"page";           //定义子表格工具栏的ID

            //在子表格容器中创建子表格和子表格分页工具栏
            $("#" + subgridId).html("<table id='"+subgridTableId+"'/><div id='"+pagerId+"'>")




            //子表格
            $("#" + subgridTableId).jqGrid(
                {

                    url:"${path}/category/queryByPage?id="+rowId,
                    editurl:"${path}/category/edit?parentId="+rowId,
                    datatype : "json",
                    rowNum : 20,
                    pager : "#"+pagerId,
                    styleUI:"Bootstrap",
                    height : "auto",
                    autowidth:true,
                    multiselect:true,
                    rowList: [2, 10, 20, 30],
                    viewrecords: true,
                    colNames: ['ID', '类别名', '级别','上级类别ID'],
                    colModel: [
                        {name: 'id', index: 'id', width: 55},
                        {name: 'cateName',editable:true, index: 'invdate', width: 90},
                        {name: 'levels', index: 'name', width: 100},
                        {name: 'parentId', index: 'name', width: 100},
                    ],
                });


            //子表格的分页工具栏
           $("#" + subgridTableId).jqGrid('navGrid',
                "#" + pagerId, {
                    edit : true,
                    add : true,
                    del : true
                },
               {closeAfterEdit:true},//修改
               {closeAfterAdd:true},//添加
               {
                   closeAfterDel:true,//关闭模态框
                    //提交之后的操作
                   afterSubmit:function (response) {
                       //向警告框中放入内容
                       $("#showMsg").html(response.responseJSON.message);
                       //展示警告框
                       $("#deleteMsg").show();

                       //警告框自动关闭
                        setTimeout(function () {
                            $("#deleteMsg").hide();
                        },3000);
                       return "success";
                   }

               }//删除


                );
        }


</script>
<%--初始化一个面板--%>
<div class="panel panel-info">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>类别信息</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <%--<li class="active" ><a href="">类别信息</a></li>--%>

    <%--警告框--%>
    <div id="deleteMsg" class="alert alert-warning alert-dismissible"  role="alert" style="width: 300px;display: none">

        <span id="showMsg"></span>
    </div>

    <%--初始化表单--%>
    <table id="cateTable" />

    <%--工具栏--%>
    <div id="catePage"></div>
</div>