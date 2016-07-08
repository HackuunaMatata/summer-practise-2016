var receivedData = [];


function getUserInfo()
{
    $.ajax({
        url: "/user",
        dataType: 'json',
        success : function(json) {
            $.each(json, function(key, value) {
                var point = [];
                point.push(key);
                point.push(value);
                receivedData.push(point);
            });

            $("#output").html(receivedData);
        },
        error: function () {
            alert("Errr is occured");
        }
    });
}

getUserInfo();