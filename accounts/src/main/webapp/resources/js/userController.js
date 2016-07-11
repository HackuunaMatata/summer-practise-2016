var receivedData = {};
getUserInfo();

function getUserInfo() {

    $.ajax({
        url: "/user",
        dataType: 'json',
        success: function (json) {
            var table = "<html><table border='1px'>";

            $.each(json, function (key, value) {
                receivedData[key] = value;
                table = table +
                    "<tr>" +
                    "<td>" + key + "</td>" +
                    "<td><input id='" + key + "' value='" + value + "'></td>" +
                    "</tr>";
            });

            console.log(receivedData);
            
            table = table + "</table></html>";
            
            $("#output").html(table);
        },
        error: function () {
            alert("Errr is occured");
        }
    });
}

function saveData() {
    
    $.each(receivedData, function (key) {
        var id = "#" + key;
        receivedData[key] = $(id).val();
    });
    
    $.ajax({
        type: "POST",
        data: receivedData,
        url: "/save",
        success: function () {
            console.log("Yupiii")
        },
        error: function () {
            alert("Errr is occured");
        }
    });
}