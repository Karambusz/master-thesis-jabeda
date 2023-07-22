import React, { useState } from "react";
import { Platform } from "react-native";
import {
    CompactImage, CompactProblemSummaryWrapper,
    CompactWebview,
    ImageWrapper, ProblemImageWrapper, ProblemInformationWrapper
} from "./compact-problem-summary.styles";
import { Text } from "../typography/text";
import { Spacer } from "../spacer/spacer";
import { Loading, LoadingContainer } from "../loading/loading";
import { colors } from "../../infrastructure/theme/colors";
import { STATUSES_MAP } from "../../constants/constants";


const isAndroid = Platform.OS === "android";
export const CompactProblemSummary = ({ photo, photoHistory, problem, problemCategory, location, status, date, isHistory }) => {
    const Image = isAndroid ? CompactWebview : CompactImage;
    const photoUri = photo && photo["uri"] ? photo["uri"] : photoHistory;
    const [isImageLoading, setIsImageLoading] = useState(true);

    const handleImageLoad = () => {
        setIsImageLoading(false);
    };

    return (
        <CompactProblemSummaryWrapper >
            <ProblemImageWrapper>
                <ImageWrapper>
                    { isImageLoading && (
                        <LoadingContainer>
                            <Loading
                                size={50}
                                animating={true}
                                color={colors.brand.primary}
                            />
                        </LoadingContainer>)
                    }
                    <Image source={{ uri: photoUri}} onLoad={handleImageLoad} />
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
                    <Spacer position="top" size="medium" />
                    <Spacer position="top" size="medium" />
                    <Text variant="headerLabel">
                        Status
                    </Text>
                    <Spacer position="top" size="small" />
                    <Text>
                        {STATUSES_MAP[status]} {status}
                    </Text>
                    <Spacer position="top" size="medium" />
                    {isHistory && (
                            <>
                                <Text variant="headerLabel">
                                    Data zgłoszenia
                                </Text>
                                <Spacer position="top" size="small" />
                                <Text>
                                    {date ? date.split("T")[0] : null}
                                </Text>
                            </>
                    )}
            </ProblemInformationWrapper>
        </CompactProblemSummaryWrapper>
    )
}