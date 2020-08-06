var sysUser = -1;
layui.use(['table', 'layer', 'jquery','laydate', 'fast','admin','laypage'], function() {
    var $ = layui.$,
    	layer = layui.layer,
        table = layui.table,
        fast = layui.fast,
        admin = layui.admin,
		laypage = layui.laypage; 
        $(document).on('click','#addUrl',function(){
					           layer.open({
					           type: 2, 
					           content: 'add',   
					           area: ['600px', '600px'],
					          resize:false
					       });
					     });
					
	var selectTab = {
		tableId: "selectAll_tab"
	};
	
	
	selectTab.initColumn = function () {
		return [
        	// 表头
           	[{
                    type: 'checkbox'
                }, 
                
                {
                title: '序号',
                align: 'center',
                type: 'numbers'
            	},

                {
                    field: 'mediaid',
                    sort: true,
                   align: 'center',
                    title: '媒体编号'
                },
                {
                    field: 'mname',
                    align: 'center',
                    sort: true,
                    title: '媒体名称'
                    },
                {
                    field: 'countrycode',
                    align: 'center',
                    sort: true,
                    title: '所属国家'
                },
                 {
                    field: 'provincecode',
                    align: 'center',
                    sort: true,
                    title: '所属省份'
                },
                 {
                    field: 'citycode',
                    align: 'center',
                    sort: true,
                    title: '所属城市'
                },
                 {
                    field: 'areacode',
                    align: 'center',
                    sort: true,
                    title: '所属地区'
                },
                 {
                    field: 'label',
                    align: 'center',
                    sort: true,
                    title: '标签'
                },
                {
                    align: 'center',
                    toolbar: '#Demo',
                    title: '规则配置'
                },
               	{
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#barDemo',
                    title: '操作'
                }
                	


            ]
        ]
	}	
	
					/**
					 * 渲染表格
					 */
					var selectTabResult = table.render({
						elem: '#' + selectTab.tableId,
						url: Feng.ctxPath + '/cjmediainfo/selectPage',
						page: true,
						height: "full-98",
						cellMinWidth: 100,
						limit: 15,
						limits: [15, 30, 60, 80, 100],
						cols: selectTab.initColumn()
					});				
   					
   					/**
					 * 点击查询按钮
					 */
   					selectTab.search = function (curr) {
   					table.reload(selectTab.tableId, {
						where : {
								'mname' : $("#mname").val(),
								'country' : $("#country").val(),
								'domfor' : $("#domfor").val()
							},
							page : {
								curr : curr// 重新从第 1 页开始
							}
						});
					};
					
					// 搜索按钮点击事件
					$('#selectAll').click(function () {
						selectTab.search(1);
					});
    				
    				//监听工具条
					table.on('tool(selectAll_tab)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
				      var data = obj.data; //获得当前行数据
					  var layEvent = obj.event; //获得 lay-event 对应的值
					  var tr = obj.tr; //获得当前行 tr 的DOM对象
					  if(layEvent === 'detail'){ //查看
					   layer.open({
					           type: 2, 
					           content: fast.ctxPath + "/cjmediainfo/edit?id=" + obj.data.mediaid,
					           area: ['600px', '600px'],
					          resize:false
					       });
					  } else if(layEvent === 'del') {
              			layer.confirm('真的删除行么', function (index) {
	                    $.ajax({
	                        url: fast.ctxPath + '/cjmediainfo/delete?id=' + obj.data.mediaid, // 数据接口
	                        type: "POST",
	                        data: data,
                        success: function(res){
	                            if (res.code='0') {
	                                //删除这一行
	                                obj.del();
	                                //关闭弹框
	                                layer.close(index);
	                                layer.msg("删除成功", {icon: 6});
	                            } else {
	                                layer.msg("删除失败", {icon: 5});
	                            }
	                      	  }
	                    	});
	                   	 return false;
	                		});
          	 			}else if(layEvent === 'edit'){ 
          	 				//编辑
          	 			 layer.open({
					           type: 2, 
					           content: fast.ctxPath + "/cjmediainfo/addEdit?id=" + obj.data.mediaid,
					           area: ['600px', '600px'],
					          resize:false
					       });
						 }else if(layEvent === 'configure'){ 
          	 				//编辑
          	 				layer.open({
					           type: 2, 
					           content: fast.ctxPath + "/cjmediainfo/addEdit?id=" + obj.data.mediaid,
					           area: ['600px', '600px'],
					          resize:false
					       });
						 }
			});
			
});


