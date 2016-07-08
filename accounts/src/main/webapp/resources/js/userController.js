var receivedData = {};

function getUserInfo() {
    $.ajax({
        url: "/user",
        dataType: 'json',
        success: function (json) {
            var table = "<html><table border='1px'>";

            $.each(json, function (key, value) {
                receivedData[key] = value;
                table = table + "<tr><td>" + key + "</td><td>" + value + "</td></tr>";
            });

            console.log(receivedData);

            $("#output").html(table);
            
            table = table + "</table></html>";
        },
        error: function () {
            alert("Errr is occured");
        }
    });
}

getUserInfo();

function saveData() {
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