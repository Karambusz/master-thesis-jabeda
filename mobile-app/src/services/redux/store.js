import { createStore, combineReducers, applyMiddleware  } from 'redux';
import thunk from "redux-thunk"
import ProblemReducer from "./reducers/problemReducer";

const rootReducer = combineReducers({
    problems: ProblemReducer,
});

export const store = createStore(rootReducer, applyMiddleware(thunk));