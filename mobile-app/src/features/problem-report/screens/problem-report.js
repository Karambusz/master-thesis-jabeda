import React, { useState } from "react";
import { Spacer } from "../../../components/spacer/spacer";
import { colors } from "../../../infrastructure/theme/colors";
import {Background, BackgroundCover} from "../../../components/background/background";
import {
    ImageWrapper,
    CompactImage,
    CompactWebview,
    LocationSearch,
    SubmitButtonContainer,
    SubmitButton
} from "../components/problem-report.styles";
import { Platform, View, TouchableWithoutFeedback, Keyboard, ScrollView, StyleSheet } from "react-native";
import { SafeArea } from "../../../components/utils/safe-area";
import { TextInput } from 'react-native-paper';
import { PaperSelect } from 'react-native-paper-select';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view'
import Toast from "react-native-toast-message";
import {toastConfig} from "../../main-menu/utility/main-screen-toast-config";
import {Text} from "../../../components/typography/text";
import {
    CATEGORY_ERROR_MESSAGE,
    CATEGORY_LABEL, DESCRIPTION_LABEL, LOCATION_SEARCH_PLACEHOLDER,
    MODAL_CLOSE_BUTTON_LABEL,
    MODAL_DONE_BUTTON_LABEL, PROBLEM_ERROR_MESSAGE, PROBLEM_LABEL,
    PROBLEM_TOAST_TEXT1_MESSAGE, PROBLEM_TOAST_TEXT2_MESSAGE, REPORT_PROBLEM_SUBMIT_LABEL
} from "../../../constants/constants";

export const selectValidator = (value, errorText = '') => {
    if (!value || value.length <= 0) {
        return errorText;
    }

    return '';
};

const isAndroid = Platform.OS === "android";
export const ProblemReportScreen = ({route}) => {
    const { photo } = route.params;
    const Image = isAndroid ? CompactWebview : CompactImage;
    //TODO change when server will be ready
    const problemsObject = [
        { _id: '1', value: 'Brak ciepłej wody' },
        { _id: '2', value: 'Płonący kosz na śmieci' },
        { _id: '3', value: 'Uszkodzone zabytki architektury' },
        { _id: '4', value: 'Przepełniony kosz uliczny' },
        { _id: '5', value: 'Niesprawny automat biletowy' },
        { _id: '6', value: 'Awantura domowa' }
    ];

    const [isProblemListDisabled, setProblemListDisabled] = useState(true);
    const [categoryList, setCategoryList] = useState({
        value: '',
        list: [
            { _id: '1', value: 'Woda' },
            { _id: '2', value: 'Ogień' },
            { _id: '3', value: 'Zniszczenia' },
            { _id: '4', value: 'Śmiecie i segregacja' },
            { _id: '5', value: 'Komunikacja publiczna' },
            { _id: '6', value: 'Sąsiad' },
        ],
        selectedList: [],
        error: '',
    });

    const [problemList, setProblemList] = useState({
        value: '',
        list: [],
        selectedList: [],
        error: '',
    });
    const [description, setDescription] = useState('');
    const [descriptionError, setDescriptionError] = useState(false);

    return (
        <Background>
            <BackgroundCover/>
            <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
                <SafeArea>
                    <ScrollView>
                        <KeyboardAwareScrollView
                            behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
                            style={styles.container}
                        >
                            <ImageWrapper>
                                <Image source={{ uri: photo["uri"] }} />
                            </ImageWrapper>
                            <Spacer position="top" size="medium" />
                            <Spacer position="top" size="medium" />
                            <View
                                style={{padding: 10}
                            }>
                                <PaperSelect
                                    label={CATEGORY_LABEL}
                                    value={categoryList.value}
                                    onSelection={(value) => {
                                        //value = {"selectedList": [{"_id": "4", "value": "Śmiecie i segregacja"}], "text": "Śmiecie i segregacja"}
                                        setCategoryList({
                                            ...categoryList,
                                            value: value.text,
                                            selectedList: value.selectedList,
                                            error: '',
                                        });
                                        console.log(value);
                                        if (value.selectedList.length > 0) {
                                            const categoryId = value.selectedList[0]._id;
                                            setProblemList({
                                                ...problemList,
                                                list: [problemsObject[categoryId - 1]],
                                            });
                                            setProblemListDisabled(false);
                                        } else {
                                            setProblemList({
                                                ...problemList,
                                                list: [],
                                                selectedList: [],
                                                value: '',
                                                error: ''
                                            });
                                            setProblemListDisabled(true);
                                        }

                                    }}
                                    arrayList={[...categoryList.list]}
                                    selectedArrayList={[...categoryList.selectedList]}
                                    errorText={categoryList.error}
                                    errorStyle={{marginTop: 5}}
                                    multiEnable={false}
                                    hideSearchBox={true}
                                    activeOutlineColor={colors.brand.primary}
                                    outlineColor={categoryList.error ? colors.ui.error : colors.brand.primary}
                                    textInputColor={categoryList.error ? colors.ui.error : colors.brand.primary}
                                    modalCloseButtonText={MODAL_CLOSE_BUTTON_LABEL}
                                    modalDoneButtonText={MODAL_DONE_BUTTON_LABEL}
                                    dialogButtonLabelStyle={{color: colors.brand.primary}}
                                />
                                <Spacer position="top" size="medium" />
                                <Spacer position="top" size="medium" />
                                <TouchableWithoutFeedback onPress={() => {
                                    if (isProblemListDisabled) {
                                        Toast.show({
                                            type: 'info',
                                            text1: PROBLEM_TOAST_TEXT1_MESSAGE,
                                            text2: PROBLEM_TOAST_TEXT2_MESSAGE,
                                            position: 'bottom',
                                            visibilityTime: 3000,
                                            autoHide: true,
                                            bottomOffset: -60
                                        });
                                    }
                                }}>
                                    <View>
                                        <PaperSelect
                                            disabled={isProblemListDisabled}
                                            label={PROBLEM_LABEL}
                                            value={problemList.value}
                                            onSelection={(value) => {
                                                setProblemList({
                                                    ...problemList,
                                                    value: value.text,
                                                    selectedList: value.selectedList,
                                                    error: '',
                                                });
                                            }}
                                            arrayList={[...problemList.list]}
                                            selectedArrayList={[...problemList.selectedList]}
                                            errorText={problemList.error}
                                            errorStyle={{marginTop: 5}}
                                            multiEnable={false}
                                            hideSearchBox={true}
                                            activeOutlineColor={problemList.error ? colors.ui.error : colors.brand.primary}
                                            outlineColor={problemList.error ? colors.ui.error : colors.brand.primary}
                                            textInputColor={colors.text.primary}
                                            modalCloseButtonText={MODAL_CLOSE_BUTTON_LABEL}
                                            modalDoneButtonText={MODAL_DONE_BUTTON_LABEL}
                                            dialogButtonLabelStyle={{color: colors.brand.primary}}
                                        />
                                    </View>
                                </TouchableWithoutFeedback>
                                <Spacer position="top" size="medium" />
                                <Spacer position="top" size="medium" />
                                <LocationSearch
                                    placeholder={LOCATION_SEARCH_PLACEHOLDER}
                                    onIconPress={() => console.log("icon")}
                                    icon="map"
                                    value="test"
                                    onSubmitEditing={() => {
                                        console.log("edit");
                                    }}
                                    onChangeText={(text) => {
                                        console.log("search");
                                    }}
                                />
                                <Spacer position="top" size="medium" />
                                <Spacer position="top" size="medium" />
                                <Spacer position="top" size="medium" />
                                <TextInput
                                    contentStyle={{height: 120}}
                                    mode={"outlined"}
                                    outlineColor={colors.brand.primary}
                                    activeOutlineColor={colors.brand.primary}
                                    label={DESCRIPTION_LABEL}
                                    value={description}
                                    onChangeText={text => {
                                        setDescription(text);
                                        setDescriptionError(!(text && text.length > 0));
                                    }}
                                    numberOfLines={5}
                                    error={descriptionError}
                                />
                                <Spacer position="top" size="medium" />
                                <Spacer position="top" size="medium" />
                                <SubmitButtonContainer>
                                    <SubmitButton
                                        mode="contained"
                                        icon="send"
                                        onPress={() => {
                                            const categoryError = selectValidator(categoryList.value, CATEGORY_ERROR_MESSAGE);
                                            const problemError = selectValidator(problemList.value, PROBLEM_ERROR_MESSAGE);
                                            setCategoryList({ ...categoryList, error: categoryError });
                                            setProblemList({ ...problemList, error: problemError });
                                            setDescriptionError(!(description && description.length > 0));
                                            if (categoryError || categoryError || descriptionError) {
                                                return;
                                            }
                                            console.log("save");
                                        }
                                        }
                                    >
                                        <Text variant="lightLabel">
                                            {REPORT_PROBLEM_SUBMIT_LABEL}
                                        </Text>
                                    </SubmitButton>
                                </SubmitButtonContainer>
                                <Spacer position="top" size="large" />
                                <Spacer position="top" size="large" />
                            </View>
                        </KeyboardAwareScrollView>
                    </ScrollView>
                </SafeArea>
            </TouchableWithoutFeedback>
            <Toast config={toastConfig} />
        </Background>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 0.6,
    }
});