
$('document').ready(function () {
    let mediaquery = window.matchMedia("(max-width: 1199px)");

    mediaQueryResponse(mediaquery);

    mediaquery.onchange = (e) => {
        mediaQueryResponse(e);
    };

    function mediaQueryResponse(mql) {

        let divCardApply = document.querySelector("#card-body-apply");
        let divTableApply = document.querySelector("#table-apply");

        if (mql.matches) {
            divCardApply.classList.remove("card-body");
            divTableApply.classList.remove("table-striped");

        } else {
            divCardApply.classList.add("card-body");
            divTableApply.classList.add("table-striped");
        }

    }

});




