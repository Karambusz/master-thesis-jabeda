import React, { useState } from "react";
import { colors } from "../../../infrastructure/theme/colors";
import { StyleSheet } from "react-native";
import { Text } from "../../../components/typography/text";
import { Spacer } from "../../../components/spacer/spacer";
import { Background, BackgroundCover } from "../../../components/background/background";
import {MainMenuContainer, MenuButton } from "../components/main-menu.styles";
import { Ionicons } from "@expo/vector-icons";
import { Snackbar } from "react-native-paper";

export const MainMenuScreen = () => {
    const isHistoryExist = false; //TODO change during history development
    const [visible, setVisible] = useState(false);
    const onToggleSnackBar = () => setVisible(!visible);
    const onDismissSnackBar = () => setVisible(false);
    return (
        <Background>
            <BackgroundCover/>
            <MainMenuContainer>
                <MenuButton
                    mode="contained"
                    icon="camera"
                    labelStyle={{textAlign: "left"}}
                    onPress={() => console.log('Pressed report')}
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
                            onToggleSnackBar();
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
            <Snackbar
                visible={visible}
                elevation={3}
                onDismiss={onDismissSnackBar}
                action={{
                    label: <Ionicons name="close" size={20} color={colors.ui.error} />
                }}>
                <Text variant="lightLabel">
                    Zgłoś problem, aby mieć dostęp do historii
                </Text>
            </Snackbar>
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