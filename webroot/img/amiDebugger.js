
$(document).ready(function() {
    var eventTimer = setInterval( 'reload()', 3000 );
});




function reload() {
    var filter = $("#filter").val();

    $.ajax( {
        type:        "post",
        url:         "poll_ajax.jsp",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType:    "html",
        data: {
            'filter': filter
        },
        success: function(resp) {
            $("#results").html( resp );

            // add the "Channel: " text by the end of the #cmd textarea
            $(".channelitem").click( function(e) {
                var text = $(this).html();
                $( "#cmd" ).val( $( "#cmd").val() + "\n" + text );
            });

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $("#results").html( XMLHttpRequest.responseText);
        }
    });
}

function sendCommand( cmd ) {
    $.ajax( {
        type:        "post",
        url:         "command_ajax.jsp",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType:    "html",
        data: {
            'cmd': cmd
        },
        success: function(resp) {
            $("#postresults").html( resp );
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.responseText);
        }
    });

}


function sendCmdArea() {
    var text = $("#cmd").val();
    sendCommand( multiline(text) );
}

function sendAction( cmd ) {
    sendCommand( "Action: " + multiline(cmd) );
}

function toCmd( s ) {
    $("#cmd").val( "Action: " + multiline(s) );
}

function multiline( s ) {
    return s.replace( /\^/g, "\n" );
}





