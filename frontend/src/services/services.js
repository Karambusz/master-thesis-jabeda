export const closeModal = (setter) => {
    setter(false);
}

export const createModalContent = (header, messages) => {
    const tempModalContent = {};
    tempModalContent.header = header;
    tempModalContent.messages = messages;

    return tempModalContent;
}

export const setModalAndLoading = (isModal, isError, isLoading, setIsModal, setModalError, setLoading) => {
    setIsModal(isModal);
    setModalError(isError);
    setLoading(isLoading);
}

//This function takes in latitude and longitude of two location and returns the distance between them as the crow flies (in km)
export const calcDistance = (lat1, lon1, lat2, lon2)  => {
    var R = 6371; // km
    var dLat = toRad(lat2 - lat1);
    var dLon = toRad(lon2 - lon1);
    var latToRad1 = toRad(lat1);
    var latToRad2 = toRad(lat2);

    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(latToRad1) * Math.cos(latToRad2); 
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); 

     // Distance in km
    return R * c;
}

// Converts numeric degrees to radians
export const toRad = (Value) => {
return Value * Math.PI / 180;
}
