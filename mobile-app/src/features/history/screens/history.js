import React from "react";
import { SafeArea } from "../../../components/utils/safe-area";
import { FlatList } from "react-native";
import {CompactProblemSummary} from "../../../components/compact-problem-summary/compact-problem-summary";
import { Background, BackgroundCover } from "../../../components/background/background";
import { Spacer } from "../../../components/spacer/spacer";

export const HistoryScreen = () => {
    const exampleHistoryArray = [1, 2, 3, 4];
    return (
        <Background>
            <BackgroundCover />
            <SafeArea>
                <FlatList data={exampleHistoryArray} renderItem={(item) => (
                    <>
                        <CompactProblemSummary
                            problemCategory={"Mock history category"}
                            problem={"Mock history problem"}
                            location={"Mock history location"}
                        />
                        <Spacer position="top" size="medium" />
                        <Spacer position="top" size="medium" />
                    </>
                )} />
            </SafeArea>
        </Background>
    )
}