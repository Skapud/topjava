const mealAjaxUrl = "user/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

const $MEAL_FILTER_FORM= $('#mealFilterForm');

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
    $MEAL_FILTER_FORM.submit(function (e) {
        e.preventDefault();
        filter($(this).serialize());
    });
});

function filter(data) {
    $.ajax({
        url: ctx.ajaxUrl + 'filter',
        type: "GET",
        data: data,
    }).done(function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
        successNoty("Success");
    });
}

function clearForm() {
    $MEAL_FILTER_FORM[0].reset();
    filter($MEAL_FILTER_FORM.serialize());
}




