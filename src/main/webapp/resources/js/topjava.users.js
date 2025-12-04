const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl : userAjaxUrl,
    ajaxDataUrl: getDataUrl
};

function getDataUrl() {
    return userAjaxUrl;
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function changeStatus(userId, checkbox) {
    var newStatus = checkbox.checked;
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + userId + "?status=" + newStatus,
    }).done(function () {
        $(checkbox).closest('tr').attr('data-user-enabled', newStatus);
        successNoty("User status updated");
    }).fail(function () {
        $(checkbox).prop('checked', !newStatus);
    });
}