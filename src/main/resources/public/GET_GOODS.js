
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}
var tk = getCookie('_tb_token_');
var size = 3;
var start = 1;
var items = new Array();

function getData() {
    if (start > size) {
        console.log(JSON.stringify(items));
        return;
    }
    console.log('request data ,page ' + start);
    $.post(
        'https://alitj.tmall.com/channel/distributor/ajax/itemList.do?_input_charset=UTF-8',
        { categoryId: 1007, currentPage: start++, pageSize: 40, _tb_token_: tk },
        function(data) {
            var list = data.data.list;
            size = data.data.totalPage;
            for (var i = 0; i < list.length; i++) {
                var x = list[i];
                        items.push({ id: x.itemId, price: x.itemPrice, name: x.productName });
                
            }
            getData();
        }
   )
}
getData();
