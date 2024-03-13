const handleAdd = () => {
    if(document.getElementById('customer_det_cont').style.display === "block")
        document.getElementById('customer_det_cont').style.display = ""
    else
        document.getElementById('customer_det_cont').style.display = "block"
}

const generateId = () => {

        var result = '';
        var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        var charactersLength = characters.length;

        for (var i = 0; i < length; i++) {
          result += characters[Math.floor(Math.random() * charactersLength)];
        }

        return result;

}

const deleteRow = async(id) => {

    const resp = await fetch("api endpoint to delete");
    const data = await resp.json();

    console.log("deleted data now sync");

}

const editTable = (obj) => {

    const firstname = document.getElementById('firstname');
    const lastname = document.getElementById('lastname');
    const address = document.getElementById('address');
    const street = document.getElementById('street');
    const city = document.getElementById('city');
    const phone = document.getElementById('no');
    const email = document.getElementById('email');
    const state = document.getElementById('state');

    firstname.value = obj.first_name;
    lastname.value = obj.last_name;
    address.value = obj.address;
    street.value = obj.street;
    city.value = obj.city;
    phone.value = obj.phone;
    email.value = obj.email;
    state.value = obj.state;

    document.getElementById("customer_det_cont").style.display = "block";

}

const addtoTable = (list) => {
    const tbody = document.getElementById("tablecont");

    for (let i = 0; i < list.length; i++) {

        const tr = document.createElement("tr");

        const id = document.createElement("article");
        id.style.display = "none";

        const td1 = document.createElement("td");
        const td2 = document.createElement("td");
        const td3 = document.createElement("td");
        const td4 = document.createElement("td");
        const td5 = document.createElement("td");
        const td6 = document.createElement("td");
        const td7 = document.createElement("td");
        const td8 = document.createElement("td");
        const td9 = document.createElement("td");


        id.innerHTML = list[i].uuid;
        td1.innerHTML = list[i].first_name;
        td2.innerHTML = list[i].last_name;
        td3.innerHTML = list[i].street;
        td4.innerHTML = list[i].address;
        td5.innerHTML = list[i].city;
        td6.innerHTML = list[i].state;
        td7.innerHTML = list[i].email;
        td8.innerHTML = list[i].phone;


        const span1 = document.createElement("span");
        const span2 = document.createElement("span");

        span1.addEventListener("click", () => deleteRow(list[i].uuid));
        span2.addEventListener("click",() => editTable(list[i]));

        span1.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor"><path d="M17 6H22V8H20V21C20 21.5523 19.5523 22 19 22H5C4.44772 22 4 21.5523 4 21V8H2V6H7V3C7 2.44772 7.44772 2 8 2H16C16.5523 2 17 2.44772 17 3V6ZM9 11V17H11V11H9ZM13 11V17H15V11H13ZM9 4V6H15V4H9Z"></path></svg>';
        span2.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor"><path d="M12.8995 6.85453L17.1421 11.0972L7.24264 20.9967H3V16.754L12.8995 6.85453ZM14.3137 5.44032L16.435 3.319C16.8256 2.92848 17.4587 2.92848 17.8492 3.319L20.6777 6.14743C21.0682 6.53795 21.0682 7.17112 20.6777 7.56164L18.5563 9.68296L14.3137 5.44032Z"></path></svg>';

        span1.style.marginRight="15px";
        td9.append(span1, span2);
        tr.append(td1,td2,td3,td4,td5,td6,td7,td8,td9);
        tbody.append(tr);
    }

}

const sync = async() => {

    /* const resp = await fetch("api endpoint to get the table data in form of list");
    const data = await resp.json();

    addtoTable(data); */

}

const handleCustomer = async() => {

    const uuid = generateId();
    const first_name = document.getElementById('firstname').value;
    const last_name = document.getElementById('lastname').value;
    const address = document.getElementById('address').value;
    const street = document.getElementById('street').value;
    const city = document.getElementById('city').value;
    const phone = document.getElementById('no').value;
    const email = document.getElementById('email').value;
    const state = document.getElementById('state').value;
    const zipcode=document.getElementById('zipcode').value;

    /* const resp = await fetch("api endpoint to add to table in backend");
    const data = await resp.json();
     */

    console.log("added to table now sync");
}

const addCustomer = async() =>{

}