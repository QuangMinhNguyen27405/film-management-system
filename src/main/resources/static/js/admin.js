$(document).ready(function(){

    $('.nBtn, .table .eBtn').on('click', function(event){

        event.preventDefault();
        var href = $(this).attr('href');

        var text = $(this).text();

        if(text == 'Edit'){
            console.log("pass");

            $.get(href, function(customer, status){
                $('.myForm #password').attr("readonly");
                $('.myForm #customerId').val(customer.customerId);
                $('.myForm #firstName').val(customer.firstName);
                $('.myForm #lastName').val(customer.lastName);
                $('.myForm #email').val(customer.email);
                $('.myForm #password').val(customer.password);
                $('.myForm #address').val(customer.address.address);

            });
            $('.myForm #exampleModal').modal();
        }
        else{
            console.log("pass2");
            $('.myForm #password').removeAttr("readonly");
            $('.myForm #customerId').val('');
            $('.myForm #firstName').val('');
            $('.myForm #lastName').val('');
            $('.myForm #email').val('');
            $('.myForm #password').val('');
            $('.myForm #address').val('');
            $('.myForm #exampleModal').modal();
        }
    });
})