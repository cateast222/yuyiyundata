layui.use(['form', 'admin', 'ax', 'laydate','fast'], function () {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var fast = layui.fast;
	var admin = layui.admin;
	var form = layui.form;
	var laydate = layui.laydate;


	// 获取详情信息，填充表单
	var ajax = new $ax(Feng.ctxPath + "/site/updateById");
	ajax.set({
		uuid: $("#uuid").val(),
	});
	var result = ajax.start();
	console.info(result);
	var json = result;
	form.val('SiteForm', result.data);

	// 表单提交事件
	form.on('submit(btnSubmit)', function (data) {
		console.log(data)
		  $.ajax({   
                url:fast.ctxPath + "/site/updatesite",       
                method:'post',       
                data:data.field,        
                dataType:'JSON',         
                success:function(res){       
                     if(res.code='0'){       
                        admin.putTempData('formOk', true);
						// 关掉对话框
						admin.closeThisDialog();
						parent.location.reload();
                        }                
                    else            
                        alert(res.msg);   
                    },              
                    error:function (data) {
                        
                    }           
                    }) ;         
                 return false;
            }); 
});