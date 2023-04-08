import styled from "styled-components/native";
import WebView from "react-native-webview";

export const CompactProblemSummaryWrapper = styled.View`
  padding: 10px;
  background-color: rgba(255, 255, 255, 0.7);
  flex-direction: row;
  justify-content: space-between;
`;

export const ProblemImageWrapper = styled.View`
  height: 210px;
  width: 50%;
`;

export const ProblemInformationWrapper = styled.View`
  height: 210px;
  width: 45%;
`;

export const CompactImage = styled.Image`
    resize-mode: cover;
    border-radius: 10px;
    width: 100%;
    height: 210px;
`;

export const CompactWebview = styled(WebView)`
    border-radius: 10px;
    width: 100%;
    height: 210px;
`;


export const ImageWrapper = styled.View`
    max-height: 210px;
    align-items: center;
`;