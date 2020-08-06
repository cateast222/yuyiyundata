/**
 * 详情对话框
 */
var SiteInfoDlg = {
    data: {
        website_sub_name: "",
        website_sub_url: "",
        sub_host: "",
        language: "",
        state: "",
        proxy:""
    }
};

layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    admin.iframeAuto();
    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/site/add", function (data) {
            Feng.success("添加成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });

    //返回按钮
    $("#backupPage").click(function () {
        window.location.href = Feng.ctxPath + "/site/list";
    });


      $('#website_name').blur(function () {
        var mediaName=$('#website_name').val();
        if (mediaName!=null && mediaName!=''){
            $.ajax({
                type:'GET', // 规定请求的类型（GET 或 POST）
                url:Feng.ctxPath + "/site/condition", // 请求的url地址
                dataType:'json', //预期的服务器响应的数据类型
                data:{
                   "medianame": mediaName
                },//规定要发送到服务器的数据
                beforeSend:function(){ //发送请求前运行的函数（发送之前就会进入这个函数）
                    $('#block').hide();
                    // ....
                },
                success: function(result){ // 当请求成功时运行的函数
                    //...
                },
                error:function(result){ //失败的函数
                    if(result.responseJSON.code==500){
                        $('#block').show();
                    }
                },
                complete:function(){ //请求完成时运行的函数（在请求成功或失败之后均调用，即在 success 和 error 函数之后，不管成功还是失败 都会进这个函数）
                    // ...
                }
            });

        }
   });

    //父级字典时
    /*$('#parentName').click(function () {
        var formName = encodeURIComponent("parent.DictInfoDlg.data.parentName");
        var formId = encodeURIComponent("parent.DictInfoDlg.data.parentId");
        var treeUrl = encodeURIComponent("/dict/ztree?dictTypeId=" + $("#dictTypeId").val());

        layer.open({
            type: 2,
            title: '父级字典',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#parentId").val(DictInfoDlg.data.parentId);
                $("#parentName").val(DictInfoDlg.data.parentName);
            }
        });
    });*/
});