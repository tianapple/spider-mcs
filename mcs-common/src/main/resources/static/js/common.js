//将表单数据转为json
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//全局配置ajax
$.ajaxSetup({
    dataType: "json",
    type: 'POST',
    complete: function (XMLHttpRequest, textStatus) {
        if (XMLHttpRequest.getResponseHeader("sessionstatus") == 'timeout') {
            var top = getTopWinow();
            $.messager.alert('提示',"由于您长时间没有操作, session已过期, 请重新登录...",'info',function(){
                top.window.location.href = '/';
            });
        }
    }
});

/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * @return 当前页面的顶层窗口对象
 */
function getTopWinow(){
    var p = window;
    while(p != p.parent){
        p = p.parent;
    }
    return p;
}

//百分比宽度
function fixWidth(percent) {
    return document.body.clientWidth * percent;//根据自身情况更改
}