layui.use(['form', 'admin', 'ax', 'laydate','fast'], function () {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var fast = layui.fast;
	var admin = layui.admin;
	var form = layui.form;
	var laydate = layui.laydate;
	
	//表单验证
	form.verify({
	  webSiteName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value==""){
		        return '媒体名称不能为空';
		  }
	  	},
	  webSiteUrl:function(value, item){
		  	if(/[\u4E00-\u9FA5]/g.test(value)){
		  	    return '网址不能为中文';
		  		}else if(value==""){
		  		return '网址不能为空';
		  	 }
		  	},
	  host:function(value, item){
		  	if(/[\u4E00-\u9FA5]/g.test(value)){
		  	    return '域名不能为中文';
		  		}else if(value==""){
		  		return '域名不能为空';
		  	 }
		  	}	  	
		});      



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
                        layer.msg("添加失败",{icon:2});
                        return false;  
                    },              
                    error:function (data) {
                        
                    }           
                    }) ;         
                 return false;
            });   
	
});