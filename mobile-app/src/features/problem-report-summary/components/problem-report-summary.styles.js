import styled from "styled-components/native";
import { Button } from "react-native-paper";
import { colors } from "../../../infrastructure/theme/colors";

export const HeaderContainer = styled.View`
  margin-top: 20px;
  padding: 10px;
`;

export const HeaderSuccessContentWrapper = styled.View`
  background-color: ${(props) => props.theme.colors.ui.success};
  border-radius: 4px;
  height: 45px;
  padding: 4px;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

export const HeaderErrorContentWrapper = styled.View`
  background-color: ${(props) => props.theme.colors.ui.error};
  border-radius: 4px;
  height: 45px;
  padding: 4px;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

export const IconWrapper = styled.View`
  width: 10%;
`;

export const MessagesWrapper = styled.View`
  width: 90%;
`;

export const FooterWrapper = styled.View`
  padding: 10px;
`;

export const Footer = styled.View`
  padding: 10px;
  background-color: rgba(255, 255, 255, 0.7);
`;

export const HomeButtonWrapper = styled.View`
  justify-content: flex-end;
  align-items: center;
`;

export const HomeButton = styled(Button).attrs({
    buttonColor: colors.brand.primary
})`
  padding: ${(props) => props.theme.space[1]};
  width: 80%;
`;

