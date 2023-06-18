import { CATEGORY_PREDICT_SERVER_URL } from "@env";

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

export const sendPhotoToPredict = photoBase64 => dispatch => {
    const data = {
        image: photoBase64
    }
    dispatch(setProblemPredictLoading(true));
    fetch(CATEGORY_PREDICT_SERVER_URL + "/predict", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(json => {
            console.log(json);
            dispatch(setPredictedProblemCategory(json));
            dispatch(setProblemPredictLoading(false));
        })
        .catch(err => {
            console.log(err);
            dispatch(setPredictedProblemCategory({}));
            dispatch(setProblemPredictLoading(false));
        });
}

export const getCategoriesAndProblems = () => dispatch => {
    fetch("http://192.168.1.3:8085/problems", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(res => res.json())
        .then(data => {
            const categories = data.map(categoryObj => {
                return { _id: categoryObj.idCategory, value: categoryObj.categoryName }
            });
            const problems = data.map(categoryObj => {
                const mappedProblems = categoryObj.problems.map(problem => {
                    return { _id: problem.idProblem, value: problem.problemName }
                })
                return { [categoryObj.categoryName]: mappedProblems }
            });
            dispatch(setCategories(categories));
            dispatch(setProblems(problems));
        })
        .catch(err => console.log(err));
}

export const setProblemPhoto = photo => ({
    type: SET_PROBLEM_PHOTO,
    photo
});
export const setPredictedProblemCategory = predictedProblemCategory => ({
    type: SET_PREDICTED_PROBLEM_CATEGORY,
    predictedProblemCategory
})

export const setProblemPredictLoading = isProblemPredictLoading => ({
    type: SET_PROBLEM_PREDICT_LOADING,
    isProblemPredictLoading
})

export const setCategories = categories => ({
    type: SET_CATEGORIES,
    categories
})

export const setProblems = problems => ({
    type: SET_PROBLEMS,
    problems
})

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