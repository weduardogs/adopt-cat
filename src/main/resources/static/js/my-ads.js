
$('document').ready(function () {
    let mediaquery = window.matchMedia("(max-width: 1199px)");

    mediaQueryResponse(mediaquery);

    mediaquery.onchange = (e) => {
        mediaQueryResponse(e);
    };

    function mediaQueryResponse(mql) {

        let divCardCats = document.querySelector("#card-body-my-ads");
        let divTableCats = document.querySelector("#table-cats");

        if (mql.matches) {
            divCardCats.classList.remove("card-body");
            divTableCats.classList.remove("table-striped");

        } else {
            divCardCats.classList.add("card-body");
            divTableCats.classList.add("table-striped");
        }

    }

});




