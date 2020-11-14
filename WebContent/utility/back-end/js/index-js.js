   

        $(".custom-control-input").click(function() {

            if ($(this).is(":checked") == true) {
                c = parseInt($(this).attr("value"));
                pv += c;
            } else {
                c = parseInt($(this).attr("value"));
                pv -= c;
            }
            $("#PV").val(pv);
        });


