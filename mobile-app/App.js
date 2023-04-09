import React from "react";
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { Provider as PaperProvider } from 'react-native-paper';
import { Provider as ReduxProvider} from 'react-redux';
import { theme } from "./src/infrastructure/theme";
import { ThemeProvider } from "styled-components/native";
import {
    useFonts as useOswald,
    Oswald_400Regular,
} from "@expo-google-fonts/oswald";
import {
    useFonts as useLato,
    Lato_400Regular
} from "@expo-google-fonts/lato";
import { Navigation } from "./src/infrastructure/navigation";
import * as Linking from 'expo-linking';
import { store } from "./src/services/redux/store";

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
    // Linking.openURL('app-settings:');
    return (
      <ThemeProvider theme={theme}>
          <SafeAreaProvider>
              <PaperProvider>
                  <ReduxProvider store={store}>
                      <Navigation />
                  </ReduxProvider>
              </PaperProvider>
          </SafeAreaProvider>
      </ThemeProvider>
    );
}
