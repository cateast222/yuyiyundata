layui.use(['form', 'admin', 'ax', 'laydate','fast'], function () {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var fast = layui.fast;
	var admin = layui.admin;
	var form = layui.form;
	var laydate = layui.laydate;
	
	//表单验证
	form.verify({
	  moduleName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value==""){
		        return '频道名称不能为空';
		  }
	  	},
	  subModuleUrl:function(value, item){
		  	if(/[\u4E00-\u9FA5]/g.test(value)){
		  	    return 'url不能为中文';
		  		}else if(value==""){
		  		return '网址不能为空';
		  	 }
		  	}
		});      
	
	// 表单提交事件
	form.on('submit(btnSubmit)', function (data) {
		  $.ajax({   
                url:fast.ctxPath + "/datawebchannel/dataInsert",       
                method:'post',       
                data:data.field,        
                dataType:'JSON',         
                success:function(res){ 
                	 console.log(res);
                     if(res.code=='200'){       
                        admin.putTempData('formOk', true);
						// 关掉对话框
						admin.closeThisDialog();
						layer.msg("添加成功",{icon:6})
						parent.location.reload();
                        }else{
                         layer.msg("网址已经存在",{icon:5})
                    	 return false;
                       }                
                    },              
                error:function (e) {
                    	 var res = $.parseJSON(e.responseText);
               			 layer.msg(res.msg);
                    }           
                    }) ;         
                 return false;
            });   
		 	 //查询当前登录用户
			 $(function() {
			        $.ajax({
			            url: fast.ctxPath + '/datawebchannel/dataSelectUser', // 数据接口
			            type: "POST",
			            data: null,
			            success: function (res) {//res表示是否与服务器连接成功
			                    var jsons=res.data;
			                    $("#createBy").val(jsons.name);
			            },
			            error: function (e) {
			
			            }
			        });
			    });
		     //查询媒体名称和网站名称
		     $(function() {
		        $.ajax({
		            url: fast.ctxPath + '/datawebchannel/dataSelectWebeSiteName', // 数据接口
		            type: "POST",
		            data: null,
		            success: function (res) {//res表示是否与服务器连接成功
		                    var jsons=res.data;
		                    $("#websiteName").val(jsons.websiteName);
		                    $("#websiteSubName").val(jsons.websiteSubName);
		            },
		            error: function (e) {
		
		            }
		        });
		    });
					
});