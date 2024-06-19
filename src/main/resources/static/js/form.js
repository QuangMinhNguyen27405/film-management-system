$(document).ready(function(){
        
    $("#email").keyup(function(){
        var regx_email = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}(\.[a-zA-Z]{2,6})?$/;

        var email_inp = $(this).val();
        if(regx_email.test(email_inp)) {
            $("#email").removeClass("is-invalid");
            $("#email").addClass("is-valid");
            $("#email_status").removeClass("text-danger");
            $("#email").removeClass("border-danger");
            $("#email_status").text("Valid");
        }
        else {
            $("#email").addClass("is-invalid");
            $("#email_status").addClass("text-danger");
            $("#email").addClass("border-danger");
            $("#email_status").text("Please enter a valid email address.");
        }
    });

    $("#password").keyup(function(){
        var regx_password = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        var password_inp = $(this).val();
        if(regx_password.test(password_inp)) {
            console.log("pass");
            $("#password").removeClass("is-invalid");
            $("#password").addClass("is-valid");
            $("#password_status").removeClass("text-danger");
            $("#password").removeClass("border-danger");
            $("#password_status").text("Is Valid");
        }
        else {
            $("#password").addClass("is-invalid");
            $("#password_status").addClass("text-danger");
            $("#password").addClass("border-danger");
            $("#password_status").text("Please enter a valid password");
        }
    });

    $("#retype_password").keyup(function(){

        var password_inp = $("#password").val();
        var retype_password_inp = $(this).val();

        if(password_inp == retype_password_inp) {
            $("#retype_password").removeClass("is-invalid");
            $("#retype_password").addClass("is-valid");
            $("#retype_password_status").removeClass("text-danger");
            $("#retype_password").removeClass("border-danger");
            $("#retype_password_status").text("Password Matched");
        }
        else {
            $("#retype_password").addClass("is-invalid");
            $("#retype_password").addClass("border-danger");
            $("#retype_password_status").addClass("text-danger");
            $("#retype_password_status").text("Password Must Match");
        }
    });

    $("#buttonCreate").on("click", function(e){
        e.preventDefault();
        document.getElementById("registration").reset();
        console.log("Prevent");
        $(".form-control").removeClass("is-valid");
        $(".status").text("");
    })

    $("buttonSignin").on("click", function(e){
        e.preventDefault();
    })
});