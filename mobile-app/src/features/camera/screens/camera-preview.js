import React from "react";
import {View, ImageBackground, Text, Button} from "react-native";

export const CameraPreview = ({photo, retakePhoto}) => {
    console.log('photo object', photo)
    return (
        <View
            style={{
                backgroundColor: 'transparent',
                flex: 1,
                width: '100%',
                height: '100%'
            }}
        >
            <ImageBackground
                source={{uri: photo && photo.uri}}
                style={{
                    flex: 0.8
                }}
            />
            <View>
                <Button onPress={retakePhoto} title="Retake" />
            </View>
        </View>
    )
}