function check(){
    var arr = document.getElementsByTagName('input');
    var pass = arr[1].value;
    var rePass = arr[2].value;
    

    if(pass != rePass){
        alert("Mật khẩu không khớp");
        return;
    }
}