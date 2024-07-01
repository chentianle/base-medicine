let tableIns;
let tree;
layui.use(['element', 'form', 'table', 'layer', 'laydate', 'tree', 'util'], function () {
    let table = layui.table;
    let form = layui.form;//select、单选、复选等依赖form
    let element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    let laydate = layui.laydate;
    tree = layui.tree;
    let height = document.documentElement.clientHeight - 160;

    //用户列表
    tableIns = table.render({
        elem: '#userTable'
        , url: ctx + '/wechartAdmin/page'
        , method: 'POST'
        //请求前参数处理
        , request: {
            pageName: 'page' //页码的参数名称，默认：page
            , limitName: 'rows' //每页数据量的参数名，默认：limit
        }
        , response: {
            statusName: 'flag' //规定数据状态的字段名称，默认：code
            , statusCode: true //规定成功的状态码，默认：0
            , msgName: 'msg' //规定状态信息的字段名称，默认：msg
            , countName: 'records' //规定数据总数的字段名称，默认：count
            , dataName: 'rows' //规定数据列表的字段名称，默认：data
        }
        //响应后数据处理
        , parseData: function (res) { //res 即为原始返回的数据
            var data = res.data;
            return {
                "flag": res.flag, //解析接口状态
                "msg": res.msg, //解析提示文本
                "records": data.records, //解析数据长度
                "rows": data.rows //解析数据列表
            };
        }
        , toolbar: '#userTableToolbarDemo'
        , title: '用户列表'
        , cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', hide: true}
            , {field: 'userName', title: '姓名'}
            , {field: 'telphone', title: '手机号'}
            , {field: 'idCard', title: '身份证'}
            , {field: 'openId', title: '微信openId',width:300}
            , {field: 'createTime', title: '创建时间'}
            , {field: 'updateTime', title: '更新时间'}
        ]]
        , defaultToolbar: ['', '', '']
        , page: true
        , height: height
        , cellMinWidth: 80
    });


    //头工具栏事件
    table.on('toolbar(test)', function (obj) {
        switch (obj.event) {
            case 'payUnifiedorder':
                $.post(ctx + "/wechartAdmin/payUnifiedorder", '', function (data) {
                    layer.msg("修改成功！", {icon: 1, time: 2000}, function () {});
                });
                break;
            case 'oncePaySharing':
                $.post(ctx + "/wechartAdmin/oncePaySharing", '', function (data) {
                    layer.msg("修改成功！", {icon: 1, time: 2000}, function () {});
                });
                break;
            case 'profitsharingaddreceiver':
                $.post(ctx + "/wechartAdmin/profitsharingaddreceiver", '', function (data) {
                    layer.msg("修改成功！", {icon: 1, time: 2000}, function () {});
                });
                break;
            case 'query':
                let userName = $("#userName").val();
                let telphone = $("#telphone").val();
                let idCard = $("#idCard").val();
                let query = {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , done: function (res, curr, count) {
                        //完成后重置where，解决下一次请求携带旧数据
                        // this.where = {};
                    }
                };
                if (!userName) {
                    userName = null;
                }
                if (!telphone) {
                    telphone = null;
                }
                if (!idCard) {
                    idCard = null;
                }
                //设定异步数据接口的额外参数
                query.where = {"userName": userName,"telphone":telphone,"idCard":idCard};
                tableIns.reload(query);
                $("#userName").val(userName);
                $("#telphone").val(telphone);
                $("#idCard").val(idCard);
                break;
            case 'reload':
                break;
        }
    });

    //监听行工具事件
    table.on('tool(test)', function (obj) {
        let data = obj.data;
        //删除
        if (obj.event === 'del') {
            layer.confirm('确认删除吗？', function (index) {
                //向服务端发送删除指令
                $.delete(ctx + "/sys/sysUser/delete/" + data.userId, {}, function (data) {
                    tableIns.reload();
                    layer.close(index);
                })
            });
        }
        //编辑
        else if (obj.event === 'edit') {
            //回显操作表单
            $("#userForm").form(data);
            $("input[name='loginName']").attr("readonly","readonly");

            form.render();
        }
        //踢下线
        else if (obj.event === 'forced') {
            layer.confirm('确认强制该用户下线吗？', function (index) {
                //向服务端发送删除指令
                $.delete(ctx + "/sys/sysUser/forced/" + data.loginName, {}, function (data) {
                    layer.close(index);
                })
            });
        }
    });

});
