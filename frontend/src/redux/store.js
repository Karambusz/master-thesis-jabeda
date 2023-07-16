import {createStore, applyMiddleware} from 'redux';
import logger from 'redux-logger';
import ReduxThunk from 'redux-thunk';
import {reducers} from "./index";

const middlewares = [ReduxThunk];

if (process.env.NODE_ENV === 'development') {
    middlewares.push(logger);
}

const store = createStore(reducers, applyMiddleware(...middlewares));

export default store;