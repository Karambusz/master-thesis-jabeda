import {
    CLEAR_PROBLEM,
    SET_PROBLEM,
    SET_PROBLEM_CATEGORY,
    SET_PROBLEM_DESCRIPTION,
    SET_PROBLEM_LOCATION,
    SET_PROBLEM_PHOTO
} from "../types/problem.types";

export const setProblemPhoto = photo => ({
    type: SET_PROBLEM_PHOTO,
    photo
});

export const setProblemCategory = category => ({
    type: SET_PROBLEM_CATEGORY,
    category
});

export const setProblem = problem => ({
    type: SET_PROBLEM,
    problem
});

export const setProblemDescription = description => ({
    type: SET_PROBLEM_DESCRIPTION,
    description
});

export const setProblemLocation = location => ({
    type: SET_PROBLEM_LOCATION,
    location
});

export const clearProblem = () => ({
    type: CLEAR_PROBLEM
})