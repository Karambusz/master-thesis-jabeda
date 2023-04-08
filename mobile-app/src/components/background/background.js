import styled from "styled-components/native";

export const MainMenuBackground = styled.ImageBackground.attrs({
    source: require("../../../assets/background-image.jpg")
})`
    flex: 1;
    background-color: #ddd;
    align-items: center;
    justify-content: center;
`;

export const Background = styled.ImageBackground.attrs({
    source: require("../../../assets/background-image.jpg")
})`
  flex: 1;
  background-color: #ddd;
`;

export const BackgroundCover = styled.View`
      position: absolute;
      width: 100%;
      height: 100%;
      background-color: rgba(255, 255, 255, 0.4);
`;