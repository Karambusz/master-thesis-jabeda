import React from "react";
import Toast, { InfoToast } from "react-native-toast-message";
import { TouchableOpacity } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { colors } from "../../../infrastructure/theme/colors";

export const toastConfig = {
    info: (props) => (
        <InfoToast
            {...props}
            text2Style={{
                fontSize: 12,
                color: colors.text.secondary
            }}
            renderTrailingIcon={() => (
                <TouchableOpacity style={{padding: 8}} onPress={() => Toast.hide()}>
                    <Ionicons name="close" size={20} color={colors.ui.error} />
                </TouchableOpacity>
            )}
        />
    )
}