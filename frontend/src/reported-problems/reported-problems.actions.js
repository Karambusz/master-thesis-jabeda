import {
    svcBanUser,
    svcGetProblemsCategories, svcGetProblemsStatuses,
    svcGetSubscriberReportedProblemsHistory,
    svcUpdateReportedProblemStatus
} from '../services/reported-problem.service';

export const UPDATE_REPORTED_PROBLEM_STATUS_BEGIN = 'UPDATE_REPORTED_PROBLEM_STATUS_BEGIN';
export const UPDATE_REPORTED_PROBLEM_STATUS_SUCCESS = 'UPDATE_REPORTED_PROBLEM_STATUS_SUCCESS';
export const UPDATE_REPORTED_PROBLEM_STATUS_FAILURE = 'UPDATE_REPORTED_PROBLEM_STATUS_FAILURE';
export const GET_SUBSCRIBER_PROBLEMS_HISTORY_BEGIN = 'GET_SUBSCRIBER_PROBLEMS_HISTORY_BEGIN';
export const GET_SUBSCRIBER_PROBLEMS_HISTORY_SUCCESS = 'GET_SUBSCRIBER_PROBLEMS_HISTORY_SUCCESS';
export const GET_SUBSCRIBER_PROBLEMS_HISTORY_FAILURE = 'GET_SUBSCRIBER_PROBLEMS_HISTORY_FAILURE';
export const GET_PROBLEMS_CATEGORIES_BEGIN = 'GET_PROBLEMS_CATEGORIES_BEGIN';
export const GET_PROBLEMS_CATEGORIES_SUCCESS = 'GET_PROBLEMS_CATEGORIES_SUCCESS';
export const GET_PROBLEMS_CATEGORIES_FAILURE = 'GET_PROBLEMS_CATEGORIES_FAILURE';
export const GET_PROBLEMS_STATUSES_BEGIN = 'GET_PROBLEMS_STATUSES_BEGIN';
export const GET_PROBLEMS_STATUSES_SUCCESS = 'GET_PROBLEMS_STATUSES_SUCCESS';
export const GET_PROBLEMS_STATUSES_FAILURE = 'GET_PROBLEMS_STATUSES_FAILURE';
export const BAN_USER_BEGIN = 'BAN_USER_BEGIN';
export const BAN_USER_SUCCESS = 'BAN_USER_SUCCESS';
export const BAN_USER_FAILURE = 'BAN_USER_FAILURE';


export const updateReportedProblemStatus = (token, reportedProblemId, problemStatusId, subscriberId) => {
    return (dispatch) => {
        dispatch({
            type: UPDATE_REPORTED_PROBLEM_STATUS_BEGIN,
        });
        try {
            return svcUpdateReportedProblemStatus(token, reportedProblemId, problemStatusId, subscriberId)
                .then((response) => {
                    if (response.status !== 200) {
                        return dispatch({
                            type: UPDATE_REPORTED_PROBLEM_STATUS_FAILURE
                        });
                    }
                    return dispatch({
                        type: UPDATE_REPORTED_PROBLEM_STATUS_SUCCESS,
                        reportedProblem: response.data,
                    });
                })
                .catch((error) => {
                    return dispatch({
                        type: UPDATE_REPORTED_PROBLEM_STATUS_FAILURE,
                        error: error,
                    });
                });
        } catch (e) {
            return dispatch({
                type: UPDATE_REPORTED_PROBLEM_STATUS_FAILURE,
                error: e,
            });
        }
    };
}


export const getSubscriberReportedProblemsHistory = (token, subscriberId) => {
    return (dispatch) => {
        dispatch({
            type: GET_SUBSCRIBER_PROBLEMS_HISTORY_BEGIN,
        });
        try {
            return svcGetSubscriberReportedProblemsHistory(token, subscriberId)
                .then((response) => {
                    if (response.status !== 200) {
                        return dispatch({
                            type: GET_SUBSCRIBER_PROBLEMS_HISTORY_FAILURE
                        });
                    }
                    return dispatch({
                        type: GET_SUBSCRIBER_PROBLEMS_HISTORY_SUCCESS,
                        problemsHistory: response.data,
                    });
                })
                .catch((error) => {
                    return dispatch({
                        type: GET_SUBSCRIBER_PROBLEMS_HISTORY_FAILURE,
                        error: error,
                    });
                });
        } catch (e) {
            return dispatch({
                type: GET_SUBSCRIBER_PROBLEMS_HISTORY_FAILURE,
                error: e,
            });
        }
    };
}


export const getProblemsCategories = () => {
    return (dispatch) => {
        dispatch({
            type: GET_PROBLEMS_CATEGORIES_BEGIN,
        });
        try {
            return svcGetProblemsCategories()
                .then((response) => {
                    return dispatch({
                        type: GET_PROBLEMS_CATEGORIES_SUCCESS,
                        categories: response,
                    });

                })
                .catch((error) => {
                    return dispatch({
                        type: GET_PROBLEMS_CATEGORIES_FAILURE,
                        error: error,
                    });
                });
        } catch (e) {
            return dispatch({
                type: GET_PROBLEMS_CATEGORIES_FAILURE,
                error: e,
            });
        }
    };
}

export const getProblemsStatuses = () => {
    return (dispatch) => {
        dispatch({
            type: GET_PROBLEMS_STATUSES_BEGIN,
        });
        try {
            return svcGetProblemsStatuses()
                .then((response) => {
                    return dispatch({
                        type: GET_PROBLEMS_STATUSES_SUCCESS,
                        statuses: response,
                    });

                })
                .catch((error) => {
                    return dispatch({
                        type: GET_PROBLEMS_STATUSES_FAILURE,
                        error: error,
                    });
                });
        } catch (e) {
            return dispatch({
                type: GET_PROBLEMS_STATUSES_FAILURE,
                error: e,
            });
        }
    };
}

export const banUser = (token, deviceId) => {
    return (dispatch) => {
        dispatch({
            type: BAN_USER_BEGIN,
        });
        try {
            return svcBanUser(token, deviceId)
                .then(() => {
                    return dispatch({
                        type: BAN_USER_SUCCESS,
                    });
                })
                .catch((error) => {
                    return dispatch({
                        type: BAN_USER_FAILURE,
                        error: error,
                    });
                });
        } catch (e) {
            return dispatch({
                type: BAN_USER_FAILURE,
                error: e,
            });
        }
    };
}
