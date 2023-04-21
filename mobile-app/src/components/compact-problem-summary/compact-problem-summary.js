import React from "react";
import { Platform } from "react-native";
import {
    CompactImage, CompactProblemSummaryWrapper,
    CompactWebview,
    ImageWrapper, ProblemImageWrapper, ProblemInformationWrapper
} from "./compact-problem-summary.styles";
import { Text } from "../typography/text";
import { Spacer } from "../spacer/spacer";


const isAndroid = Platform.OS === "android";
export const CompactProblemSummary = ({ photo, problem, problemCategory, location }) => {
    const Image = isAndroid ? CompactWebview : CompactImage;
    const photoUri = photo && photo["uri"] ? photo["uri"] : 'https://reactnative.dev/img/tiny_logo.png';

    return (
        <CompactProblemSummaryWrapper >
            <ProblemImageWrapper>
                <ImageWrapper>
                    <Image source={{ uri: photoUri}} />
                </ImageWrapper>
            </ProblemImageWrapper>
            <ProblemInformationWrapper>
                    <Text variant="headerLabel">
                        Lokalizacja
                    </Text>
                    <Spacer position="top" size="small" />
                    <Text>
                        {location}
                    </Text>
                    <Spacer position="top" size="medium" />
                    <Spacer position="top" size="medium" />
                    <Text variant="headerLabel">
                        Kategoria
                    </Text>
                    <Spacer position="top" size="small" />
                    <Text>
                        {problemCategory}
                    </Text>
                    <Spacer position="top" size="medium" />
                    <Spacer position="top" size="medium" />
                    <Text variant="headerLabel">
                        Problem
                    </Text>
                    <Spacer position="top" size="small" />
                    <Text>
                        {problem}
                    </Text>
            </ProblemInformationWrapper>
        </CompactProblemSummaryWrapper>
    )
}