layui.use(['form', 'admin', 'ax', 'laydate','fast'], function () {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var fast = layui.fast;
	var admin = layui.admin;
	var form = layui.form;
	var laydate = layui.laydate;


	// 获取详情信息，填充表单
	var ajax = new $ax(Feng.ctxPath + "/datawebchannel/dataSelectById");
	    ajax.set({
		  uuid: $("#uuid").val(),
	});
	var result = ajax.start();
		form.val('dataEditForm', result.data);

	// 表单提交事件
	form.on('submit(btnSubmit)', function (data) {
		  $.ajax({   
                url:fast.ctxPath + "/datawebchannel/dataUpdate",       
                method:'post',       
                data:data.field,        
                dataType:'JSON',         
                success:function(res){       
                     if(res.code=='200'){       
                        admin.putTempData('formOk', true);
						// 关掉对话框
						admin.closeThisDialog();
						parent.location.reload();
                        }                
                    else            
                        layer.msg("url已经存在",{icon:5});
                        return false;
                    },              
                    error:function (data) {
                        
                    }           
                    }) ;         
                 return false;
            }); 
            
           $(function() {
		        $.ajax({
		            url: fast.ctxPath + '/datawebchannel/dataSelectUser', // 数据接口
		            type: "POST",
		            data: null,
		            success: function (res) {//res表示是否与服务器连接成功
		                    var jsons=res.data;
		                    $("#updateBy").val(jsons.name);
		            },
		            error: function (e) {
		
		            }
		        });
		    });
					    
});