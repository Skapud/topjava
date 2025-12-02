const mealAjaxUrl = "user/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

$(function () {
    makeEditable(
        $('#mealsTable').DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Update",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            order: [[0, 'desc']]
        })
    );
});

$(function () {
    $('form').submit(function (e) {
        var $form = $(this);
        filter($form);
        e.preventDefault();
    });
});

function filter($form) {
    $.ajax({
        url: $form.attr('action'),
        type: $form.attr('method'),
        data: $form.serialize(),
    }).done(function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
        successNoty("Success");
    });
}

function clearForm() {
    var $form = $('#mealFilterForm');
    $form[0].reset();
    filter($form);
}




