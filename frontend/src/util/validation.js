export const validateStringFields = (field) => {
    const regex = /^[a-zA-ZAaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż -]*$/;
    return regex.test(field) && field.length > 0 && field.length < 50;
}

export const validatePasswordField = (field) => {
    return field.length >= 6
}

export const validateEmailField = (field) => {
    const regex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regex.test(field) && field.length > 0 && field.length < 50;
}

export const validatePhoneField = (field) => {
    const regex = /^\+[48][0-9]{10}$/;
    return regex.test(field);
}

export const validateAddressField = (field) => {
    const regex = /^[a-zA-ZAaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż -]+[,]+[ 0-9]+[a-zA-Z]?/;
    return regex.test(field) && field.length > 0;
}