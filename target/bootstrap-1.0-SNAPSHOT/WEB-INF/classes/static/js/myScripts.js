/*
function modalId(id, tagId) {
    let a = document.getElementById(tagId);
    a.setAttribute("value", id);
}

$('#type-btn').click(function () {
    alert('lol');
});

*/
$('.danger-delete').click( function(){
    let data = $(this).closest("tr").children("td").map(function () {
        return $(this).text();
    });

    $('#deleteId').val(data[0]);
    $('#dFirstName').val(data[1]);
    $('#dLastName').val(data[2]);
    $('#dAge').val(data[3]);
    $('#dEmail').val(data[4]);
});

$('.info-edit').click( function(){
    let data = $(this).closest("tr").children("td").map(function () {
        return $(this).text();
    });

    $('#editId').val(data[0]);
    $('#eFirstName').attr('placeholder', data[1]);
    $('#eLastName').attr('placeholder', data[2]);
    $('#eAge').val(data[3]);
    $('#eEmail').attr('placeholder', data[4]);
});

