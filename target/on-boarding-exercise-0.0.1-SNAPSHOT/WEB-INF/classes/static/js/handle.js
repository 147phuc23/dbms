function handleBadRequest(json){
    if(json.message === 'Some Visas Does Not Exists'){
        let s = "The following visas do not exist: " + "{" + json.details + "}";
        $("#error-message3").html(s);
        $("#error-message3").css({"padding": "4px"});
        document.getElementById("members").style.borderColor = "#FF0000";
    }else {
        document.getElementById("members").style.borderColor = "#CCC";
        $("#error-message3").css({"padding": "0px"});
        $("#error-message3").html("");
    }

    if(json.message === 'Project Number Already Exists'){
        $("#error-message4").html("The project number already existed. Please select a different project number");
        $("#error-message4").css({"padding": "4px"});
        document.getElementById("p-number").style.borderColor = "#FF0000";
    }else {
        document.getElementById("p-number").style.borderColor = "#CCC";
        $("#error-message4").css({"padding": "0px"});
        $("#error-message4").html("");
    }

    if(json.message === 'This Project Is Concurrently Updated'){
        if (confirm("This project is concurrently updated by another user, do you want to reload it?")) {
            let projectNumber = $("#p-number").val().trim()
            let url = '/project/' + projectNumber;
            $.get(url, function (data) {
                $(".main-form").replaceWith(data);
                $("#p-number").prop("readonly", true);
                $("#sidebar-new-project").css("color", "black");
                $("#sidebar-project-list").css("color", "#2F85FA");
                $("#fragment-title").html("Edit Project");
                $("#btn-create-project").val("Edit Project");
            });
        }
    }

    if(json.message === 'Illegal User Form'){

    }
}

function handlerServerError(){
    let url = '/error';
    $.get(url, function (data){
        $("#content-body").replaceWith(data);
    });
}