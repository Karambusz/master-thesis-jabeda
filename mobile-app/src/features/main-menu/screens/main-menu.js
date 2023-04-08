import React from "react";
import { StyleSheet } from "react-native";
import { Text } from "../../../components/typography/text";
import { Spacer } from "../../../components/spacer/spacer";
import { MainMenuBackground, BackgroundCover } from "../../../components/background/background";
import { MainMenuContainer, MenuButton } from "../components/main-menu.styles";
import { toastConfig } from "../utility/main-screen-toast-config";
import Toast from 'react-native-toast-message';
import {
    HISTORY_BUTTON_LABEL,
    HISTORY_TOAST_TEXT1_MESSAGE, HISTORY_TOAST_TEXT2_MESSAGE,
    REPORT_PROBLEM_BUTTON_LABEL
} from "../../../constants/constants";

export const MainMenuScreen = ({navigation}) => {

    const isHistoryExist = false; //TODO change during history development
    return (
        <MainMenuBackground>
            <BackgroundCover/>
            <MainMenuContainer>
                <MenuButton
                    mode="contained"
                    icon="camera"
                    onPress={() => {
                        console.log('Pressed report');
                        navigation.navigate("CameraScreen");
                    }}
                >
                    <Text variant="lightLabel">
                        {REPORT_PROBLEM_BUTTON_LABEL}
                    </Text>
                </MenuButton>
                <Spacer size="large"/>
                <MenuButton
                    mode="contained"
                    icon="history"
                    style={[isHistoryExist ? styles.historyExist : styles.historyNotExist]}
                    labelStyle={{textAlign: "left"}}
                    onPress={() => {
                        if (!isHistoryExist) {
                            Toast.show({
                                type: 'info',
                                text1: HISTORY_TOAST_TEXT1_MESSAGE,
                                text2: HISTORY_TOAST_TEXT2_MESSAGE,
                                position: 'bottom',
                                visibilityTime: 3000,
                                autoHide: true,
                                bottomOffset: 20
                            });
                        } else {
                            console.log("Pressed history")
                            //TODO navigate to history screen
                        }
                    }}>
                    <Text variant="lightLabel">
                        {HISTORY_BUTTON_LABEL}
                    </Text>
                </MenuButton>
            </MainMenuContainer>
            <Toast config={toastConfig} />
        </MainMenuBackground>
    )
}

const styles = StyleSheet.create({
    historyExist: {
        opacity: 1
    },
    historyNotExist: {
        opacity:0.5
    },
});