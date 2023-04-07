import styled from "styled-components/native";
import { Button } from "react-native-paper";
import {colors} from "../../../infrastructure/theme/colors";

export const CameraPreviewButtonContainer = styled.View`
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 16px;
  justify-content: space-between;
  align-items: center;
  flex-direction: row;
`;


export const CameraPreviewButton = styled(Button).attrs({
    buttonColor: colors.brand.primary
})`
  padding: ${(props) => props.theme.space[1]};
  width: 140px;
`;