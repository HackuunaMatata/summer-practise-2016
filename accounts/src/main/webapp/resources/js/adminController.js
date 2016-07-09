var receivedData = {};
getQuestions();

var users = [];
getUsers();

var selected = 0;

function getUsers() {
    $.ajax({
        type: "POST",
        url: "/usersInfo",
        dataType: 'json',
        success: function (json) {
            $.each(json, function (id, user) {
                users[id] = user;
            });
            
            console.log(users);
        },
        error: function () {
            alert("Errr is occured");
        }
    });
}

function addUser() {
    var select = "<br/><select id='"+ "sel" + selected++ +"'>";
    users.forEach(function (user, index, users) {
        select = select + "<option value='" + index + "'>" + index + " " + user + "</option>";
    });
    select = select + "</select>";
    $("#users").append(select);
}

function download() {
    
    var usersDownload = [];
    var current;
    
    for (var i=0; i<selected; i++) {
        current = "#sel" + i;
        usersDownload.push($(current + " option:selected").val());
    }
    console.log(usersDownload);

    // var data = {};
    // data.download = usersDownload;
    //
    // console.log(data);
    
    $.ajax({
        type: "POST",
        url: "/download",
        data: {"download" : usersDownload},
        success: function (json) {
            console.log("User's ID send to BE");
        },
        error: function () {
            alert("Errr is occured");
        }
    });
}

function getQuestions() {

    $.ajax({
        type: "POST",
        url: "/questionsInfo",
        dataType: 'json',
        success: function (json) {
            var table = "<html><table border='1px'>";
            table = table + "<th>Checked</th><th>Question</th><th>Type</th>";
            console.log(json);
            var correct;
            
            $.each(json, function (question, objects) {
                table = table + "<tr>";
                correct = true;
                $.each(objects, function (object, params) {
                    $.each(params, function (param, value) {

                        if (param == "Check") {
                            table = table + "<td>";
                            if (value == false) correct = false;
                            if (value == "yes") table = table + "<input type='checkbox' checked id='"+ question + "_check" +"'>";
                            if (value == "no") table = table + "<input type='checkbox' id='"+ question + "_check" +"'>";
                            table = table + "</td>";
                        }
                        if (param == "Type") {
                            if (correct == true) {
                                table = table + "<td><input type='text' value='" + question + "'></td><td>";
                                if (value != "list") {
                                    table = table + "<input type='text' value='" + value + "'></td>";
                                }
                            } else {
                                table = table + "<td>" + question + "</td><td>";
                                if (value != "list") {
                                    table = table + value + "</td>";
                                }
                            }
                            
                        }
                        if (param == "Variants") {
                            $.each(value, function (key, current) {
                                table = table + current + "<br/>";
                            });
                            table = table + "</td>";
                        }
                    });
                    
                });
                table = table + "</tr>";
            });

            table = table + "</table></html>";

            $("#questions").html(table);
        },
        error: function () {
            alert("Errr is occured");
        }
    });
}