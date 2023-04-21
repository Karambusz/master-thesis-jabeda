import { GEOCODE_GOOGLE_API_URL } from "@env";
export const getAddressFromCoordinates = (latitude, longitude, apiKey) => {
    return new Promise((resolve, reject) => {
        fetch(
            GEOCODE_GOOGLE_API_URL +
            latitude +
            ',' +
            longitude +
            '&key=' +
            apiKey,
        )
            .then(response => response.json())
            .then(responseJson => {
                if (responseJson.status === 'OK') {
                    resolve(responseJson?.results?.[0]?.formatted_address);
                } else {
                    reject('not found');
                }
            })
            .catch(error => {
                reject(error);
            });
    });
};