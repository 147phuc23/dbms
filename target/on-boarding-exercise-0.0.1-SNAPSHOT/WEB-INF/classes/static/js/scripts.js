const QUERY = 0;
const SEARCH = 1;

let state = {
    'querySet': [],
    'page': 1,
    'rows': 5,
    'window': 4,
    'op': QUERY,
    'textCriteria': "",
    'selectionCriteria': null
};

function queryOrSearch(){

    if(state.op === QUERY){
        let url = '/projects';
        $.get(url, {"page": state.page - 1}, function (data){
            doStuff(data);
        });
    }else if(state.op === SEARCH){
        let url = '/search';
        $.get(url, {"textFilter": state.textCriteria, "selectionFilter": state.selectionCriteria, "page": state.page - 1}, function (data){
            doStuff(data);
        });
    }

}

function doStuff(data){
    state.querySet = data;
    buildTable();
    preventDeleteProjects();
}

function clearSearchState() {
    state.searchTextCriteria = "";
    state.searchSelectionCriteria = null;
    $("#text-filter").val("");
    $("#selection-filter").val("Project status");
}

function preventDeleteProjects(){
    $('#main-table > tbody  > tr').each(function() {
        if($(this).find("[class*='td-status']").html() === 'New'){
            $(this).find("[class*='trash']").find("[class*='text-danger']").css("visibility", "visible");
        } else{
            $(this).find("[class*='custom-checkbox']").click(function (e){
                e.preventDefault();
            });
        }
    });
}

$(document).ready(function (){
    clearSearchState();
    state.page = 1;
    state.op = QUERY;
    queryOrSearch();
});

// Searching

function searchProject(e){
    e.preventDefault();

    state.textCriteria = $("#text-filter").val();
    state.selectionCriteria = $("#selection-filter").val();

    state.page = 1;
    state.op = SEARCH;

    queryOrSearch();
}

function resetSearch(e) {
    e.preventDefault();

    clearSearchState();
    state.page = 1;
    state.op = QUERY;
    queryOrSearch();
}

// Create new project

function validate() {
    let elements = [
        document.getElementById("p-number"),
        document.getElementById("p-name"),
        document.getElementById("customer"),
        document.getElementById("group"),
        document.getElementById("status"),
        document.getElementById("start-date")];

    let checked = true;
    let empties = true;
    let errorMessage = document.getElementById("error-message");
    let errorMessage2 = document.getElementById("error-message2");

    elements.forEach(element => {
        if(element.value.trim() === '') {
            element.style.borderColor = "#FF0000";
            empties = false;
            checked = false;
        } else {
            element.style.borderColor = "#CCC";
        }
    });

    let members = $(".responsiveChosen").val().join(",").trim();
    console.log(members);

    // if((members !== '') && (!members.match(/^(([A-Z]{3}[,])*([A-Z]{3}))$/))){
    //     members.style.borderColor = "#FF0000";
    //     errorMessage2.innerHTML = "Please type visas followed by comma e.g: DTH,BHU,...,ANC";
    //     errorMessage2.style.padding = "4px";
    //     checked = false;
    // } else {
    //     members.style.borderColor = "#CCC";
    //     errorMessage2.style.padding = "0px";
    //     errorMessage2.innerHTML = "";
    // }

    if(empties === false){
        errorMessage.style.padding = "10px";
        errorMessage.innerHTML = "Please enter all the mandatory fields (*)";
    } else {
        errorMessage.style.padding = "0px";
        errorMessage.innerHTML = "";
    }

    return checked;
}

function createOrUpdateProject(e){
    e.preventDefault();

    if(validate() === false)
        return;

    let projectNumber = $("#p-number").val().trim();
    let projectName = $("#p-name").val().trim();
    let customer = $("#customer").val().trim();
    let group = $("#group").val().trim();
    let members = $(".responsiveChosen").val().join(",").trim();
    let status = $("#status").val().trim();
    let startDate = $("#start-date").val().trim();
    let endDate = $("#end-date").val().trim();
    let version = $(".main-form").attr("id");

    let op = $("#btn-create-project").val();
    let url;
    if(op === "Create Project"){
        url = '/save';
    } else if (op === "Edit Project"){
        url = '/update';
    }

    $.ajax({
        url: url, // Specifies the URL to send the request to. Default is the current page
        dataType: "json", // The data type expected of the server response.
        type: "post", // Specifies the type of request. (GET or POST)
        contentType: "application/json", // The content type used when sending data to the server.
        data: JSON.stringify({ // Specifies data to be sent to the server
            "projectNumber": projectNumber,
            "name": projectName,
            "customer": customer,
            "group": group,
            "members": members,
            "status": status,
            "startDate": startDate,
            "endDate": endDate,
            "version": version
        }),
        success: function (data){ // A function to be run when the request succeeds
            console.log("SUCCESS: ", data);
            showListProject();
        },
        error : function(data) { // A function to run if the request fails.
            console.log("ERROR MESSAGE: ", data.responseText);
            console.log("STATUS: ", data.status);
            let json = JSON.parse(data.responseText);
            if(data.status === 400){
                handleBadRequest(json);
            } else if(data.status === 500){
                handlerServerError();
            }
        }
    });
}

function cancelCreate(e){
    e.preventDefault();
    showListProject();
}

// Update project

function renderProject(e) {
    let projectNumber = e.target.id;
    let url = '/project/' + projectNumber;
    $.get(url, function (data) {
        $(".main-list-project").replaceWith(data);
        $("#p-number").prop("readonly", true);
        $("#sidebar-new-project").css("color", "black");
        $("#sidebar-project-list").css("color", "#2F85FA");
        $("#fragment-title").html("Edit Project information");
        $("#btn-create-project").val("Edit Project");
        $(".responsiveChosen").chosen();
    });
}

// Delete projects

function panelEffect(){
    let projectIds = []
    let panel = $("#multi-delete-panel");
    let numberDiv = $("#number-of-deletions");
    $("input[type=checkbox]:checked").each(function (){
        projectIds.push($(this).attr("id"));
    });

    if(projectIds.length !== 0){
        numberDiv.html(projectIds.length);
        panel.css("visibility", "visible");
    } else{
        panel.css("visibility", "hidden");
    }

}

function deleteProject(e){
    let projectId = e.target.parentElement.parentElement.parentElement.firstElementChild.firstElementChild.firstElementChild.id;
    let url = '/project/delete/' + projectId;
    $.post(url, function (){
        queryOrSearch();
    });
}

function multiDeleteProjects(){

    if (confirm("Are you sure?")) {
        let projectIds = [];
        $("input[type=checkbox]:checked").each(function (){
            projectIds.push($(this).attr("id"));
        });

        projectIds = JSON.stringify(projectIds);

        let url = '/multi/delete';
        $.post(url, {"projectIds": projectIds} , function (){
            queryOrSearch();
        });
    }

}

function showListProject(){
    let url = '/fragment/list';
    $.get(url, function (data){
        $(".main-form").replaceWith(data);
        $("#sidebar-project-list").css("color", "black")
        $("#sidebar-new-project").css("color", "#666666");

        $("#text-filter").val(state.textCriteria);
        if(state.selectionCriteria === null){
            $("#selection-filter").val("Project status");
        }else{
            $("#selection-filter").val(state.selectionCriteria);
        }

        queryOrSearch();
    });
}

function showProjectForm(){
    let url = '/fragment/form';
    $.get(url, function (data){
        $(".main-list-project").replaceWith(data);
        $("#sidebar-new-project").css("color", "black");
        $("#sidebar-project-list").css("color", "#2F85FA");
        $(".responsiveChosen").chosen();
    });
}