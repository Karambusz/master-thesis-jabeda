import React from "react";
import { SafeArea } from "../../../components/utils/safe-area";
import { Text } from "../../../components/typography/text";
import { Spacer } from "../../../components/spacer/spacer";
import { Background, BackgroundCover } from "../../../components/background/background";
import { CompactProblemSummary } from "../../../components/compact-problem-summary/compact-problem-summary";
import { Ionicons } from "@expo/vector-icons";
import {
    HeaderContainer,
    HeaderContentWrapper, HomeButtonWrapper, HomeButton,
    IconWrapper,
    MessagesWrapper,
    FooterWrapper,
    Footer
} from "../components/problem-report-summary.styles";
import {
    GO_HOME_BUTTON_LABEL,
    PROBLEM_SUMMARY_SUCCESS_MESSAGE,
    PROBLEM_SUMMARY_SUCCESS_TITLE,
} from "../../../constants/constants";


export const ReportProblemSummaryScreen = ({route, navigation}) => {
    const { photo } = route.params;
    return (
        <Background>
            <BackgroundCover />
            <SafeArea style={{padding: 10}}>
                <HeaderContainer>
                    <HeaderContentWrapper>
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
                    </HeaderContentWrapper>
                </HeaderContainer>
                <FooterWrapper>
                    <CompactProblemSummary photo={photo}/>
                    <Footer>
                        <Spacer position="top" size="medium" />
                        <Text variant="headerLabel">
                            Opis
                        </Text>
                        <Spacer position="top" size="small" />
                        <Text>
                            Zasady segregacji odpadów na pięć frakcji. Podręczna ściąga segrega bla bla bla.
                        </Text>
                        <Spacer position="top" size="large" />
                        <Spacer position="top" size="large" />
                        <HomeButtonWrapper>
                            <HomeButton
                                mode="contained"
                                icon="home"
                                onPress={() => {
                                    navigation.navigate("MainMenuScreen", {
                                        isHistoryExist: true
                                    });
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
        </Background>
    )
}



