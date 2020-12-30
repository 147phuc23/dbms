function handleBadRequest(json){
    // let errorMessage3 = $("#error-message3");

    // if(json.message === 'Some Visas Does Not Exists'){
    //     let s = "The following visas do not exist: " + "{" + json.details + "}";
    //     errorMessage3.html(s);
    //     errorMessage3.css({"padding": "4px"});
    //     $("#members").style.borderColor = "#FF0000";
    // }else {
    //     $("#members").style.borderColor = "#AAAAAA";
    //     errorMessage3.css({"padding": "0px"});
    //     errorMessage3.html("");
    // }

    let errorMessage4 = $("#error-message4");

    if(json.message === 'Project Number Already Exists'){
        errorMessage4.html("The project number already existed. Please select a different project number");
        errorMessage4.css({"padding": "4px"});
        document.getElementById("p-number").style.borderColor = "#FF0000";
    }else {
        document.getElementById("p-number").style.borderColor = "#AAAAAA";
        errorMessage4.css({"padding": "0px"});
        errorMessage4.html("");
    }

    if(json.message === 'Concurrently Updated'){
        if (confirm("This project is concurrently updated by another user, do you want to reload it?")) {
            let projectNumber = $("#p-number").val().trim();
            let url = '/project/' + projectNumber;
            $.get(url, function (data) {
                $(".main-form").replaceWith(data);
                $("#p-number").prop("readonly", true);
                $("#sidebar-new-project").css("color", "black");
                $("#sidebar-project-list").css("color", "#2F85FA");
                $("#fragment-title").html("Edit Project");
                $("#btn-create-project").val("Edit Project");
                $(".responsiveChosen").chosen();
            });
        }
    }

    // remain some illegal form exception

}

function handlerServerError(){
    let url = '/error';
    $.get(url, function (data){
        $("#content-body").replaceWith(data);
    });
}