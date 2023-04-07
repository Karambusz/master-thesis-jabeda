import React, { useState } from "react";
import { StyleSheet } from "react-native";
import { Text } from "../../../components/typography/text";
import { Spacer } from "../../../components/spacer/spacer";
import { Background, BackgroundCover } from "../../../components/background/background";
import { MainMenuContainer, MenuButton } from "../components/main-menu.styles";
import { toastConfig } from "../utility/main-screen-toast-config";
import Toast from 'react-native-toast-message';

export const MainMenuScreen = ({navigation}) => {

    const isHistoryExist = false; //TODO change during history development
    return (
        <Background>
            <BackgroundCover/>
            <MainMenuContainer>
                <MenuButton
                    mode="contained"
                    icon="camera"
                    labelStyle={{textAlign: "left"}}
                    onPress={() => {
                        console.log('Pressed report');
                        navigation.navigate("CameraScreen")
                    }}
                >
                    <Text variant="lightLabel">
                        Zgłoś problem
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
                                text1: 'Uwaga',
                                text2: 'Zgłoś problem, aby mieć dostęp do historii',
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
                        Historia
                    </Text>
                </MenuButton>
            </MainMenuContainer>
            <Toast config={toastConfig} />
        </Background>
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