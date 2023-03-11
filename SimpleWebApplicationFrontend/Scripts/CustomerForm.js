let baseUrl = "http://localhost:8080/customer"

/*----- show hide password function start--------*/
function password_show_hide() {
    let x = document.getElementById("password");
    let show_eye = document.getElementById("show_eye");
    let hide_eye = document.getElementById("hide_eye");
    hide_eye.classList.remove("d-none");
    if (x.type === "password") {
        x.type = "text";
        show_eye.style.display = "none";
        hide_eye.style.display = "block";
    } else {
        x.type = "password";
        show_eye.style.display = "block";
        hide_eye.style.display = "none";
    }
}
/*----- show hide password function end--------*/

/*----- logout ------------*/
$(".logoutContainer").click(function () {
    swal({
        title: "Are you sure?",
        text: "Do you really want to logout!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                $("#loginPage").css("display", "block");
                $("#customerPageContext").css("display", "none");
            } else {
                $("#loginPage").css("display", "none");
                $("#customerPageContext").css("display", "block");
            }
        });
})

/*-----------------loading all customers---------------*/
async function loadAllCustomers() {

    $(".customerTableBody").empty();
    $.ajax({
        url: baseUrl,
        method: "GET",
        success: function (resp) {
            for (const cust of resp.data) {
                let row = `<tr><td>${cust.userId}</td><td>${cust.userName}</td><td>${cust.address}</td><td>${cust.email}</td><td>${cust.telephoneNumber}</td></tr>`;
                $(".customerTableBody").append(row)
            }
            $('#customerTabele').DataTable();
        }
    });

}
loadAllCustomers();

/*-----------------save customers---------------*/

async function saveCustomer() {
    let userId = $("#userId").val();
    let userName = $("#userName").val();
    let address = $("#address").val();
    let email = $("#email").val();
    let telephoneNumber = $("#telephoneNumber").val();
    let password = $("#password").val();

    customer = {
        userId: userId,
        userName: userName,
        address: address,
        email: email,
        telephoneNumber: telephoneNumber,
        password: password
    }

    $.ajax({
        url: baseUrl,
        method: "POST",
        data: JSON.stringify(customer),
        contentType: "application/json",
        dataType: "json",
        success: function (resp) {
            if (resp.message == "Successfully Added") {
                swal("Good job!", "Successfully add the Customer!", "success");
                loadAllCustomers();
                clearAll();
                $('#btnCustomerSave').prop('disabled', true);
                $('#btnUpdateCustomer').prop('disabled', true);
            } else {
                swal("Error request!", "Fail to add the customer!", "error");
                $('#btnCustomerSave').prop('disabled', true);
                $('#btnUpdateCustomer').prop('disabled', true);
            }
        }
    })
}
$("#btnCustomerSave").click(function () {
    saveCustomer();
})

/*-----------------select a customer when clicked a table row---------------*/
$("#customerTabele").click(function (e) {
    let clicked_userId = $(e.target).closest('tr').find('td').eq(0).html();
    let clicked_userName = $(e.target).closest('tr').find('td').eq(1).html();
    let clicked_userAddress = $(e.target).closest('tr').find('td').eq(2).html();
    let clicked_userEmail = $(e.target).closest('tr').find('td').eq(3).html();
    let clicked_userTelephoneNumber = $(e.target).closest('tr').find('td').eq(4).html();

    $("#userId").val(clicked_userId);
    $("#userName").val(clicked_userName);
    $("#address").val(clicked_userAddress);
    $("#email").val(clicked_userEmail);
    $("#telephoneNumber").val(clicked_userTelephoneNumber);
})

/*-----------------update customers---------------*/
$("#btnUpdateCustomer").click(function () {

    let userId = $("#userId").val();
    let userName = $("#userName").val();
    let address = $("#address").val();
    let email = $("#email").val();
    let telephoneNumber = $("#telephoneNumber").val();
    let password = $("#password").val();

    if (userId == null || userName == null || address == null || email == null || telephoneNumber == null || password == null) {
        alert("Please select a customer");
    } else {
        customer = {
            userId: userId,
            userName: userName,
            address: address,
            email: email,
            telephoneNumber: telephoneNumber,
            password: password
        }

        $.ajax({
            url: baseUrl,
            method: "PUT",
            data: JSON.stringify(customer),
            contentType: "application/json",
            dataType: "json",
            success: function (resp) {
                if (resp.message == "Successfully Updated") {
                    swal("Good job!", "Successfully Update the Customer!", "success");
                    loadAllCustomers();
                    clearAll();
                    $('#btnCustomerSave').prop('disabled', true);
                    $('#btnUpdateCustomer').prop('disabled', true);
                } else {
                    swal("Error request!", "Fail to update the customer!", "error");
                    $('#btnCustomerSave').prop('disabled', true);
                    $('#btnUpdateCustomer').prop('disabled', true);
                }
            }
        })
    }
})

/*-----------------delete customers---------------*/
$("#btnDeleteCustomer").click(function () {
    let userId = $("#userId").val();

    $.ajax({
        url: baseUrl+"?userId=" + userId,
        method: "DELETE",
        success: function (resp) {
            if (resp.message == "Successfully deleted") {
                swal("Good job!", "Successfully deleted the customer!", "success");
                loadAllCustomers();
                clearAll()
            } else {
                swal("Error request!", "Fail to delete the customer!", "error");
            }
        }
    })
})

/*-----------------clearAll---------------*/
function clearAll() {
    $("#userId").val("");
    $("#userName").val("");
    $("#address").val("");
    $("#email").val("");
    $("#telephoneNumber").val("");
    $("#password").val("");
    $("#confirmPassword").val("");
}

/*------------ validation --------------*/

function validation() {
    const userIdRegex = /^(C)[0-9]{1,3}$/;
    const userNameRegex = /^[A-z ]{5,20}$/;
    const userAddress = /^[A-z ]{5,20}$/;
    const emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    const telephoneNumberRegex = /^[0-9]{10}$/;
    const passwordRegex = /(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}/;

    $("#userId").keydown(function (e) {
        if (e.key == "Enter") {
            let outUserId = $("#userId").val();
            if (userIdRegex.test(outUserId)) {
                $("#userId").css('border-color', '#04db14');
                $("#userId").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                $("#userId").css('color', '#04db14');
                $("#userName").focus();

                $("#userName").keydown(function (e) {
                    if (e.key == "Enter") {
                        let outUserName = $("#userName").val();
                        if (userNameRegex.test(outUserName)) {
                            $("#userName").css('border-color', '#04db14');
                            $("#userName").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                            $("#userName").css('color', '#04db14');
                            $("#address").focus();

                            $("#address").keydown(function (e) {
                                if (e.key == "Enter") {
                                    let outUserAddress = $("#address").val();
                                    if (userAddress.test(outUserAddress)) {
                                        $("#address").css('border-color', '#04db14');
                                        $("#address").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                        $("#address").css('color', '#04db14');
                                        $("#email").focus();

                                        $("#email").keydown(function (e) {
                                            if (e.key == "Enter") {
                                                let outUserEmail = $("#email").val();
                                                if (emailRegex.test(outUserEmail)) {
                                                    $("#email").css('border-color', '#04db14');
                                                    $("#email").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                    $("#email").css('color', '#04db14');
                                                    $("#telephoneNumber").focus();

                                                    $("#telephoneNumber").keydown(function (e) {
                                                        if (e.key == "Enter") {
                                                            let outUserMobileNum = $("#telephoneNumber").val();
                                                            if (telephoneNumberRegex.test(outUserMobileNum)) {
                                                                $("#telephoneNumber").css('border-color', '#04db14');
                                                                $("#telephoneNumber").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                                $("#telephoneNumber").css('color', '#04db14');
                                                                $("#password").focus();

                                                                $("#password").keydown(function (e) {
                                                                    if (e.key == "Enter") {
                                                                        let outPassword = $("#password").val();
                                                                        if (passwordRegex.test(outPassword)) {
                                                                            $("#password").css('border-color', '#04db14');
                                                                            $("#password").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                                            $("#password").css('color', '#04db14');

                                                                            $("#confirmPassword").focus();
                                                                            $("#confirmPassword").keyup(function (e) {

                                                                                let checkingPassword = $("#password").val();
                                                                                if (checkingPassword == $("#confirmPassword").val()) {
                                                                                    $("#confirmPassword").css('border-color', '#04db14');
                                                                                    $("#confirmPassword").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                                                    $("#confirmPassword").css('color', '#04db14');
                                                                                    $('#btnCustomerSave').prop('disabled', false);
                                                                                    $('#btnUpdateCustomer').prop('disabled', false);

                                                                                }else {
                                                                                    $("#confirmPassword").css('border-color', '#ff0202');
                                                                                    $("#confirmPassword").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                                                    $("#confirmPassword").css('color', '#ff0202');
                                                                                    $('#btnCustomerSave').prop('disabled', true);
                                                                                    $('#btnUpdateCustomer').prop('disabled', true);
                                                                                }
                                                                            })


                                                                        }else {
                                                                            $("#password").css('border-color', '#ff0202');
                                                                            $("#password").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                                            $("#password").css('color', '#ff0202');
                                                                            $('#btnCustomerSave').prop('disabled', true);
                                                                            $('#btnUpdateCustomer').prop('disabled', true);
                                                                        }
                                                                    }
                                                                })

                                                            }else {
                                                                $("#telephoneNumber").css('border-color', '#ff0202');
                                                                $("#telephoneNumber").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                                $("#telephoneNumber").css('color', '#ff0202');
                                                                $('#btnCustomerSave').prop('disabled', true);
                                                                $('#btnUpdateCustomer').prop('disabled', true);
                                                            }
                                                        }
                                                    })

                                                }else {
                                                    $("#email").css('border-color', '#ff0202');
                                                    $("#email").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                                    $("#email").css('color', '#ff0202');
                                                    $('#btnCustomerSave').prop('disabled', true);
                                                    $('#btnUpdateCustomer').prop('disabled', true);
                                                }
                                            }
                                        })

                                    }else {
                                        $("#address").css('border-color', '#ff0202');
                                        $("#address").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                                        $("#address").css('color', '#ff0202');
                                        $('#btnCustomerSave').prop('disabled', true);
                                        $('#btnUpdateCustomer').prop('disabled', true);
                                    }
                                }
                            });

                        } else {
                            $("#userName").css('border-color', '#ff0202');
                            $("#userName").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                            $("#userName").css('color', '#ff0202');
                            $('#btnCustomerSave').prop('disabled', true);
                            $('#btnUpdateCustomer').prop('disabled', true);
                        }
                    }
                });


            } else {
                $("#userId").css('border-color', '#ff0202');
                $("#userId").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
                $("#userId").css('color', '#ff0202');
                $('#btnCustomerSave').prop('disabled', true);
                $('#btnUpdateCustomer').prop('disabled', true);
            }
        }
    });


}

validation();
