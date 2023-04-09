import { createStore, combineReducers} from 'redux';
import ProblemReducer from "./reducers/problemReducer";

const rootReducer = combineReducers({
    problems: ProblemReducer,
});

export const store = createStore(rootReducer);