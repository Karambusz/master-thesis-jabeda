import styled from "styled-components/native";
import WebView from "react-native-webview";
import { Searchbar, Button } from "react-native-paper";
import {colors} from "../../../infrastructure/theme/colors";

export const CompactImage = styled.Image`
    resize-mode: cover;
    border-radius: 10px;
    width: 100%;
    height: 240px;
`;

export const CompactWebview = styled(WebView)`
    border-radius: 10px;
    width: 100%;
    height: 240px;
`;


export const ImageWrapper = styled.View`
    padding: 10px;
    max-height: 240px;
    align-items: center;
`;

export const LocationSearch = styled(Searchbar)`
  border-radius: 4px;
  shadow-color: #000;
  background-color: #fff;
  shadow-radius: 3px;
  shadow-opacity: 0.15;
`;

export const SubmitButtonContainer = styled.View`
  justify-content: flex-end;
  align-items: center;
`;

export const SubmitButton = styled(Button).attrs({
    buttonColor: colors.brand.primary
})`
  padding: ${(props) => props.theme.space[1]};
  width: 50%;
`;
