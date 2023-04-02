import { StatusBar } from 'expo-status-bar';
import { StyleSheet, View } from 'react-native';
import { Text } from './src/components/typography/text'
import { theme } from "./src/infrastructure/theme";
import { ThemeProvider } from "styled-components/native";
import {
    useFonts as useOswald,
    Oswald_400Regular,
} from "@expo-google-fonts/oswald";
import {
    useFonts as useLato,
    Lato_400Regular } from "@expo-google-fonts/lato";

export default function App() {
    const [oswaldLoaded] = useOswald({
        Oswald_400Regular,
    });

    const [latoLoaded] = useLato({
        Lato_400Regular,
    });

    if (!oswaldLoaded || !latoLoaded) {
        return null;
    }
  return (
      <ThemeProvider theme={theme}>
        <View style={styles.container}>
          <Text variant="label">Text</Text>
          <StatusBar style="auto" />
        </View>
      </ThemeProvider>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
