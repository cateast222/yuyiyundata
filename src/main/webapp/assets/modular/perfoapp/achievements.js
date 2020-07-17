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
            var time=value; //得到日期生成的值，如：2017-08-18
            times=time;
            console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
            console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
        }
    });

    //考核日期
    laydate.render({
        elem: '#insDate'
    });
    //日期
    laydate.render({
        elem: '#INS_DATES'
    });
    //考核月份
    laydate.render({
        elem: '#ckMonth'
    });

    var selectSelf = table.render({
        elem: '#selectSelf',
        method: 'post',
        url: fast.ctxPath + '/perfoAppraisal/selectSelf', // 数据接口
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
                    field: 'ckMonth',
                    sort: true,
                    align: 'center',
                    title: '年月'
                },
                {
                    field: 'thePerson',
                    width: 150,
                    align: 'center',
                    sort: true,
                    title: '被审核人'
                    },
                {
                    field: 'department',
                    width: 150,
                    align: 'center',
                    sort: true,
                    title: '所在部门'
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






    // 监听行工具事件
    table.on('tool(lineBar)', function(obj) { // 注：tool
        // 是工具条事件名，test
        // 是 table
        // 原始容器的属性
        // lay-filter='对应的值'
        var data = obj.data, // 获得当前行数据
            layEvent = obj.event; // 获得 lay-event 对应的值
        if (layEvent === 'select') {
            $.ajax({
                url: fast.ctxPath + '/perfoAppraisal/selectById', // 数据接口
                type: "POST",
                data: data,
                success: function (res) {//res表示是否与服务器连接成功
                    var jsons=res.data;
                    console.info(res.data);
                    console.info(JSON.parse(jsons[0].wkPerform))
                    $('#thePerson').val(jsons[0].thePerson);
                    $('#department').val(jsons[0].department);
                    $('#ckMonth').val(jsons[0].ckMonth);
                    $('#superior').val(jsons[0].superior);
                    $('#insDate').val(jsons[0].insDate);
                    if(JSON.parse(jsons[0].wkPerform)!=null){
                        $.each(JSON.parse(jsons[0].wkPerform), function(idx, obj) {
                            $('#Wk_ACHIEVE').val(obj.Wk_ACHIEVE);
                            $('#WKLOAD').val(obj.WKLOAD);
                            $('#WK_CIEN').val(obj.WK_CIEN);
                        });
                    }
                    if(JSON.parse(jsons[0].seAbility)!=null){
                        $.each(JSON.parse(jsons[0].seAbility), function(idx, obj) {
                            $('#EXE_FORCE').val(obj.EXE_FORCE);
                            $('#PLANN').val(obj.PLANN);
                            $('#JOB_SKILLS').val(obj.JOB_SKILLS);
                            $('#CPS_RIG').val(obj.CPS_RIG);
                        });
                    }
                    if(JSON.parse(jsons[0].attWard)!=null){
                        $.each(JSON.parse(jsons[0].attWard), function(idx, obj) {
                            $('#TEAMWORK').val(obj.TEAMWORK);
                            $('#SE_DUTY').val(obj.SE_DUTY);
                            $('#INI_ENT').val(obj.INI_ENT);
                            $('#COM_QUALITY').val(obj.COM_QUALITY);
                        });
                    }
                    if(JSON.parse(jsons[0].exeInspec)!=null){
                        $.each(JSON.parse(jsons[0].exeInspec), function(idx, obj) {
                            $('#BS_POS').val(obj.BS_POS);
                            $('#BS_REDC').val(obj.BS_REDC);
                        });
                    }
                    if(JSON.parse(jsons[0].attStas)!=null){
                        $.each(JSON.parse(jsons[0].attStas), function(idx, obj) {
                            $('#ATT_DAY').val(obj.ATT_DAY);
                            $('#ATT_LEAVE').val(obj.ATT_LEAVE);
                            $('#ATT_HOLIDAY').val(obj.ATT_HOLIDAY);
                            $('#OVERTIME').val(obj.OVERTIME);
                            $('#B_LATE').val(obj.B_LATE);
                            $('#ABSENT').val(obj.ABSENT);
                        });
                    }
                    if(JSON.parse(jsons[0].overall)!=null){
                        $.each(JSON.parse(jsons[0].overall), function(idx, obj) {
                            $('#RAN_POIN').val(obj.RAN_POIN);
                            $('#OT_EVAL ').val(obj.OT_EVAL);
                            $('#INS_PERSON').val(obj.INS_PERSON);
                            $('#INS_DATES').val(obj.INS_DATES);
                        });
                    }
                    if(JSON.parse(jsons[0].tatal)!=null){
                        $.each(JSON.parse(jsons[0].tatal), function(idx, obj) {
                            $('#person').val(obj.person);
                            $('#superiors').val(obj.superiors);
                            $('#scores').val(obj.scores);
                            $('#penalty').val(obj.penalty);
                        });
                    }

                },
                error: function (e) {

                }
            });
        }
    });


    // 历史记录搜索
    $('#searchSysUserBtn').on('click', function() {
        selectSelf.search();
    });




    //历史记录表格加载
    selectSelf.search = function() {
        selectSelf.reload({
            where: {
                'insDate':times,
                'thePerson': $('#thePersons').val()
            },
            page: {
                curr: 1
            }
        });
    }

    $('#btnSubmit').click(function() {
        var sa=[];
        var sb=[];
        var sc=[];
        var sd=[];
        var se=[];
        var sf=[];
        var dataa = {};
        var datab = {};
        var datac = {};
        var datad = {};
        var datae = {};
        var dataf = {};

        //ajax请求地址
        var options = $("#superior option:selected");　　　　//获取选中项

        var day = options.val();　　　　　　　　　　　　　　//获取选中项的值

        //被考核人
        var thePerson = $('#thePerson').val();
        //所属部门
        var department = $('#department').val();
        //考核月份
        var ckMonth = $('#ckMonth').val();
        //直属上级
        var superior = day;
        //考核日期
        var insDate = $('#insDate').val();




        //工作业绩
        var a = $('.ajson');
        $.each(a, function () {
            dataa[$(this).attr("name")] = $(this).val();

        });
        //工作能力
        var b = $('.bjson');
        $.each(b, function () {
            datab[$(this).attr("name")] = $(this).val();

        });
        //工作态度
        var c= $('.cjson');
        $.each(c, function () {
            datac[$(this).attr("name")] = $(this).val();

        });
        //例外考核
        var d = $('.djson');
        $.each(d, function () {
            datad[$(this).attr("name")] = $(this).val();

        });
        //出勤状况
        var e = $('.ejson');
        $.each(e, function () {
            datae[$(this).attr("name")] = $(this).val();

        });

        sa.push(dataa);
        sb.push(datab);
        sc.push(datac);
        sd.push(datad);
        se.push(datae);






        var data = {
            "wkPerform": JSON.stringify(sa),
            "seAbility": JSON.stringify(sb),
            "attWard": JSON.stringify(sc),
            "exeInspec": JSON.stringify(sd),
            "attStas": JSON.stringify(se),
            "thePerson": thePerson,
            "department": department,
            "ckMonth": ckMonth,
            "superior": superior,
            "insDate": insDate,
        };
        console.log(data)


        $.ajax({
            url: fast.ctxPath + '/perfoAppraisal/insertPer', // 数据接口
            type: "POST",
            data: data,
            success: function (res) {//res表示是否与服务器连接成功
            },
            error: function (e) {

            }
        });






    });
    $(function(){

        $("#Wk_ACHIEVE,#WKLOAD,#WK_CIEN,#EXE_FORCE,#PLANN,#JOB_SKILLS,#CPS_RIG,#TEAMWORK,#SE_DUTY,#INI_ENT,#COM_QUALITY,#BS_POS,#BS_REDC").bind('input propertychange', function() {
            var a = $('#Wk_ACHIEVE').val();
            var b = $('#WKLOAD').val();
            var c = $('#WK_CIEN').val();
            var d = $('#EXE_FORCE').val();
            var e = $('#PLANN').val();
            var f = $('#JOB_SKILLS').val();
            var g = $('#CPS_RIG').val();
            var h = $('#TEAMWORK').val();
            var i = $('#SE_DUTY').val();
            var j = $('#INI_ENT').val();
            var k = $('#COM_QUALITY').val();
            var l = $('#BS_POS').val();
            var m = $('#BS_REDC').val();
            var sum= a*0.3+b*0.3+c*0.3+d*0.3+e*0.3+f*0.3+g*0.3+h*0.3+i*0.3+j*0.3+k*0.3+l*0.3+m*0.3;

            $('#person').val(sum);
        });

    });
    $(function() {

        $.ajax({
            url: fast.ctxPath + '/perfoAppraisal/select', // 数据接口
            type: "POST",
            data: null,
            success: function (res) {//res表示是否与服务器连接成功
                $.each(res.data,function(index,value){
                    $("#superior").append("<option value='"+value.userid+"'>"+value.name+"</option>");
                    // $("#ca").val(slv);
                })

            },
            error: function (e) {

            }
        });
    });
    $(function() {

        $.ajax({
            url: fast.ctxPath + '/perfoAppraisal/selectUser', // 数据接口
            type: "POST",
            data: null,
            success: function (res) {//res表示是否与服务器连接成功
                    var jsons=res.data;
                    $("#thePerson").val(jsons[0].name);
            },
            error: function (e) {

            }
        });
    });


});


