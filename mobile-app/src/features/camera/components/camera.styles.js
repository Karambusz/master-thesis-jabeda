import styled from "styled-components/native";
import { Camera } from "expo-camera";

export const ReportProblemCamera = styled(Camera)`
  width: 100%;
  height: 100%;
`;

export const TakePhotoButtonContainer = styled.View`
  flex: 1;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 36px;  
`;

export const TakePhotoButton = styled.TouchableOpacity`
  width: 70px;
  height: 70px;
  border-radius: 50px;
  background-color: ${(props) => props.theme.colors.ui.quaternary};
`;