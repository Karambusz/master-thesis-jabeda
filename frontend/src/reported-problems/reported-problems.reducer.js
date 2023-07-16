import {createReducer} from "../util/create-reducer";
import {
    BAN_USER_BEGIN, BAN_USER_FAILURE, BAN_USER_SUCCESS,
    GET_PROBLEMS_CATEGORIES_BEGIN,
    GET_PROBLEMS_CATEGORIES_FAILURE,
    GET_PROBLEMS_CATEGORIES_SUCCESS,
    GET_PROBLEMS_STATUSES_BEGIN,
    GET_PROBLEMS_STATUSES_FAILURE,
    GET_PROBLEMS_STATUSES_SUCCESS,
    UPDATE_REPORTED_PROBLEM_STATUS_BEGIN,
    UPDATE_REPORTED_PROBLEM_STATUS_FAILURE,
    UPDATE_REPORTED_PROBLEM_STATUS_SUCCESS
} from "./reported-problems.actions";
import _merge from 'lodash/merge'

export const initialState = {
    categories: [],
    categoriesError: null,
    categoriesLoading: false,
    categoriesFailure: false,
    statuses: [],
    statusesError: null,
    statusesLoading: false,
    statusesFailure: false,
    problemUpdateLoading: false,
    problemUpdateFailed: false,
    reportedProblem: null,
    reportedProblemError: null,
    banSuccess: false,
    banLoading: false,
    banError: null,
};

export const reducers = {
    [UPDATE_REPORTED_PROBLEM_STATUS_BEGIN](state) {
        const update = {
            problemUpdateLoading: true,
        };
        return _merge({}, state, update);
    },
    [UPDATE_REPORTED_PROBLEM_STATUS_FAILURE](state, action) {
        const update = {
            problemUpdateLoading: false,
            problemUpdateFailed: true,
            reportedProblemError: action.error,
        };
        return _merge({}, state, update);
    },
    [UPDATE_REPORTED_PROBLEM_STATUS_SUCCESS](state, action) {
        const update = {
            problemUpdateLoading: false,
            reportedProblem: action.reportedProblem,
        };
        return _merge({}, state, update);
    },
    [GET_PROBLEMS_CATEGORIES_BEGIN](state) {
        const update = {
            categoriesLoading: true,
        };
        return _merge({}, state, update);
    },
    [GET_PROBLEMS_CATEGORIES_SUCCESS](state, action) {
        const update = {
            categoriesLoading: false,
            categories: action.categories,
        };
        return _merge({}, state, update);
    },
    [GET_PROBLEMS_CATEGORIES_FAILURE](state, action) {
        const update = {
            categoriesLoading: false,
            categoriesFailure: true,
            categoriesError: action.error,
        };
        return _merge({}, state, update);
    },
    [GET_PROBLEMS_STATUSES_BEGIN](state) {
        const update = {
            statusesLoading: true,
        };
        return _merge({}, state, update);
    },
    [GET_PROBLEMS_STATUSES_SUCCESS](state, action) {
        const update = {
            statusesLoading: false,
            statuses: action.statuses,
        };
        return _merge({}, state, update);
    },
    [GET_PROBLEMS_STATUSES_FAILURE](state, action) {
        const update = {
            statusesLoading: false,
            statusesFailure: true,
            statusesError: action.error,
        };
        return _merge({}, state, update);
    },
    [BAN_USER_BEGIN](state) {
        const update = {
            banLoading: true
        };
        return _merge({}, state, update);
    },
    [BAN_USER_SUCCESS](state) {
        const update = {
            banLoading: false,
            banSuccess: true,
        };
        return _merge({}, state, update);
    },
    [BAN_USER_FAILURE](state, action) {
        const update = {
            banLoading: false,
            banError: action.error
        };
        return _merge({}, state, update);
    }
}

export default createReducer(initialState, reducers);
