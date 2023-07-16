import {combineReducers} from "redux";
import reportedProblems from "../reported-problems/reported-problems.reducer";
import problemsHistory from "../problems-history/problems-history.reducer";
import userData from "./user/user.reducer";

export const reducers = combineReducers(
    {
        userData,
        reportedProblems,
        problemsHistory,
    }
)