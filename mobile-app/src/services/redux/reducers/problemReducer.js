import {
    CLEAR_PROBLEM,
    SET_PROBLEM,
    SET_PROBLEM_CATEGORY,
    SET_PROBLEM_DESCRIPTION,
    SET_PROBLEM_LOCATION,
    SET_PROBLEM_PHOTO
} from "../types/problem.types";

const initialState = {
    photo: {},
    problemCategory: "",
    problem: "",
    problemDescription: "",
    location: {
        fullAddress: ""
    }
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
        case SET_PROBLEM:
            return {
                ...state,
                problem: action.problem
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
                initialState,
                location: {
                    ...initialState.location
                }
            }
        default:
            return state;
    }
}