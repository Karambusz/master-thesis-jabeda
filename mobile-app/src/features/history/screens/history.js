import React from "react";
import { SafeArea } from "../../../components/utils/safe-area";
import { FlatList } from "react-native";
import { CompactProblemSummary } from "../../../components/compact-problem-summary/compact-problem-summary";
import { Background, BackgroundCover } from "../../../components/background/background";
import { Spacer } from "../../../components/spacer/spacer";

export const HistoryScreen = ({ route }) => {
    const { reportedProblemHistory } = route.params;
    return (
        <Background>
            <BackgroundCover />
            <SafeArea>
                <FlatList data={reportedProblemHistory} renderItem={({item}) => (
                    <>
                        <CompactProblemSummary
                            photoHistory={item.imageUrl}
                            problemCategory={item.problem.category}
                            problem={item.problem.problemName}
                            location={item.reportedProblemAddress.address}
                            status={item.problemStatusCode}
                            date={item.reportedDateTime}
                            isHistory={true}
                        />
                        <Spacer position="top" size="medium" />
                        <Spacer position="top" size="medium" />
                    </>
                )} />
            </SafeArea>
        </Background>
    )
}