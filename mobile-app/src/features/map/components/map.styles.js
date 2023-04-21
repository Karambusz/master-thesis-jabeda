import styled from "styled-components/native";
import {Button} from "react-native-paper";
import { colors } from "../../../infrastructure/theme/colors";
import MapView from "react-native-maps";

export const MapContainer = styled.View`
  flex: 1;
`;

export const Map = styled(MapView)`
  flex: 1;
`;

export const SearchContainer = styled.View`
  position: absolute;
  top: 15px;
  left: 0;
  right: 0;
  z-index: 1;
  padding: 16px;
`;

export const AddressInfoContainer = styled.View`
  padding: ${(props) => props.theme.space[1]};
  background-color:  ${(props) => props.theme.colors.brand.muted};
  opacity: 0.75;
  border-radius: ${(props) => props.theme.space[1]};
  margin-top: 5px;
`;

export const SubmitButtonContainer = styled.View`
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
  align-items: center;
`;

export const SubmitButton = styled(Button).attrs({
    buttonColor: colors.brand.primary
})`
  padding: ${(props) => props.theme.space[1]};
  width: 70%;
`;