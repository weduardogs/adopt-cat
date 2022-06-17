
$('document').ready(function () {
    let mediaquery = window.matchMedia("(max-width: 1199px)");

    mediaQueryResponse(mediaquery);

    mediaquery.onchange = (e) => {
        mediaQueryResponse(e);
    };

    function mediaQueryResponse(mql) {
        let divCard = document.querySelector("#card-body-users");
        let divTable = document.querySelector("#table-user");

        if (mql.matches) {
            divCard.classList.remove("card-body");
            divTable.classList.remove("table-striped");
        } else {
            divCard.classList.add("card-body");
            divTable.classList.add("table-striped");
        }

    }

});




