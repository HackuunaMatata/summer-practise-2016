var path;
var pathsize;

function LoadEvents() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            CreateEvents(xhttp.responseText);
        }
    };
    xhttp.open("GET", "Servlet?event", false);
    xhttp.send();
}

function CreateEvents(params) {
    //alert(params)
    var objects = JSON.parse(params);
    path = new Array(objects.length);
    for (var j = 0; j < objects.length; j++) {
        for (var i = 0; i < objects[j].image.length; i++) {
            path[j] += String.fromCharCode(parseInt(objects[j].image[i]));
        }
    }
    for (var i = 0; i < path.length; i++) {
        path[i].replace("undefined", "")
    }
    pathsize = path.length;
    var elem = document.createElement("select");
    elem.id = "events";
    elem.name = "event";
    var table = document.getElementById("table");
    var row = table.insertRow(table.rows.length);
    var cell = row.insertCell(0).innerHTML = "Мероприятие:";
    cell = row.insertCell(1);
    cell.colSpan = "10";
    cell.appendChild(elem);
    var option;
    option = document.createElement("option");
    option.value = "";
    option.selected = true;
    option.disabled = true;
    elem.appendChild(option);
    for (var i = 0; i < objects.length; i++) {
        option = document.createElement("option");
        option.id = objects[i].idevent;
        option.value = objects[i].idevent;
        option.text = objects[i].title;
        elem.appendChild(option);
    }
    elem.addEventListener("change", GetInstr);
}

function GetInstr() {
    while (document.getElementById("table").rows.length != 1) {
        document.getElementById("table").deleteRow(1);
    }
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            CreateForm(xhttp.responseText);
        }
    };
    var e = document.getElementById("events");
    var event = e.options[e.selectedIndex].value;
    //alert(event - 1);
    document.getElementById("logo").setAttribute("src", path[event - 1]);
    //alert(document.getElementById("logo").src)
    xhttp.open("GET", "Servlet?form=" + event, false);
    xhttp.send();
}

function CreateForm(x) {
    var params = JSON.parse(x);
    var questions = params.questions;
    var answers = params.answers;
    var table = document.getElementById("table");
    var e = document.getElementById("events");
    var strUser = e.options[e.selectedIndex].value;
    for (var i = 0; i < questions.length; i++) {
        if (questions[i].isActive == true) {
            switch (questions[i].tag) {
                case "input":
                    switch (questions[i].type) {
                        case "text":
                            var elem = document.createElement(questions[i].tag);
                            elem.setAttribute("type", questions[i].type);
                            elem.id = questions[i].iditem;
                            elem.name = questions[i].iditem;
                            var row = table.insertRow(table.rows.length);
                            var cell = row.insertCell(0).innerHTML = questions[i].description;
                            cell = row.insertCell(1);
                            cell.appendChild(elem);

                            break;
                        case "file":
                            var elem = document.createElement(questions[i].tag);
                            elem.setAttribute("type", questions[i].type);
                            elem.id = questions[i].iditem;
                            //elem.name = questions[i].iditem;
                            elem.name = 41;
                            elem.accept = "image/*";
                            var row = table.insertRow(table.rows.length);
                            var cell = row.insertCell(0).innerHTML = questions[i].description;
                            cell = row.insertCell(1).appendChild(elem);
                            break;
                        default:
                            var elem = document.createElement(questions[i].tag);
                            elem.setAttribute("type", questions[i].type);
                            elem.id = questions[i].iditem;
                            elem.name = questions[i].iditem;
                            var row = table.insertRow(table.rows.length);
                            var cell = row.insertCell(0).innerHTML = questions[i].description;
                            cell = row.insertCell(1);
                            cell.appendChild(elem);
                            break;
                        case "checkbox":
                            var row = table.insertRow(table.rows.length);
                            var cell = row.insertCell(0).innerHTML = questions[i].description;
                            var g = 1;
                            var num = 1;
                            for (var j = 0; j < answers.length; j++) {
                                if (answers[j].iditem == questions[i].iditem) {
                                    var elem = document.createElement(questions[i].tag);
                                    elem.setAttribute("type", questions[i].type);
                                    elem.id = questions[i].iditem;
                                    elem.value = g;
                                    elem.name = questions[i].iditem;

                                    cell = row.insertCell(num);
                                    cell.innerHTML = answers[j].answer;
                                    cell.appendChild(elem);
                                    num++;
                                    g++;
                                }
                            }
                            num = 0;
                            break;
                        case "radio":
                            var g = 1;
                            var row = table.insertRow(table.rows.length);
                            var cell = row.insertCell(0).innerHTML = questions[i].description;
                            var num = 1;
                            for (var j = 0; j < answers.length; j++) {
                                if (answers[j].iditem == questions[i].iditem) {
                                    var elem = document.createElement(questions[i].tag);
                                    elem.setAttribute("type", questions[i].type);
                                    elem.id = g;
                                    elem.value = g;
                                    elem.name = questions[i].iditem;

                                    cell = row.insertCell(num);
                                    cell.innerHTML = answers[j].answer;
                                    cell.appendChild(elem);
                                    num++;
                                    g++;
                                }
                            }
                            num = 0;
                            break;
                    }
                    break;
                case "select":
                    var elem = document.createElement(questions[i].tag);
                    elem.id = questions[i].iditem;
                    elem.name = questions[i].iditem;
                    var row = table.insertRow(table.rows.length);
                    var cell = row.insertCell(0).innerHTML = questions[i].description;
                    cell = row.insertCell(1);
                    cell.appendChild(elem);
                    var option;
                    option = document.createElement("option");
                    option.value = "";
                    option.selected = true;
                    option.disabled = true;
                    elem.appendChild(option);
                    for (var j = 0; j < answers.length; j++) {
                        //alert(answers[j].iditem);
                        if (answers[j].iditem == questions[i].iditem) {
                            option = document.createElement("option");
                            option.id = answers[j].iditem;
                            option.value = answers[j].answer;
                            option.text = answers[j].answer;
                            elem.appendChild(option);
                        }
                    }
                    break;
                case "textarea":
                    var elem = document.createElement(questions[i].tag);
                    elem.id = questions[i].iditem;
                    elem.name = questions[i].iditem;
                    var row = table.insertRow(table.rows.length);
                    var cell = row.insertCell(0).innerHTML = questions[i].description;
                    cell = row.insertCell(1);
                    cell.appendChild(elem);
                    break;
            }
        }
    }
    var maxcol = 0;
    for (var i = 0; i < document.getElementById("table").rows.length; i++) {
        var r = document.getElementById("table").rows[i].cells.length;
        if (r > maxcol) {
            maxcol = r;
        }
    }
    for (var i = 0; i < document.getElementById("table").rows.length; i++) {
        var r = document.getElementById("table").rows[i].cells.length;
        if (r < maxcol) {
            document.getElementById("table").rows[i].cells[r - 1].colSpan = maxcol.toString();
        }
    }
}