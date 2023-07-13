function send(){
    var arr = document.getElementsByTagName('input');
    var email = arr[1].value;
    var pass = arr[2].value;
    

    if(email == "" || pass == ""){
        alert("Vui lòng điền đầy đủ thông tin");
        return;
    }
}