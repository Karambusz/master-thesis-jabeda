import React from "react";
import { useDispatch } from "react-redux";
import { CameraPreviewButtonContainer, CameraPreviewButton } from "../components/camera-preview.styles";
import { View, ImageBackground } from "react-native";
import { Text } from "../../../components/typography/text";
import {PHOTO_ACCEPT_MESSAGE, PHOTO_RETAKE_MESSAGE} from "../../../constants/constants";
import { setProblemPhoto, sendPhotoToPredict } from "../../../services/redux/actions/problem.actions";

export const CameraPreview = ({ photo, retakePhoto, navigation }) => {
    const dispatch = useDispatch();
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
                        {PHOTO_RETAKE_MESSAGE}
                    </Text>
                </CameraPreviewButton>
                <CameraPreviewButton
                    mode="contained"
                    icon="send"
                    onPress={() => {
                        dispatch(setProblemPhoto(photo));
                        console.log(Object.keys(photo));
                        dispatch(sendPhotoToPredict(photo.base64));
                        navigation.navigate("ProblemReportScreen");
                    }}
                >
                    <Text variant="lightLabel">
                        {PHOTO_ACCEPT_MESSAGE}
                    </Text>
                </CameraPreviewButton>
            </CameraPreviewButtonContainer>
        </View>
    )
}