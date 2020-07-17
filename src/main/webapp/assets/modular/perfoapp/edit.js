var sysUser = -1;
layui.use(['table', 'layer', 'jquery','laydate', 'fast'], function() {
	var layer = layui.layer,
		table = layui.table,
		$ = layui.jquery,
		fast = layui.fast;

	var laydate = layui.laydate;

	var times;
	//年月范围
	laydate.render({
		elem: '#dateTime'
		,type: 'month'
		,range: true
		,done: function(value, date, endDate){

		}
	});

	//月份
	laydate.render({
		elem: '#ckMonth'
		,format: 'yyyyMMdd'
		,type: 'month'
	});

	//日期
	laydate.render({
		elem: '#insDate'
		,format: 'yyyy-MM-dd HH:mm:ss'
		,type: 'datetime'
	});

	$(function(){
		$('.focus').bind('input propertychange', function() {
			//上级
			var a = $('#Wk_ACHIEVES').val();
			var b = $('#WKLOADS').val();
			var c = $('#WK_CIENS').val();
			var d = $('#EXE_FORCES').val();
			var e = $('#JOB_SKILLSS').val();
			var f = $('#CPS_RIGS').val();
			var g = $('#TEAMWORKS').val();
			var h = $('#SE_DUTYS').val();
			var PLANNS = $('#PLANNS').val();
			var i = $('#INI_ENTS').val();
			var j = $('#COM_QUALITYS').val();
			var k = $('#BS_POSS').val();
			var l = $('#BS_REDCS').val();

			//个人
			var a1 = $('#Wk_ACHIEVE').val();
			var b1 = $('#WKLOAD').val();
			var c1 = $('#WK_CIEN').val();
			var d1 = $('#EXE_FORCE').val();
			var e1 = $('#JOB_SKILLS').val();
			var f1 = $('#CPS_RIG').val();
			var g1 = $('#TEAMWORK').val();
			var h1 = $('#SE_DUTY').val();
			var PLANNS1 = $('#PLANN').val();
			var i1 = $('#INI_ENT').val();
			var j1 = $('#COM_QUALITY').val();
			var k1 = $('#BS_POS').val();
			var l1 = $('#BS_REDC').val();

			var sum1= Number(a1)+Number(b1)+Number(c1)+Number(d1)+Number(e1)+Number(f1)+Number(g1)+Number(h1)+Number(i1)+Number(j1)+Number(k1)+Number(l1)+Number(PLANNS1);

			var sum= Number(a)+Number(b)+Number(c)+Number(d)+Number(e)+Number(f)+Number(g)+Number(h)+Number(i)+Number(j)+Number(k)+Number(l)+Number(PLANNS);

            //上级
			$('#superiors').val(sum);

			//个人
		 var person=$('#person').val();

			//日常得分
			var   scores=$("#scores").val();
			//日常扣分
			var   penalty=$("#penalty").val();
			//总分
			var totalpoints=Number(sum)*0.7+Number(person)*0.3+Number(scores)+Number(penalty);
		   $("#totalpoints").val(totalpoints);
		});
	})

});

