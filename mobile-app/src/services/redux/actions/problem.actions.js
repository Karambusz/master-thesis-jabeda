import { CATEGORY_PREDICT_SERVER_URL, MAIN_SERVER_URL } from "@env";

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
    SET_CATEGORIES,
    SET_PROBLEM_REPORTED_LOADING, SET_REPORTED_PROBLEM_HISTORY_LOADING, SET_REPORTED_PROBLEM_HISTORY
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
    fetch(MAIN_SERVER_URL + "/problems", {
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

export const reportProblem = reportedProblem => dispatch => {
    dispatch(setProblemReportedLoading(true));
    fetch(MAIN_SERVER_URL + "/reported-problems", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(reportedProblem)
    })
        .then(res => res.json())
        .then(json => {
            dispatch(setProblemReportedLoading(false));
        })
        .catch(err => {
            console.log(err);
            dispatch(setProblemReportedLoading(false, true));
        });
}

export const getReportedProblemHistory = deviceId => dispatch => {
    dispatch(setReportedProblemHistoryLoading(true));
    fetch(MAIN_SERVER_URL + "/reported-problems/user-history?userDeviceId=" + deviceId, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(res => res.json())
        .then(json => {
            dispatch(setReportedProblemHistory(json));
            dispatch(setReportedProblemHistoryLoading(false));
        })
        .catch(err => {
            console.log(err);
            dispatch(setReportedProblemHistory([]));
            dispatch(setReportedProblemHistoryLoading(false));
        });
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

export const setProblemReportedLoading = (isProblemReportedLoading, isProblemReportedError = false) => ({
    type: SET_PROBLEM_REPORTED_LOADING,
    isProblemReportedLoading,
    isProblemReportedError
})

export const setReportedProblemHistoryLoading = isReportedProblemHistoryLoading => ({
    type: SET_REPORTED_PROBLEM_HISTORY_LOADING,
    isReportedProblemHistoryLoading
})

export const setReportedProblemHistory = reportedProblemHistory => ({
    type: SET_REPORTED_PROBLEM_HISTORY,
    reportedProblemHistory
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