import {
    CLEAR_PROBLEM,
    SET_PROBLEM,
    SET_PROBLEM_CATEGORY,
    SET_PROBLEM_DESCRIPTION,
    SET_PROBLEM_LOCATION,
    SET_PROBLEM_PHOTO,
    SET_PREDICTED_PROBLEM_CATEGORY,
    SET_PROBLEM_PREDICT_LOADING,
    SET_PROBLEMS,
    SET_CATEGORIES
} from "../types/problem.types";

const initialState = {
    photo: {},
    problemCategory: "",
    problem: "",
    problemDescription: "",
    location: {
        fullAddress: ""
    },
    predictedProblemCategory: {},
    isProblemPredictLoading: false,
    categories: [],
    problems: []
}

export default (state = initialState, action) => {
    switch (action.type) {
        case SET_PROBLEM_PHOTO:
            return {
                ...state,
                photo: action.photo
            }
        case SET_PROBLEM_CATEGORY:
            return {
                ...state,
                problemCategory: action.category
            }
        case SET_PREDICTED_PROBLEM_CATEGORY:
            return {
                ...state,
                predictedProblemCategory: action.predictedProblemCategory
            }
        case SET_PROBLEM_PREDICT_LOADING:
            return {
                ...state,
                isProblemPredictLoading: action.isProblemPredictLoading
            }
        case SET_PROBLEM:
            return {
                ...state,
                problem: action.problem
            }
        case SET_PROBLEMS:
            return {
                ...state,
                problems: action.problems
            }
        case SET_CATEGORIES:
            return {
                ...state,
                categories: action.categories
            }
        case SET_PROBLEM_DESCRIPTION:
            return {
                ...state,
                problemDescription: action.description
            }
        case SET_PROBLEM_LOCATION:
            return {
                ...state,
                location: action.location
            }
        case CLEAR_PROBLEM:
            return {
                ...state,
                photo: {},
                problemCategory: "",
                problem: "",
                problemDescription: "",
                predictedProblemCategory: {},
                isProblemPredictLoading: false,
                location: {
                    ...initialState.location
                }
            }
        default:
            return state;
    }
}