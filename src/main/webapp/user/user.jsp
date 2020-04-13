<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        //初始化表格
        $("#userTable").jqGrid( {
            url : "${path}/user/queryByPage",//前台向后台返回 page 当前页 rows每页展示条数
            editurl:"${path}/user/edit",
            datatype : "json",                //后台向前台返回 page；当前页 rows 数据（List集合）total 总页数  records:总条数
            rowNum : 2,
            rowList : [ 2,5,10, 20, 30 ],
            pager : '#userPage',//工具栏
            viewrecords : true,//是否显示总条数
            styleUI:"Bootstrap",
            height:"auto",
            multiselect:true,
            autowidth:true,
            colNames : [ 'id', '用户名', '手机号', '头像', '签名','微信', '状态',"注册时间" ],
            colModel : [
                {name : 'id',width : 55},
                {name : 'username',editable:true,width : 50},
                {name : 'phone',editable:true,width : 70,align : "center"},
                {name : 'headImg',edittype:"file",editable:true,width :100,align : "center",
                    formatter:function(cellvalue,options,rowObject){
                        //return "<img src='${path}/upload/photo/"+cellvalue+"' width='150px' height='100px'/>"
                        return "<img src='"+cellvalue+"' width='150px' height='100px'/>"
                    }
                },
                {name : 'sign',editable:true,width : 80,align : "center"},
                {name : 'wechat',editable:true,width : 80,align : "center"},
                {name : 'status',width : 80,align : "center",
                    formatter:function(cellvalue,options,rowObject){
                        if(cellvalue==1){
                            return "<button onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn btn-success'>冻结</button>"
                        }else{
                            return "<button onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn btn-danger'>解除冻结</button>"
                        }

                    }
                },
                {name : 'createDate',width : 80,align : "center"}

            ]
        });

        //表格工具栏
        $("#userTable").jqGrid('navGrid', '#userPage',
            {edit : true,add : true,del : true,addtext:"添加",deltext:"删除",edittext:"修改"},
            {},//修改关闭
            {
                closeAfterAdd:true,//关闭对话框
                //文件上传
                afterSubmit:function(response){   //提交之后上传文件到本地
                   //异步文件上传  不会刷新页面
                    $.ajaxFileUpload({
                       url:"${path}/user/uploadUser",
                       type:"post",
                       dataType:"json",
                        fileElementId:"headImg",//上传文件的name
                       data:{id:response.responseText},
                       success:function () {
                            //刷新表单
                           $("#userTable").trigger("reloadGrid");
                       } 
                    })

                    //必须有返回值  否则模态框关不掉
                    return "close";
                }
            },//添加关闭
            {}//删除关闭
        );
        //发送验证码
        $("#basic-addon2").click(function(){

            var phone=$("#phoneInput").val();

            $.ajax({
                url:"${path}/user/sendPhoneCode",
                type:"post",
                data:{"phone":phone},
                success:function(message){
                    //刷新页面
                    alert(message)
                }
            });

        });

        $("#btnOut").click(function () {
            $.ajax({
                url:"${path}/user/outPutUserExcel",
                type:"post",
                success:function () {
                    alert("已导出至桌面成功")
                }
            })
        });

    });

    //修改状态
    function updateStatus(id,status){
        //根据ID修改状态
        //id status
        if(status==1){
            $.ajax({
                url:"${path}/user/edit",
                type:"post",
                data:{"id":id,"status":"0","oper":"edit"},
                success:function(){
                    //刷新页面
                    $("#userTable").trigger("reloadGrid")
                }
            });
        }else{
            $.ajax({
                url:"${path}/user/edit",
                type:"post",
                data:{"id":id,"status":"1","oper":"edit"},
            success:function(){
                //刷新页面
                $("#userTable").trigger("reloadGrid")
            }
        });
        }
    }



</script>
<%--初始化一个面板--%>
<div class="panel panel-info">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>
    <%--标签页--%>
    <div class="nav nav-tabs">
        <li class="active" ><a href="">用户信息</a></li>
    </div>

    <%--发送验证码--%>
    <div class="input-group" style="width: 400px;height: 30px;float: right">
        <input id="phoneInput" type="text" class="form-control" placeholder="请输入验证码" aria-describedby="basic-addon2">
        <span class="input-group-addon" id="basic-addon2">点击发送验证码</span>
    </div>


        <%--按钮--%>
        <div class="panel panel-body">
            <button class="btn btn-info" id="btnOut">导出用户信息</button>
        </div>


    <%--初始化表单--%>
    <table id="userTable" />
    
    <%--工具栏--%>
    <div id="userPage"></div>
</div>