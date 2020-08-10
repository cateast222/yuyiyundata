layui.use(['table', 'layer', 'jquery','laydate', 'fast','admin','laypage'], function() {
    var $ = layui.$,
    	layer = layui.layer,
        table = layui.table,
        fast = layui.fast,
        admin = layui.admin,
		laypage = layui.laypage; 
		//表格定义					
		var dataTab = {
			tableId: "dataTab"
		};
	
		//初始化表格
		dataTab.initColumn = function () {
		 return [ // 表头
           	 [{
                 type: 'checkbox'
                }, 
                {
                 title: '序号',
                 align: 'center',
                 type: 'numbers'
            	},
                {
                 field: 'moduleName',
                    align: 'center',
                    sort: true,
                    title: '频道名称'
                   },
                 {
                 field: 'subModuleUrl',
                 align: 'center',
                 sort: true,
                 title: '频道网址',
                 templet : function(d) {
						return '<a title="' + d.subModuleUrl
							+ '" style="color: #01AAED;" href="'
							+ d.subModuleUrl + '" target="_blank">'
							+ d.subModuleUrl + '</a>';
					}
                },
                 {
                 field: 'collectState',
                 align: 'center',
                 sort: true,
                 title: '采集状态'
                },
                {
                 field: 'state',
                 align: 'center',
                 sort: true,
                 title: '状态'
                },
               	{
                 fixed: 'right',
                 align: 'center',
                 toolbar: '#barDemo',
                 title: '操作'
                },
                {
                 align: 'center',
                 toolbar: '#Demo',
                 title: '规则配置'
                }
            ]
        ]
	}	
	
					/**
					 * 渲染表格
					 */
					var selectTabResult = table.render({
						elem: '#' + dataTab.tableId,
						url: Feng.ctxPath + '/datawebchannel/dataSelectPage',
						page: true,
						height: "full-98",
						cellMinWidth: 100,
						limit: 15,
						limits: [15, 30, 60, 80, 100],
						cols: dataTab.initColumn(),
						done: function(res, curr, count){
							$("[data-field='collectState']").children().each(function(){
						     if($(this).text()=='1'){
						      $(this).text("未采集")
						     }else if($(this).text()=='2'){
						      $(this).text("已采集")
						     }else if($(this).text()=='3'){
						      $(this).text("通过")
						     }else if($(this).text()=='4'){
						      $(this).text("正常")
						     }else if($(this).text()=='5'){
						      $(this).text("启用")
						     }else if($(this).text()=='6'){
						      $(this).text("暂停")
						     }else if($(this).text()=='7'){
						      $(this).text("废弃")
						     }
						    });
						    $("[data-field='state']").children().each(function(){
							 if($(this).text()=='1'){
						      $(this).text("启用")
						     }else if($(this).text()=='2'){
						      $(this).text("弃用")
						     }		   
						   });
						}
					});				
   					
   						/**
					     * 搜索框事件
					     */
					    dataTab.search = function (curr) {
					        var queryData = {};
					        queryData['moduleName'] = $("#moduleName").val();
					        queryData['subModuleUrl'] = $("#subModuleUrl").val();
					        table.reload(dataTab.tableId,{
					        where: queryData,
					        page : {
									curr : curr// 重新从第 1 页开始
								}
					        });
					    };
					 
					 // 搜索按钮点击事件
					 $('#selectAll').click(function () {
							dataTab.search(1);
					 });  
					    
					 //新增按钮点击事件   
					 $(document).on('click','#addUrl',function(){
					           layer.open({
					           type: 2, 
							   title:'新增数据',
					           content: 'dataAdd',   
					           area: ['450px', '370px'],
					          resize:false
					       });
					     });   

					
    				
    				//监听工具条
					table.on('tool(dataTab)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
				      var data = obj.data; //获得当前行数据
					  var layEvent = obj.event; //获得 lay-event 对应的值
					  var tr = obj.tr; //获得当前行 tr 的DOM对象
					   if(layEvent === 'del') {//删除
              			layer.confirm('真的删除行么', function (index) {
	                    $.ajax({
	                        url: fast.ctxPath + '/datawebchannel/dataDelete?id=' + obj.data.uuid, // 数据接口
	                        type: "POST",
	                        data: data,
                        success: function(res){
	                            if (res.code==200) {//res表示是否与服务器连接成功,200表示成功，500表示失败
	                                //删除这一行
	                                obj.del();
	                                //关闭弹框
	                                layer.close(index);
	                                layer.msg('删除成功！', {icon: 1,time:2000,shade:0.2});
	                                //表格重载
						            table.reload('dataTab');
	                            } else {
	                                layer.msg('删除失败！', {icon: 2,time:2000,shade:0.2});
	                            }
	                      	  }
	                    	});
	                   	 return false;
	                		});
          	 			}else if(layEvent === 'edit'){//编辑
          	 			 layer.open({
					           type: 2, 
							   title:'修改页面',
					           content: fast.ctxPath + "/datawebchannel/dataAddEdit?id=" + obj.data.uuid,
					           area: ['450px', '470px'],
					           resize:false
					       });
						 }else if(layEvent === 'details'){ 
	          	 				//详情配置
	          	 				layer.msg("详情配置",{icon : 6});
						 }else if(layEvent === 'channel'){ 
							 	//频道配置
							 	layer.msg("频道配置",{icon : 6});
						 }
					});
			
			
						//批量删除，#deleteBacth是删除按钮的id
						$("#deleteBacth").on('click',function () {
							 //获取选中状态
						     var checkStatus = table.checkStatus('dataTab');//dataTab是table的id
						      //console.log(checkStatus);
						     var selectCount =  checkStatus.data.length;
						     if(selectCount==0){
						         parent.layer.msg('请先选择要删除的数据行！', {icon: 2});
						         return ;
						     }
						     layer.confirm('真的要删除选中的项吗？',function(index){
				                layer.close(index);
				                   var ids="";
				                for(var i=0; i<selectCount; i++){
				                    ids = ids + "," + checkStatus.data[i].uuid;
				                }
						      //console.log(ids);
						     $.ajax({
						         url:fast.ctxPath + '/datawebchannel/dataDeleteBacth',
						         data:{"ids":ids},
						         type: 'post',
						         success: function (res) {
						             if(res.code==200){
						                 layer.msg('删除成功！', {icon: 1,time:2000,shade:0.2});
						                 table.reload('dataTab');
						             }else{
						                 layer.msg('删除失败！', {icon: 2,time:3000,shade:0.2});
						             }
						         }
						     })
						  })
						})
						
						
						//批量配置
						$("#configureBacth").on('click',function(){
							//获取选中状态
						     var checkStatus = table.checkStatus('dataTab');//dataTab是table的id
						      //console.log(checkStatus);
						     var selectCount =  checkStatus.data.length;
						     if(selectCount==0){
						         parent.layer.msg('请先选择需要配置的频道！', {icon: 2});
						         return ;
						     }
						     
						       layer.close(index);
				                   var ids="";
				                for(var i=0; i<selectCount; i++){
				                    ids = ids + "," + checkStatus.data[i].uuid;
				                }
				              
				              
						})			
						//批量更新  
						$("#updateBacth").on('click',function () {
							 //获取选中状态
						     var checkStatus = table.checkStatus('dataTab');//dataTab是table的id
						      //console.log(checkStatus);
						     var selectCount =  checkStatus.data.length;
						     if(selectCount==0){
						         parent.layer.msg('请先选择要修改的状态行！', {icon: 2});
						         return ;
						     }
						     layer.confirm('确定采集通过吗？',function(index){
				                layer.close(index);
				                   var ids="";
				                for(var i=0; i<selectCount; i++){
				                    ids = ids + "," + checkStatus.data[i].uuid;
				                }
						      //console.log(ids);
						     $.ajax({
						         url:fast.ctxPath + '/datawebchannel/dataUpdateBacth',
						         data:{"ids":ids},
						         type: 'post',
						         success: function (res) {
						             if(res.code==200){
						                 layer.msg('更新成功！', {icon: 1,time:2000,shade:0.2});
						                 table.reload('dataTab');
						             }else{
						                 layer.msg('更新失败！', {icon: 2,time:3000,shade:0.2});
						             }
						         }
						     })
						  })
						})			
					});
