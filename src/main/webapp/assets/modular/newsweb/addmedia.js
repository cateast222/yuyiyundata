layui.use(['form', 'admin', 'ax', 'laydate','fast'], function () {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var fast = layui.fast;
	var admin = layui.admin;
	var form = layui.form;
	var laydate = layui.laydate;



	// 表单提交事件
	form.on('submit(btnSubmit)', function (data) {
		  console.log(data)
		  $.ajax({   
                url:fast.ctxPath + "/newsweb/insertMedia",
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
	
		 $(function() {
		        $.ajax({
		            url: fast.ctxPath + '/newsweb/selectUser', // 数据接口
		            type: "POST",
		            data: null,
		            success: function (res) {//res表示是否与服务器连接成功
		                    var jsons=res.data;
		                    console.info(res.data);
		                    $("#createBy").val(jsons[0].name);
		            },
		            error: function (e) {
		
		            }
		        });
		    });
					
});