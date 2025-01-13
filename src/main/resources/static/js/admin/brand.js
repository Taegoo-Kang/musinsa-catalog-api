$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();

    var actions = $("table td:last-child").html();

    // Append table with add row form on add new button click
    $(".add-new").click(function(){
        $(this).attr("disabled", "disabled");
        var index = $("table tbody tr:last-child").index();
        var row = '<tr>' +
            '<td name="brandNo"><input type="text" class="form-control" name="brandNo" disabled></td>' +
            '<td name="brandName"><input type="text" class="form-control" name="brandName"></td>' +
            '<td>' + actions + '</td>' +
            '</tr>';
        $("table").append(row);
        $("table tbody tr").eq(index + 1).find(".add, .edit").toggle();
        $('[data-toggle="tooltip"]').tooltip();
    });

    // Add row on add button click
    $(document).on("click", ".add", function(){
        var empty = false;
        var input = $(this).parents("tr").find('input[type="text"]');
        input.each(function(){
            const inputName = $(this).attr('name');
            if (inputName !== 'brandNo' && !$(this).val()) {
                $(this).addClass("error");
                empty = true;
            } else {
                $(this).removeClass("error");
            }
        });

        const parentTr = $(this).parents("tr");
        parentTr.find(".error").first().focus();

        if (!empty) {
            var brandNo;
            var brandName;
            input.each(function(){
                if ($(this).attr('name') === 'brandNo') {
                    brandNo = $(this).val();
                } else {
                    brandName = $(this).val();
                }
                // $(this).parent("td").html($(this).val());
            });

            var sendData = {
                brandName: brandName
            };
            var ajaxType = brandNo === undefined || brandNo === '' ? 'POST' : 'PUT'
            if (ajaxType === 'POST') {
                $.ajax({
                    type: 'POST',
                    url: '/api/admin/brand',
                    contentType: 'application/json',
                    data: JSON.stringify(sendData),
                    success: function(data) {
                        parentTr.attr('brandNo', data.body.brandNo);
                        input.each(function() {
                            if ($(this).attr('name') === 'brandNo') {
                                $(this).parent("td").html(data.body.brandNo);
                            } else {
                                $(this).parent("td").html($(this).val());
                            }
                        })
                    },
                    error: function(res) {
                        alert(res.responseJSON.errorMessage);
                    }
                })
            } else {
                $.ajax({
                    type: 'PUT',
                    url: '/api/admin/brand/' + brandNo,
                    contentType: 'application/json',
                    data: JSON.stringify(sendData),
                    success: function(data) {
                        input.each(function() {
                            $(this).parent("td").html($(this).val());
                            $(this).parent("td").html($(this).val());
                        })
                    },
                    error: function(res) {
                        alert(res.responseJSON.errorMessage);
                    }
                })
            }

            $(this).parents("tr").find(".add, .edit").toggle();
            $(".add-new").removeAttr("disabled");
        }
    });

    // Edit row on edit button click
    $(document).on("click", ".edit", function(){

        $(this).parents("tr").find("td:not(:last-child)").each(function(){
            if ($(this).attr('name') === 'brandNo') {
                $(this).html('<input name="brandNo" type="text" class="form-control" value="' + $(this).text() + '" disabled>');
            } else {
                $(this).html('<input name="brandName" type="text" class="form-control" value="' + $(this).text() + '">');
            }
        });
        $(this).parents("tr").find(".add, .edit").toggle();
        $(".add-new").attr("disabled", "disabled");
    });

    // Delete row on delete button click
    $(document).on("click", ".delete", function(){
        const thisTr = $(this).parents("tr");
        const brandNo = thisTr.attr('brandNo');
        if (brandNo === undefined || brandNo === '') {
            thisTr.remove();
            $(".add-new").removeAttr("disabled");
        }

        $.ajax({
            type: 'DELETE',
            url: '/api/admin/brand/' + brandNo,
            success: function() {
                thisTr.remove();
                $(".add-new").removeAttr("disabled");
            },
        })
    });
});