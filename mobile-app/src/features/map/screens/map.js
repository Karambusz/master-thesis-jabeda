import React, { useEffect, useState } from "react";
import * as Application from 'expo-application';
import { useDispatch } from "react-redux";
import { Marker } from "react-native-maps";
import { StyleSheet } from "react-native";
import { requestForegroundPermissionsAsync, getCurrentPositionAsync } from 'expo-location';
import { LoadingContainer, Loading } from "../../../components/loading/loading";
import { Background, BackgroundCover } from "../../../components/background/background";
import { GooglePlacesAutocomplete } from 'react-native-google-places-autocomplete';
import {colors} from "../../../infrastructure/theme/colors";
import {Ionicons} from "@expo/vector-icons";
import { Text } from "../../../components/typography/text";
import { MAP_SUBMIT_LABEL } from "../../../constants/constants";
import {
    MapContainer,
    Map,
    SubmitButton,
    SearchContainer,
    SubmitButtonContainer,
    AddressInfoContainer
} from "../components/map.styles";
import { getAddressFromCoordinates } from "../../../services/utils/utils";
import { GOOGLE_API_KEY } from "@env";
import {setProblemLocation} from "../../../services/redux/actions/problem.actions";

export const MapScreen = ({navigation}) => {

    const dispatch = useDispatch();
    const [region, setRegion] = useState(null);
    const [marker, setMarker] = useState(null);
    const [selectedPlace, setSelectedPlace] = useState(null);
    const handleSelectPlace = (place, details = null) => {
        setSelectedPlace(place["description"]);
        if (details !== null) {
            const { location, viewport } = details.geometry;
            const { lat, lng } = location;
            const northeastLat = viewport.northeast.lat;
            const southwestLat = viewport.southwest.lat;
            setMarker({ latitude: lat, longitude: lng });
            setRegion({
                latitude: lat,
                longitude: lng,
                latitudeDelta: northeastLat - southwestLat,
                // latitudeDelta: 0.00069,
                longitudeDelta: 0.015,
            });
        }
    };

    useEffect(() => {
        (async () => {
            const { granted } = await requestForegroundPermissionsAsync();
            if (granted) {
                const { coords } = await getCurrentPositionAsync();
                const { latitude, longitude } = coords;
                getAddressFromCoordinates(latitude, longitude, GOOGLE_API_KEY)
                    .then(res => setSelectedPlace(res));
                setRegion({
                    latitude,
                    longitude,
                    latitudeDelta: 0.00069,
                    longitudeDelta: 0.015,
                });
                setMarker({ latitude, longitude });
            } else {
                alert('Location permission denied');
                navigation.navigate("ProblemReportScreen");
            }
        })();
    }, []);

    const handleMapPress = event => {
        const { latitude, longitude } = event.nativeEvent.coordinate;
        getAddressFromCoordinates(latitude, longitude, GOOGLE_API_KEY)
            .then(res => setSelectedPlace(res));
        setMarker({ latitude, longitude });
    };

    return (
        <MapContainer>
            {region ? (
                <>
                    <Map
                        showsUserLocation={true}
                        region={region}
                        onPress={handleMapPress}
                    >
                        {marker && <Marker coordinate={marker}/>}
                    </Map>
                    <SearchContainer >
                        <GooglePlacesAutocomplete
                            renderLeftButton={() => <Ionicons style={{padding: 8}} name="map" size={20} color={colors.brand.secondary} />}
                            placeholder="Search"
                            fetchDetails={true}
                            onPress={handleSelectPlace}
                            query={{
                                key: GOOGLE_API_KEY,
                                language: 'pl'
                            }}
                            styles={{
                                textInputContainer: styles.textInputContainer,
                                textInput: styles.textInput,
                                listView: styles.listView,
                                poweredContainer: styles.poweredContainer,
                                separator: styles.separator,
                            }}
                        />
                        <AddressInfoContainer style={{ marginTop: 5, padding: 5 }}>
                            <Text variant="boldLabel">
                                {selectedPlace}
                            </Text>
                        </AddressInfoContainer>
                    </SearchContainer>
                    <SubmitButtonContainer>
                        <SubmitButton
                            mode="contained"
                            icon="send"
                            onPress={() => {
                                const location = {
                                    fullAddress: selectedPlace,
                                    latitude: region.latitude,
                                    longitude: region.longitude
                                };
                                dispatch(setProblemLocation(location));
                                //TODO save problem here
                                Application.getIosIdForVendorAsync()
                                    .then(res => console.log(res));
                                navigation.navigate("ReportProblemSummaryScreen")
                            }
                            }>
                            <Text variant="lightLabel">
                                {MAP_SUBMIT_LABEL}
                            </Text>
                        </SubmitButton>
                    </SubmitButtonContainer>
                </>
            ) : (
                <Background>
                    <BackgroundCover />
                    <LoadingContainer>
                        <Loading
                            size={50}
                            animating={true}
                            color={colors.brand.primary}
                        />
                    </LoadingContainer>
                </Background>
            )}
        </MapContainer>
    )
}

const styles = StyleSheet.create({
    textInputContainer: {
        backgroundColor: '#fff',
        borderTopWidth: 0,
        borderBottomWidth: 0,
    },
    textInput: {
        marginLeft: 0,
        marginRight: 0,
        height: 38,
        color: '#5d5d5d',
        fontSize: 16,
    },
    listView: {
        backgroundColor: '#fff',
        marginTop: 8,
        borderWidth: 1,
        borderColor: '#e2e2e2',
    },
    poweredContainer: {
        display: 'none',
    },
    separator: {
        backgroundColor: '#e2e2e2',
    }
});