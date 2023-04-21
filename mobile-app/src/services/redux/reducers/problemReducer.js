import { SET_PROBLEM_PHOTO } from "../types/problem.types";

const initialState = {
    photo: {}
}

export default (state = initialState, action) => {
    switch (action.type) {
        case SET_PROBLEM_PHOTO:
            return {
                ...state,
                photo: action.photo
            }
        default:
            return state;
    }
}