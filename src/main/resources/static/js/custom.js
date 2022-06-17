
$('document').ready(function () {
    $('#modalAcepta').on('show.bs.modal', function(e) {
        var nombre= $(e.relatedTarget).data('nombre');
        var email= $(e.relatedTarget).data('email');
        var catId= $(e.relatedTarget).data('catid');
        var applyId = $(e.relatedTarget).data('applyid');

        $('#nombreUsuario').val(nombre);
        $('#emailUsuario').val(email);
        $('#catId').val(catId);
        $('#applicationId').val(applyId);

    });

});

function itemsToReturn() {
    return 3;
}




