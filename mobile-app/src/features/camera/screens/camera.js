import React, { useRef, useState } from "react";
import { PinchGestureHandler } from "react-native-gesture-handler";
import { Camera, CameraType } from "expo-camera";
import { CameraPreview } from "./camera-preview";
import { Text } from "../../../components/typography/text";
import { Button, View } from 'react-native';
import { ReportProblemCamera, TakePhotoButtonContainer, TakePhotoButton } from "../components/camera.styles";


export const CameraScreen = ({navigation, ...props}) => {
    const cameraRef = useRef();
    const [zoom, setZoom] = useState(0);
    const [previewVisible, setPreviewVisible] = useState(false);
    const [capturedImage, setCapturedImage] = useState(null);

    const [permission, requestPermission] = Camera.useCameraPermissions();

    const onPinchGestureEvent = (nativeEvent) => {
        const scale = nativeEvent.nativeEvent.scale;
        const velocity = nativeEvent.nativeEvent.velocity / 20;

        let newZoom =
            velocity > 0
                ? zoom + scale * velocity * 0.01
                : zoom -
                scale * Math.abs(velocity) * 0.02

        if (newZoom < 0) newZoom = 0;
        else if (newZoom > 0.5) newZoom = 0.5;

        setZoom(newZoom)
    };
    const takePhoto = async () => {
        if (cameraRef) {
            const photo = await cameraRef.current.takePictureAsync();
            console.log(photo);
            setPreviewVisible(true)
            setCapturedImage(photo);
        }
    };

    const retakePhoto = () => {
        console.log("click retake");
        setCapturedImage(null)
        setPreviewVisible(false)
    }

    console.log(permission);

    if (!permission) {
        // Camera permissions are still loading
        return <View />
    }

    if (!permission.granted) {
        // Camera permissions are not granted yet
        return (
            <View style={{flex: 1, justifyContent: 'center'}}>
                <Text style={{ textAlign: 'center' }}>Aby zgłosić problem, musisz nadać dostęp do swojej kamery</Text>
                <Button onPress={requestPermission} title="Nadaj pozwolenie" />
            </View>
        );
    }

    return (
        <>
        {
            previewVisible && capturedImage ? (
                    <CameraPreview photo={capturedImage} retakePhoto={retakePhoto} />
            )
            : (
                <PinchGestureHandler
                    onGestureEvent={onPinchGestureEvent}
                >
                    <ReportProblemCamera
                        ref={camera => (cameraRef.current = camera)}
                        type={CameraType.back}
                        zoom={zoom}>
                        <TakePhotoButtonContainer>
                            <TakePhotoButton
                                onPress={takePhoto}
                            />
                        </TakePhotoButtonContainer>
                    </ReportProblemCamera>
                </PinchGestureHandler>
            )
        }
        </>
        )

}