import React from "react";

import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { TransitionPresets } from "@react-navigation/stack";
import { MainMenuScreen } from "../../features/main-menu/screens/main-menu";
import { CameraScreen } from "../../features/camera/screens/camera";
import { ProblemReportScreen } from "../../features/problem-report/screens/problem-report";

const AppStack = createNativeStackNavigator();

export const AppNavigator = () => {
    return (
        <NavigationContainer>
            <AppStack.Navigator
                screenOptions={{
                    headerShown: false,
                    ...TransitionPresets.SlideFromRightIOS
                }}
            >
                <AppStack.Screen
                    name="MainMenuScreen"
                    component={MainMenuScreen}
                />
                <AppStack.Screen
                    name="CameraScreen"
                    component={CameraScreen}
                />
                <AppStack.Screen
                    name="ProblemReportScreen"
                    component={ProblemReportScreen}
                />
            </AppStack.Navigator>
        </NavigationContainer>
    );
};
