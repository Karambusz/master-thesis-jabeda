import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { SafeArea } from "../../../components/utils/safe-area";
import { Text } from "../../../components/typography/text";
import { Spacer } from "../../../components/spacer/spacer";
import { Background, BackgroundCover } from "../../../components/background/background";
import { CompactProblemSummary } from "../../../components/compact-problem-summary/compact-problem-summary";
import { Ionicons } from "@expo/vector-icons";
import {
    HeaderContainer,
    HeaderSuccessContentWrapper, HomeButtonWrapper, HomeButton,
    IconWrapper,
    MessagesWrapper,
    FooterWrapper,
    Footer, HeaderErrorContentWrapper
} from "../components/problem-report-summary.styles";
import {
    GO_HOME_BUTTON_LABEL, PROBLEM_SUMMARY_ERROR_MESSAGE, PROBLEM_SUMMARY_ERROR_TITLE,
    PROBLEM_SUMMARY_SUCCESS_MESSAGE,
    PROBLEM_SUMMARY_SUCCESS_TITLE,
} from "../../../constants/constants";
import {clearProblem, getReportedProblemHistory} from "../../../services/redux/actions/problem.actions";
import { Loading, LoadingContainer } from "../../../components/loading/loading";
import { colors } from "../../../infrastructure/theme/colors";
import * as Application from "expo-application";


export const ReportProblemSummaryScreen = ({ navigation }) => {
    const { photo, problemCategory, problem, location, problemDescription,
        isProblemReportedError, isProblemReportedLoading } = useSelector(state => state.problems);
    const dispatch = useDispatch();

    return (
        <Background>
            <BackgroundCover />
            { isProblemReportedLoading ? (
                <LoadingContainer>
                    <Loading
                        size={50}
                        animating={true}
                        color={colors.brand.primary}
                    />
                </LoadingContainer>
            ) : (
                <SafeArea style={{padding: 10}}>
                    <HeaderContainer>
                        {
                            !isProblemReportedError ? (
                                <HeaderSuccessContentWrapper>
                                    <IconWrapper>
                                        <Ionicons name="checkmark-circle-sharp" size={30} color={"#fff"} />
                                    </IconWrapper>
                                    <MessagesWrapper>
                                        <Text variant="headerLabel">
                                            {PROBLEM_SUMMARY_SUCCESS_TITLE}
                                        </Text>
                                        <Text>
                                            {PROBLEM_SUMMARY_SUCCESS_MESSAGE}
                                        </Text>
                                    </MessagesWrapper>
                                </HeaderSuccessContentWrapper>
                            ) : (
                                <HeaderErrorContentWrapper>
                                    <IconWrapper>
                                        <Ionicons name="warning" size={30} color={"#fff"} />
                                    </IconWrapper>
                                    <MessagesWrapper>
                                        <Text variant="headerLabel">
                                            {PROBLEM_SUMMARY_ERROR_TITLE}
                                        </Text>
                                        <Text>
                                            {PROBLEM_SUMMARY_ERROR_MESSAGE}
                                        </Text>
                                    </MessagesWrapper>
                                </HeaderErrorContentWrapper>
                            )
                        }
                    </HeaderContainer>
                    <FooterWrapper>
                        <CompactProblemSummary
                            status={"Pending"}
                            date={new Date().toISOString().replace(/.\d+Z$/g, "Z")}
                            photo={photo}
                            problemCategory={problemCategory}
                            problem={problem}
                            location={location.fullAddress}
                            isHistory={false}
                        />
                        <Footer>
                            <Spacer position="top" size="medium" />
                            <Text variant="headerLabel">
                                Opis
                            </Text>
                            <Spacer position="top" size="small" />
                            <Text>
                                {problemDescription}
                            </Text>
                            <Spacer position="top" size="large" />
                            <Spacer position="top" size="large" />
                            <HomeButtonWrapper>
                                <HomeButton
                                    mode="contained"
                                    icon="home"
                                    onPress={() => {
                                        dispatch(clearProblem());
                                        Application.getIosIdForVendorAsync()
                                            .then(deviceId => {
                                                dispatch(getReportedProblemHistory(deviceId));
                                            });
                                        navigation.navigate("MainMenuScreen");
                                    }
                                    }
                                >
                                    <Text variant="lightLabel">
                                        {GO_HOME_BUTTON_LABEL}
                                    </Text>
                                </HomeButton>
                            </HomeButtonWrapper>
                            <Spacer position="top" size="large" />
                            <Spacer position="top" size="large" />
                        </Footer>
                    </FooterWrapper>
                </SafeArea>
            )}
        </Background>
    )
}



