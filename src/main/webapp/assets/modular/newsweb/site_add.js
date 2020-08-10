layui.use(['layer', 'form', 'admin', 'ax','fast'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var fast = layui.fast;
    
    //表单验证
	form.verify({
	  websiteSubName: function(value, item){ //value：表单的值、item：表单的DOM对象
		    if(value==""){
		        return '网站名称不能为空';
		  }
	  	},
	  websiteSubUrl:function(value, item){
		  	if(/[\u4E00-\u9FA5]/g.test(value)){
		  	    return '网址不能为中文';
		  		}else if(value==""){
		  		return '网址不能为空';
		  	 }
		  	},
	  subHost:function(value, item){
		  	if(/[\u4E00-\u9FA5]/g.test(value)){
		  	    return '域名不能为中文';
		  		}else if(value==""){
		  		return '域名不能为空';
		  	 }
		  	}	  	
		});      
	
    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {	
    	 $.ajax({   
                url:fast.ctxPath + "/site/add",       
                method:'post',       
                data:data.field,        
                dataType:'JSON',         
                success:function(res){ 
                     if(res.code=='200'){       
                        admin.putTempData('formOk', true);
						// 关掉对话框
						admin.closeThisDialog();
						parent.location.reload()
                        }else{
                         layer.msg("网址已经存在",{icon:5})
                    	 return false;
                       }                
                    },              
                error:function (e) {
                    }           
                    }) ;         
                 return false;
    });

    //返回按钮
    $("#backupPage").click(function () {
        window.location.href = Feng.ctxPath + "/site/list";
    });
	
	         //查询媒体名称
		     $(function() {
		        $.ajax({
		            url: fast.ctxPath + '/site/SelectMediaName', // 数据接口
		            type: "POST",
		            data: null,
		            success: function (res) {//res表示是否与服务器连接成功
		                    var jsons=res.data;
		                    $("#websiteName").val(jsons.websiteName);
		            },
		        });
		    });
});