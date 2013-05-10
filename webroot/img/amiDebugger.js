
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
    sendCommand( text );
}

function sendAction( cmd ) {
    sendCommand( "Action: " + cmd );
}







