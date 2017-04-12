
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
        console.log('over page,data size ' + items.length);
        size = 3;
        start = 1;
        var i = 0;
        console.log(items)
        updatePrice();

        function updatePrice() {
            if (i == items.length) {
                console.log('over');
                items = new Array();
                return;
            };
            var d = items[i++];
            console.log(i + '--' + d.name);
            //改价
            if (d.price)
                $1.post('https://alitj.tmall.com/channel/distributor/ajax/itemUpdate.do?_input_charset=UTF-8', { itemId: d.id, itemPrice: d.price, _tb_token_: tk });
            //上架
            if (d.status)
                $1.post('https://alitj.tmall.com/channel/distributor/ajax/itemStatus.do?_input_charset=UTF-8', { itemIdList: d.id, status: 1, _tb_token_: tk });

            setTimeout(updatePrice, 10);
        }
        return;
    }
    console.log('request data ,page ' + start);
    $1.post({
        url: 'https://alitj.tmall.com/channel/distributor/ajax/itemList.do?_input_charset=UTF-8',
        data: { categoryId: 1007, currentPage: start++, pageSize: 40, _tb_token_: tk },
        success: function(data) {
            var list = data.data.list;
            size = data.data.totalPage;
            for (var i = 0; i < list.length; i++) {
                var x = list[i];
                if (x.productPriceInterval) {
                    var price = x.productPriceInterval.trim().split('-')[0];
                    if (price != x.itemPrice)
                        items.push({ id: x.itemId, price: price, name: x.productName });
                }

                if (x.status == '2') {
                    items.push({ id: x.itemId, status: x.status, name: x.productName });
                }
            }
            getData();
        }
    })
}
getData();
setInterval(getData, 1000 * 60 * 3);
