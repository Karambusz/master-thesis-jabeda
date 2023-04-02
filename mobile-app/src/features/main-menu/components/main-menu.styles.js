import styled from "styled-components/native";
import { colors } from "../../../infrastructure/theme/colors";
import { Button } from "react-native-paper";
export const MainMenuContainer = styled.View`
    background-color: rgba(255, 255, 255, 0.6);
    padding: ${(props) => props.theme.space[4]};
    margin-top: ${(props) => props.theme.space[2]};
    width: 70%;
`;

export const MenuButton = styled(Button).attrs({
    buttonColor: colors.brand.primary
})`
  padding: ${(props) => props.theme.space[1]};
`;


