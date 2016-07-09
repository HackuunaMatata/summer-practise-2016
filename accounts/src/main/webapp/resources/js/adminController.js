var receivedData = {};
getQuestions();

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
                            } else {
                                table = table + "<td>" + question + "</td><td>";
                            }
                            if (value != "list") {
                                table = table + value + "</td>";
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
                // receivedData[key] = value;
                // table = table +
                //     "<tr>" +
                //     "<td>" + key + "</td>" +
                //     "<td><input id='" + key + "' value='" + value + "'></td>" +
                //     "</tr>";
            });

            // console.log(receivedData);

            table = table + "</table></html>";

            $("#questions").html(table);
        },
        error: function () {
            alert("Errr is occured");
        }
    });
}