export const postData = async (url, data) => {
    const res = await fetch(url, {
        method: "POST",
        headers: {
            'Content-type': 'application/json'
        },
        body: data // info, that we send
    });

    const resData =  await res.json();

    return {data: resData, status: res.status};
};
export const getResource = async (url) => {
    const res = await fetch(url);

    if (!res.ok) {
        throw prompt(new Error(`Could not fetch ${url}, status: ${res.status}`));
    }

    return await res.json();
};
export const getWithAuthorization = async (url, token) => {
    const res = await fetch(url, {
        method: "GET",
        headers: {
            'Content-type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    });

    if (!res.ok) {
        throw prompt(new Error(`Could not fetch ${url}, status: ${res.status}`));
    }

    return await res.json();
};

export const fetchWithAuthorization = async (url,  method, token,  data = false) => {

    const options = {
        method,
        headers: {
            'Content-type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    };

    if (data && method !== 'GET') {
        options.body = JSON.stringify(data);
    }
    const res = await fetch(url, options);

    const resData =  await res.json();

    return {data: resData, status: res.status};
};

export const fetchWithoutData = async (url,  method, token,  data = false) => {

    const options = {
        method,
        headers: {
            'Content-type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    };

    if (data && method !== 'GET') {
        options.body = JSON.stringify(data);
    }
    return await fetch(url, options);
};