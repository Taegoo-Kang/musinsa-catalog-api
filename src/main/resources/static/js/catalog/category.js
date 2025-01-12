$(document).ready(function() {
    $('#categorySearch').click(function() {
        const category = $('#categoryInput').val();

        $.ajax({
            type: 'GET',
            url: '/api/catalog/low-high/category/' + category,
            success: function(data) {

                const table = $('#categoryTableBody');
                table.empty();

                var html;
                if (data.lowPriceGoodsList.length > 0) {
                    const lowPriceGoods = data.lowPriceGoodsList[0];
                    const highPriceGoods = data.highPriceGoodsList[0];
                    html = '<tr>'
                        + '<td>' + data.categoryName + '</td>'
                        + '<td>' + lowPriceGoods.brandName + '</td>'
                        + '<td>' + lowPriceGoods.salePrice + '</td>'
                        + '<td>최저가</td>'
                        + '</tr>'
                        + '<tr>'
                        + '<td>' + data.categoryName + '</td>'
                        + '<td>' + highPriceGoods.brandName + '</td>'
                        + '<td>' + highPriceGoods.salePrice + '</td>'
                        + '<td>최고가</td>'
                        + '</tr>';

                    table.append(html);
                }
            }
        })
    });

    $('#categoryInput').off().on('keyup', function(key) {
        if (key.keyCode === 13) {
            $('#categorySearch').click();
        }

    });
})