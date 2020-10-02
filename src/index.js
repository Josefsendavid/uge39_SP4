import 'bootstrap/dist/css/bootstrap.css'

const tb = document.getElementById("tb");

const url = "https://www.josefsendavid.dk/rest2/api/person/all";
display();
function display() {
    document.getElementById("tb").value = "";
    fetch(url)
            .then(res => fetchWithErrorCheck(res))
            .then((data) => {
                const trs = data.all.map(user => {
                    return `<tr><td>${user.id}</td><td>${user.fName}</td><td>${user.lName}</td><td>${user.phone}</td><td>${user.street}</td><td>${user.zip}</td><td>${user.city}</td></tr>`

                });
                const trStr = trs.join("");
                document.getElementById("tb").innerHTML = trStr;
                console.log(trStr);
            });
}



function fetchWithErrorCheck(res) {
    if (!res.ok) {
        return Promise.reject({status: res.status, fullError: res.json()});
    }
    return res.json();
}