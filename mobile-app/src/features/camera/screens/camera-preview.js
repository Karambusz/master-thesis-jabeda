import React from "react";
import { CameraPreviewButtonContainer, CameraPreviewButton } from "../components/camera-preview.styles";
import { View, ImageBackground } from "react-native";
import { Text } from "../../../components/typography/text";



export const CameraPreview = ({photo, retakePhoto}) => {
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
                    flex: 1
                }}
            />
            <CameraPreviewButtonContainer>
                <CameraPreviewButton
                    mode="contained"
                    icon="camera-retake"
                    onPress={retakePhoto}
                >
                    <Text variant="lightLabel">
                        Znowu
                    </Text>
                </CameraPreviewButton>
                <CameraPreviewButton
                    mode="contained"
                    icon="send"
                    onPress={() => {}}
                >
                    <Text variant="lightLabel">
                        Zgłoś
                    </Text>
                </CameraPreviewButton>
            </CameraPreviewButtonContainer>
        </View>
    )
}