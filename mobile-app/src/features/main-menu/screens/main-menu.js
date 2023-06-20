import React, {useEffect} from "react";
import { useDispatch, useSelector } from 'react-redux';
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
import { getCategoriesAndProblems, getReportedProblemHistory } from "../../../services/redux/actions/problem.actions";
import { Loading, LoadingContainer } from "../../../components/loading/loading";
import { colors } from "../../../infrastructure/theme/colors";
import * as Application from "expo-application";

export const MainMenuScreen = ({ navigation }) => {
    const { isReportedProblemHistoryLoading, reportedProblemHistory } = useSelector(state => state.problems);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getCategoriesAndProblems());
        Application.getIosIdForVendorAsync()
            .then(deviceId => {
                dispatch(getReportedProblemHistory(deviceId));
            });
    }, [])
    return (
        <MainMenuBackground>
            <BackgroundCover/>
            {!isReportedProblemHistoryLoading ? (
                <MainMenuContainer>
                    <MenuButton
                        mode="contained"
                        icon="camera"
                        onPress={() => {
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
                        style={[reportedProblemHistory.length > 0 ? styles.historyExist : styles.historyNotExist]}
                        labelStyle={{textAlign: "left"}}
                        onPress={() => {
                            if (!reportedProblemHistory) {
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
                                navigation.navigate("HistoryScreen", {
                                    reportedProblemHistory: reportedProblemHistory
                                });
                            }
                        }}>
                        <Text variant="lightLabel">
                            {HISTORY_BUTTON_LABEL}
                        </Text>
                    </MenuButton>
                </MainMenuContainer>
            ) : (
                <LoadingContainer>
                    <Loading
                        size={50}
                        animating={true}
                        color={colors.brand.primary}
                    />
                </LoadingContainer>
            )}
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