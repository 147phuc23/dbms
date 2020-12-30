function buildTable() {
    let table = $('#table-body');
    table.empty();

    let myList = state.querySet;

    for (let i = 0; i < myList.length; i++) {
        let row = `<tr>
                        <td>
                            <div class="custom-control custom-checkbox">
                                <input onclick="panelEffect()" type="checkbox" class="custom-control-input"
                                       id="${myList[i].id}" name="${myList[i].id}"/>
                                <label class="custom-control-label" for="${myList[i].id}"></label>
                            </div>
                        </td>
                        <td>
                            <a id="${myList[i].projectNumber}" onclick="renderProject(event)" href="#">${myList[i].projectNumber}</a>
                        </td>
                        <td>${myList[i].name}</td>
                        <td class="td-status">${myList[i].status}</td>
                        <td>${myList[i].customer}</td>
                        <td>${myList[i].startDate}</td>
                        <td class="trash">
                            <a onclick="deleteProject(event)" href="#" class="text-danger" style="visibility: hidden">
                                <i class="fa fa-trash"></i>
                            </a>
                        </td>`;
        table.append(row);
    }

    pageButtons();
}

function pageButtons() {
    let wrapper = document.getElementById('pagination-wrapper');

    wrapper.innerHTML = ``;

    let block = Math.ceil(state.page / state.window);
    let maxLeft = (block - 1) * state.window + 1;
    let maxRight = maxLeft + state.window - 1;

    for (let page = maxLeft; page <= maxRight; page++) {
        wrapper.innerHTML += `<button value=${page} class="page btn btn-sm btn-info">${page}</button>`;
    }

    if (block > 1) {
        wrapper.innerHTML = `<button value=${maxLeft - 1} class="page btn btn-sm btn-info">&#171;</button>` + wrapper.innerHTML;
    }

    wrapper.innerHTML += `<button value=${maxRight + 1} class="page btn btn-sm btn-info">&#187;</button>`;

    $('button[value="'+state.page+'"]').css("border", "2px solid black");

    $('.page').on('click', function() {
        state.page = Number($(this).val());
        queryOrSearch();
    });

}
