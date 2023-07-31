import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Spacer } from "../../../components/spacer/spacer";
import { colors } from "../../../infrastructure/theme/colors";
import { Background, BackgroundCover } from "../../../components/background/background";
import { Loading, LoadingContainer } from "../../../components/loading/loading";
import {
    ImageWrapper,
    CompactImage,
    CompactWebview,
    SubmitButtonContainer,
    ContinueButton
} from "../components/problem-report.styles";
import { Platform, View, TouchableWithoutFeedback, Keyboard, StyleSheet } from "react-native";
import { SafeArea } from "../../../components/utils/safe-area";
import { TextInput } from 'react-native-paper';
import { PaperSelect } from 'react-native-paper-select';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view'
import Toast from "react-native-toast-message";
import { toastConfig } from "../../main-menu/utility/main-screen-toast-config";
import { Text } from "../../../components/typography/text";
import {
    CATEGORY_ERROR_MESSAGE,
    CATEGORY_LABEL,
    DESCRIPTION_LABEL,
    MODAL_CLOSE_BUTTON_LABEL,
    MODAL_DONE_BUTTON_LABEL,
    PROBLEM_ERROR_MESSAGE,
    PROBLEM_LABEL,
    PROBLEM_TOAST_TEXT1_MESSAGE,
    PROBLEM_TOAST_TEXT2_MESSAGE,
    REPORT_PROBLEM_SUBMIT_LABEL,
    SUBMIT_TOAST_TEXT1_MESSAGE,
    SUBMIT_TOAST_TEXT2_MESSAGE,
    CATEGORY_TRANSLATED_VALUES,
    CATEGORY_PREDICTION_HELPER_TEXT,
    CATEGORY_PREDICTED_BY_YOLO,
    YOLO_DIDNT_PREDICT_CATEGORY
} from "../../../constants/constants";
import { setProblem, setProblemCategory, setProblemDescription } from "../../../services/redux/actions/problem.actions";
import * as Application from "expo-application";
import { Ionicons } from "@expo/vector-icons";

export const selectValidator = (value, errorText = '') => {
    if (!value || value.length <= 0) {
        return errorText;
    }

    return '';
};

const isAndroid = Platform.OS === "android";
export const ProblemReportScreen = ({navigation}) => {
    const { photo, problemCategory, problem, problemDescription, predictedProblemCategory,
        isProblemPredictLoading, categories, problems } = useSelector(state => state.problems);
    const dispatch = useDispatch();
    const Image = isAndroid ? CompactWebview : CompactImage;
    const [isProblemListDisabled, setProblemListDisabled] = useState(true);
    const [categoryList, setCategoryList] = useState({
        value: problemCategory,
        list: categories,
        selectedList: [],
        error: '',
    });

    const [problemList, setProblemList] = useState({
        value: problem,
        list: [],
        selectedList: [],
        error: '',
    });
    const [descriptionError, setDescriptionError] = useState(false);
    const [categoryId, setCategoryId] = useState(0);
    const [problemId, setProblemId] = useState(0);

    useEffect(() => {
        if (Object.keys(predictedProblemCategory).length > 0 && predictedProblemCategory.isDetected) {
            const categoryName = CATEGORY_TRANSLATED_VALUES[predictedProblemCategory.category];
            const selectedCategory = categories.filter(category => category.value === categoryName);
            // value = {"selectedList": [{"_id": "4", "value": "Śmiecie i segregacja"}], "text": "Śmiecie i segregacja"}
            dispatch(setProblemCategory(categoryName));
            setCategoryList({
                ...categoryList,
                value: categoryName,
                selectedList: selectedCategory,
                error: '',
            });
            setCategoryId(selectedCategory[0]._id);
            const filteredProblems = problems.filter(problem => Object.keys(problem)[0] === categoryName);
            setProblemList({
                ...problemList,
                list: filteredProblems[0][categoryName],
            });
            setProblemListDisabled(false);
        }
    }, [predictedProblemCategory])

    return (
        <Background>
            <BackgroundCover />
            {
                isProblemPredictLoading ? (
                        <LoadingContainer>
                            <Loading
                                size={50}
                                animating={true}
                                color={colors.brand.primary}
                            />
                        </LoadingContainer>
                ) : (
                        <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
                            <SafeArea>
                                    <KeyboardAwareScrollView
                                        keyboardShouldPersistTaps="always"
                                        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
                                        style={styles.container}
                                        removeClippedSubviews={false}
                                    >
                                        <ImageWrapper>
                                            <Image source={{ uri: photo["uri"] }} />
                                        </ImageWrapper>
                                        <Spacer position="top" size="medium" />
                                        <Spacer position="top" size="medium" />
                                        <View
                                            style={{padding: 10}}
                                        >
                                            {Object.keys(predictedProblemCategory).length > 0 && predictedProblemCategory.isDetected
                                             ? (<Text variant="success">
                                                    <Ionicons name="checkmark-circle-sharp" size={15} color={colors.ui.success} />
                                                    {CATEGORY_PREDICTED_BY_YOLO}
                                                </Text>) :
                                                (<Text variant="error">
                                                    <Ionicons name="ios-close-circle" size={15} color={colors.ui.error} />
                                                    {YOLO_DIDNT_PREDICT_CATEGORY}
                                                </Text>)
                                            }
                                            <Spacer position="top" size="medium" />
                                            <PaperSelect
                                                label={CATEGORY_LABEL}
                                                value={problemCategory}
                                                onSelection={(value) => {
                                                    //value = {"selectedList": [{"_id": "4", "value": "Śmiecie i segregacja"}], "text": "Śmiecie i segregacja"}
                                                    dispatch(setProblem(""));
                                                    dispatch(setProblemCategory(value.text));
                                                    setCategoryList({
                                                        ...categoryList,
                                                        value: value.text,
                                                        selectedList: value.selectedList,
                                                        error: '',
                                                    });
                                                    if (value.selectedList.length > 0) {
                                                        const categoryName = value.selectedList[0].value;
                                                        const filteredProblems = problems.filter(problem => Object.keys(problem)[0] === categoryName);
                                                        setProblemList({
                                                            ...problemList,
                                                            list: filteredProblems[0][categoryName],
                                                        });
                                                        setProblemListDisabled(false);
                                                        setCategoryId(parseInt(value.selectedList[0]._id));
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
                                            {Object.keys(predictedProblemCategory).length > 0 && predictedProblemCategory.isDetected &&
                                                <Text variant="error">
                                                    {CATEGORY_PREDICTION_HELPER_TEXT}
                                                </Text>
                                            }
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
                                                        bottomOffset: 30
                                                    });
                                                }
                                            }}>
                                                <View>
                                                    <PaperSelect
                                                        disabled={isProblemListDisabled}
                                                        label={PROBLEM_LABEL}
                                                        value={problem}
                                                        onSelection={(value) => {
                                                            dispatch(setProblem(value.text));
                                                            setProblemList({
                                                                ...problemList,
                                                                value: value.text,
                                                                selectedList: value.selectedList,
                                                                error: '',
                                                            });
                                                            setProblemId(parseInt(value.selectedList[0]._id));
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
                                            <Spacer position="top" size="medium" />
                                            <TextInput
                                                contentStyle={{height: 120}}
                                                mode={"outlined"}
                                                outlineColor={colors.brand.primary}
                                                activeOutlineColor={colors.brand.primary}
                                                label={DESCRIPTION_LABEL}
                                                value={problemDescription}
                                                onChangeText={text => {
                                                    dispatch(setProblemDescription(text));
                                                    setDescriptionError(!(text && text.length > 0));
                                                }}
                                                numberOfLines={5}
                                                error={descriptionError}
                                            />
                                            <Spacer position="top" size="medium" />
                                            <Spacer position="top" size="medium" />
                                            <SubmitButtonContainer>
                                                <ContinueButton
                                                    mode="contained"
                                                    icon="arrow-right-thick"
                                                    onPress={() => {
                                                        const categoryError = selectValidator(categoryList.value, CATEGORY_ERROR_MESSAGE);
                                                        const problemError = selectValidator(problemList.value, PROBLEM_ERROR_MESSAGE);
                                                        setCategoryList({ ...categoryList, error: categoryError });
                                                        setProblemList({ ...problemList, error: problemError });
                                                        setDescriptionError(!(problemDescription && problemDescription.length > 0));
                                                        if (categoryError || problemError || (!(problemDescription && problemDescription.length > 0))) {
                                                            Toast.show({
                                                                type: 'error',
                                                                text1: SUBMIT_TOAST_TEXT1_MESSAGE,
                                                                text2: SUBMIT_TOAST_TEXT2_MESSAGE,
                                                                position: 'top',
                                                                visibilityTime: 3000,
                                                                autoHide: true,
                                                                topOffset: 30
                                                            });
                                                            return;
                                                        }
                                                        Application.getIosIdForVendorAsync()
                                                            .then(deviceId => {
                                                                const reportedProblem = {
                                                                    category: categoryId,
                                                                    problem: problemId,
                                                                    description: problemDescription,
                                                                    date: new Date().toISOString().replace(/.\d+Z$/g, "Z"),
                                                                    deviceId: deviceId,
                                                                    imageBase64: photo.base64
                                                                };
                                                                navigation.navigate("MapScreen", {
                                                                    reportedProblem: reportedProblem
                                                                });
                                                            });
                                                    }
                                                    }
                                                >
                                                    <Text variant="lightLabel">
                                                        {REPORT_PROBLEM_SUBMIT_LABEL}
                                                    </Text>
                                                </ContinueButton>
                                            </SubmitButtonContainer>
                                            <Spacer position="top" size="large" />
                                            <Spacer position="top" size="large" />
                                        </View>
                                    </KeyboardAwareScrollView>
                            </SafeArea>
                        </TouchableWithoutFeedback>
                )
            }
            <Toast config={toastConfig} />
        </Background>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 0.6,
    }
});