function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}
var tk = getCookie('_tb_token_');
url='https://sell.taobao.com/auction/merchandise/auction_list.json?_input_charset=utf-8&_output_charset=utf-8&action=goodsmanager/GoodsTitleEditAction&event_submit_do_edit_item=1'
for (var i = 0; i < p.length; i++) {
    var x= p[i];
    $.post(url,{oldtext:'',
    newItemTitleStr:x.name,
    id:x.id,
    _tb_token_:tk,
    type:'c'})
    
}
