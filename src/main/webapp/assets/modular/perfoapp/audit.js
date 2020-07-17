var sysUser = -1;
layui.use(['table', 'layer', 'jquery','laydate', 'fast'], function() {
	var layer = layui.layer,
		table = layui.table,
		$ = layui.jquery,
		fast = layui.fast;

	var laydate = layui.laydate;


	//




	var unrevisedHistoryTable = table.render({
		elem: '#unrevisedHistoryTable',
		method: 'post',
		url: fast.ctxPath + '/perfoAppraisal/unrevisedHistory', // 数据接口
		// where: {
		// 	'deptId': fast.getUrlParam('deptId')
		// },
		page: true, // 开启分页
		limit: 20,
		limits: [20, 50, 100, 200],
		cols: [
			[ // 表头
				{
					type: 'radio'
				}, {
					title: '序号',
					align: 'center',
					type: 'numbers'
				},

				{
					field: 'the_person',
					sort: true,
					align: 'center',
					title: '被审核人'
				 },
				{
					field: 'ck_month',
					sort: true,
					align: 'center',
					title: '考核月份'
				},
				{
					field: 'state',
					width: 150,
					align: 'center',
					sort: true,
					title: '状态',templet: function(d){
						if (d.state=='1'){
							return '<span style="color: #c00;">已审核</span>';
						}


					}
				},

			]
		]
	});

	var tableIns = table.render({
		elem: '#unrevisedAllTable',
		method: 'Get',
		url: fast.ctxPath + '/perfoAppraisal/unrevisedAll', // 数据接口
		// where: {
		// 	'sysUser': sysUser
		// },
		page: false, // 开启分页
		limit: 20,
		limits: [20, 50, 100, 200],
		toolbar: '#toolBar', // 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		cols: [
			[ // 表头
				{
					type: 'checkbox',
					align: 'center',
					fixed: 'left'
				}, {
					title: '序号',
					align: 'center',
					type: 'numbers'
				}, {
					field: 'the_person',
					align: 'center',
					sort: true,
					title: '被审核人'
				}, {
					field: 'department',
					align: 'center',
					sort: true,
					title: '所属部门'
				}, {
					field: 'ck_month',
					width: 150,
					align: 'center',
					sort: true,
					title: '考核月份'
				}, {
					field: 'name',
					width: 150,
					align: 'center',
					sort: true,
					title: '直属上级'
				}, {
					field: 'ins_date',
					width: 150,
					align: 'center',
					sort: true,
					title: '日期'
				},

				{
					field: 'state',
					width: 150,
					align: 'center',
					sort: true,
					title: '状态',templet: function(d){
						if (d.state=='0'){
							return '<span style="color: #c00;">未审核</span>';
						}
						else if (d.state=='0'){
							return '<span style="color: #c00;">未审核</span>';
						}

					}
				},

				{
					fixed: 'right',
					width: 150,
					align: 'center',
					toolbar: '#lineBar',
					title: '操作'
				}
			]
		]
	});

var datas={};
var a;
	var body;
	// 监听行工具事件
	table.on('tool(unrevisedAllTable)', function(obj) { // 注：tool
		// 是工具条事件名，test
		// 是 table
		// 原始容器的属性
		// lay-filter='对应的值'
		var data = obj.data, // 获得当前行数据
			layEvent = obj.event; // 获得 lay-event 对应的值
		 if (layEvent === 'edit') {
			// 修改
			layer.open({
				type: 2,
				title: '绩效审核',
				shadeClose: false,
				shade: 0.3,
				area: ['1800px', '800px'],
				content: fast.ctxPath + '/perfoAppraisal/edidUnrevised',
				btn: ['保存', '关闭',],
				success: function(layero, index) {
					body= layer.getChildFrame('body', index);
					var iframeWin = window[layero.find('iframe')[0]['name']];
					body.find("#thePerson").val(data.the_person);
					body.find("#department").val(data.department);
					body.find("#ckMonth").val(data.ck_month);
					body.find("#insDate").val(data.ins_date);
					body.find("#INS_PERSON").val(data.name);

					 var a=body.find('.focus').val();
					//直属上级
					body.find("#superior").val(data.name);
					//考核日期
					var now = new Date();
					var year = now.getFullYear();
					var month = now.getMonth()+1;
					var day = now.getDate();
					var datetime=year+"-"+month+"-"+day;
					body.find("#INS_DATES").val(datetime);

                   //个人
					var wk_perform;
					var wk_performs;
					//工作业绩


					if(JSON.parse(data.wk_perform)!=null){
						$.each(JSON.parse(data.wk_perform), function(idx, obj) {
						var	 Wk_ACHIEVE=body.find("#Wk_ACHIEVE").val(obj.Wk_ACHIEVE);
						var	WKLOAD=body.find("#WKLOAD").val(obj.WKLOAD);
						var WK_CIEN=body.find("#WK_CIEN").val(obj.WK_CIEN);

						var	Wk_ACHIEVES=body.find("#Wk_ACHIEVES").val(obj.Wk_ACHIEVES);
						var	WKLOADS=body.find("#WKLOADS").val(obj.WKLOADS);
						var	WK_CIENS=body.find("#WK_CIENS").val(obj.WK_CIENS);
						wk_perform=Number(obj.Wk_ACHIEVE)+Number(obj.WKLOAD)+Number(obj.WK_CIEN);
						wk_performs=Number(obj.Wk_ACHIEVES)+Number(obj.WKLOADS)+Number(obj.WK_CIENS);

						});
					}

					var se_ability;
					var se_abilityS;
					//工作能力

					if(JSON.parse(data.se_ability)!=null){
						$.each(JSON.parse(data.se_ability), function(idx, obj) {
						var EXE_FORCE=body.find("#EXE_FORCE").val(obj.EXE_FORCE);
						var PLANN=body.find("#PLANN").val(obj.PLANN);
						var JOB_SKILLS=body.find("#JOB_SKILLS").val(obj.JOB_SKILLS);
						var CPS_RIG=body.find("#CPS_RIG").val(obj.CPS_RIG);
						var EXE_FORCES=body.find("#EXE_FORCES").val(obj.EXE_FORCES);
						var PLANNS=body.find("#PLANNS").val(obj.PLANNS);
						var JOB_SKILLSS=body.find("#JOB_SKILLSS").val(obj.JOB_SKILLSS);
						var	CPS_RIGS=body.find("#CPS_RIGS").val(obj.CPS_RIGS);
							se_ability=Number(obj.EXE_FORCE)+Number(obj.PLANN)+Number(obj.JOB_SKILLS)+Number(obj.CPS_RIG);
							se_abilityS=Number(obj.EXE_FORCES)+Number(obj.PLANNS)+Number(obj.JOB_SKILLSS)+Number(obj.CPS_RIGS);
						});
					}

					var att_ward;
					var att_wardS;
					//工作态度

					if(data.att_ward!=null&&data.att_ward!=''){
						$.each(JSON.parse(data.att_ward), function(idx, obj) {
							var	TEAMWORK=body.find("#TEAMWORK").val(obj.TEAMWORK);
							var	SE_DUTY=body.find("#SE_DUTY").val(obj.SE_DUTY);
							var	INI_ENT=body.find("#INI_ENT").val(obj.INI_ENT);
							var	COM_QUALITY=body.find("#COM_QUALITY").val(obj.COM_QUALITY);
							var	TEAMWORKS=body.find("#TEAMWORKS").val(obj.TEAMWORKS);
							var	SE_DUTYS=body.find("#SE_DUTYS").val(obj.SE_DUTYS);
							var	INI_ENTS=body.find("#INI_ENTS").val(obj.INI_ENTS);
							var	COM_QUALITYS=body.find("#COM_QUALITYS").val(obj.COM_QUALITYS);
							att_ward=Number(obj.TEAMWORK)+Number(obj.SE_DUTY)+Number(obj.INI_ENT)+Number(obj.COM_QUALITY);
							att_wardS=Number(obj.TEAMWORKS)+Number(obj.SE_DUTYS)+Number(obj.INI_ENTS)+Number(obj.COM_QUALITYS);
						});
					}

					var exe_inspec;
					var exe_inspecS;
					//例外考核

					if(JSON.parse(data.exe_inspec)!=null){
						$.each(JSON.parse(data.exe_inspec), function(idx, obj) {
							var	BS_POS=body.find("#BS_POS").val(obj.BS_POS);
							var	BS_REDC=body.find("#BS_REDC").val(obj.BS_REDC);
							var	BS_POSS=body.find("#BS_POSS").val(obj.BS_POSS);
							var	BS_REDCS=body.find("#BS_REDCS").val(obj.BS_REDCS);
							exe_inspec=Number(obj.BS_POS)+Number(obj.BS_REDC);
							exe_inspecS=Number(obj.BS_POSS)+Number(obj.BS_REDCS);


						});
					}


					//出勤情况

					if(JSON.parse(data.att_stas)!=null){
						$.each(JSON.parse(data.att_stas), function(idx, obj) {
							var	ATT_DAY=body.find("#ATT_DAY").val(obj.ATT_DAY);
							var	ATT_LEAVE=body.find("#ATT_LEAVE").val(obj.ATT_LEAVE );
							var	ATT_HOLIDAY=body.find("#ATT_HOLIDAY").val(obj.ATT_HOLIDAY);
							var	B_LATE=body.find("#B_LATE").val(obj.B_LATE);
							var	ABSENT=body.find("#ABSENT").val(obj.ABSENT);
						});
					}
                  //总评

					if(data.overall!=null&&data.overall!=''){
						$.each(JSON.parse(data.overall), function(idx, obj) {
							var	RAN_POIN=body.find("#RAN_POIN").val(obj.RAN_POIN);
							var	OT_EVAL=body.find("#OT_EVAL").val(obj.OT_EVAL);

						//	var	INS_DATEs=body.find("#INS_DATEs").val(obj.INS_DATEs);

						});
					}
					//总分


					if(data.tatal!=null&&data.tatal!=''){
						$.each(JSON.parse(data.tatal), function(idx, obj) {
							var	personal=body.find("#personal").val(obj.personal);
							var	superiors=body.find("#superiors").val(obj.superiors);
							var	scores=body.find("#scores").val(obj.scores);
							var	penalty=body.find("#penalty").val(obj.penalty);

						});
					}

				 //个人评分
                  var   personage= Number(wk_perform)+Number(se_ability)+Number(att_ward)+Number(exe_inspec);
					 body.find("#person").val(personage);







				},
				/**
				 * 保存方法
				 * @param index
				 * @param layero
				 */
				yes: function(index, layero) {
					//工作业绩
					var wkperform=[];
					var wkperforms={};
					wkperforms={
						"Wk_ACHIEVE": body.find("#Wk_ACHIEVE").val(),
						"WKLOAD":body.find("#WKLOAD").val(),
						"WK_CIEN":body.find("#WK_CIEN").val(),
						"Wk_ACHIEVES":body.find("#Wk_ACHIEVES").val(),
						"WKLOADS": body.find("#WKLOADS").val(),
						"WK_CIENS":body.find("#WK_CIENS").val(),

					}
					wkperform.push(wkperforms);

					//工作能力
					var seability=[];
					var seabilityS={};
					seabilityS={
						"EXE_FORCE": body.find("#EXE_FORCE").val(),
						"PLANN":body.find("#PLANN").val(),
						"PLANNS":body.find("#PLANNS").val(),
						"JOB_SKILLS":body.find("#JOB_SKILLS").val(),
						"CPS_RIG":body.find("#CPS_RIG").val(),
						"EXE_FORCES": body.find("#EXE_FORCES").val(),
						"SE_DUTYS":body.find("#SE_DUTYS").val(),
						"JOB_SKILLSS":body.find("#JOB_SKILLSS").val(),
						"CPS_RIGS":body.find("#CPS_RIGS").val()
					}
					seability.push(seabilityS);

					//工作态度
					var attward=[];
					var attwards={};
					attwards={
						"TEAMWORK": body.find("#BS_POS").val(),
						"SE_DUTY":body.find("#SE_DUTY").val(),
						"INI_ENT":body.find("#INI_ENT").val(),
						"COM_QUALITY":body.find("#COM_QUALITY").val(),
						"TEAMWORKS": body.find("#TEAMWORKS").val(),
						"SE_DUTYS":body.find("#SE_DUTYS").val(),
						"INI_ENTS":body.find("#INI_ENTS").val(),
						"COM_QUALITYS":body.find("#COM_QUALITYS").val()
					}
					attward.push(attwards);

                   //例外考核
					var exeinspec=[];
					var exeinspecs={};
					exeinspecs={
						"BS_POS": body.find("#BS_POS").val(),
						"BS_REDC":body.find("#BS_REDC").val(),
						"BS_POSS":body.find("#BS_POSS").val(),
						"BS_REDCS":body.find("#BS_REDCS").val()
					}
					exeinspec.push(exeinspecs);


					//出勤情况
					var ATTSTAS=[];
					var ATTSTASS={};
					ATTSTASS={
						"ATT_DAY": body.find("#ATT_DAY").val(),
						"ATT_LEAVE":body.find("#ATT_LEAVE").val(),
						"ATT_HOLIDAY":body.find("#ATT_HOLIDAY").val(),
						"B_LATE":body.find("#B_LATE").val(),
						"ABSENT":body.find("#ABSENT").val()
					}
					ATTSTAS.push(ATTSTASS);

					//总评
					var overall=[];
					var overalls={};
					overalls={
						"RAN_POIN": body.find("#RAN_POIN").val(),
						"OT_EVAL":body.find("#OT_EVAL").val(),
						"INS_PERSON": body.find("#INS_PERSON").val(),
					//	"INS_DATEs":body.find("#INS_DATES").val(),

					}
					overall.push(overalls);

					//总分
					var tatal=[];
					var tatals={};
					tatals={
						"personal": body.find("#personal").val(),
						"superiors":body.find("#superiors").val(),
						"scores": body.find("#scores").val(),
						"penalty":body.find("#penalty").val(),
					}
					tatal.push(tatals);

					datas={
						"uuid":data.uuid,
						"wkPerform": JSON.stringify(wkperform),
						"seAbility":JSON.stringify(seability),
						"attWard":JSON.stringify(attward),
						"exeInspec":JSON.stringify(exeinspec),
						"attStas":JSON.stringify(ATTSTAS),
						"overall":JSON.stringify(overall),
						"tatal":JSON.stringify(tatal)
					}


					$.ajax({
						url:fast.ctxPath+"/perfoAppraisal/updatePerfo",
						type:"POST",
						data:datas,
						success:function(res){
							console.log(res);
							layer.msg(res.message, {icon: 1});
							layer.close(index);
							tableIns.search();
						},
						error:function(e){
						}
					});

				},
				end: function() {
				//	tableIns.search();
				}
			});
		}
	});


	// 审核人搜索
	$('#searchApiDataAuthBtn').on('click', function() {
		tableIns.search();
	});



	// 历史记录搜索
	$('#searchSysUserBtn').on('click', function() {
		unrevisedHistoryTable.search();
	});

	//数据权限表格加载
	tableIns.search = function() {
		tableIns.reload({
			where: {
				'thePerson': $('#thePerson').val()
			},
			page: {
				curr: 1
			}
		});
	}


	//历史记录表格加载
	unrevisedHistoryTable.search = function() {
		unrevisedHistoryTable.reload({
			where: {
				//'insDate':times,
				'thePerson': $('#thePersons').val()
			},
			page: {
				curr: 1
			}
		});
	}
});
