import {createReducer} from "../util/create-reducer";
import {
    GET_SUBSCRIBER_PROBLEMS_HISTORY_BEGIN,
    GET_SUBSCRIBER_PROBLEMS_HISTORY_FAILURE,
    GET_SUBSCRIBER_PROBLEMS_HISTORY_SUCCESS
} from "../reported-problems/reported-problems.actions";
import _merge from "lodash/merge";

export const initialState = {
    historyLoading: false,
    historyFailed: false,
    problemsHistory: null,
    historyError: null,

};

export const reducers = {
    [GET_SUBSCRIBER_PROBLEMS_HISTORY_BEGIN](state) {
        const update = {
            historyLoading: true,
        };
        return _merge({}, state, update);
    },
    [GET_SUBSCRIBER_PROBLEMS_HISTORY_SUCCESS](state, action) {
        const update = {
            historyLoading: false,
            problemsHistory: action.problemsHistory,
        };
        return _merge({}, state, update);
    },
    [GET_SUBSCRIBER_PROBLEMS_HISTORY_FAILURE](state, action) {
        const update = {
            historyLoading: false,
            historyFailed: true,
            historyError: action.error,
        };
        return _merge({}, state, update);
    },
}

export default createReducer(initialState, reducers);