import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {withRouter} from "../util/with-router";
import ProblemsHistoryContainer from "./problems-history.container";
import {
    getProblemsCategories, getProblemsStatuses,
    getSubscriberReportedProblemsHistory
} from "../reported-problems/reported-problems.actions";
import {get} from "lodash";

const mapStateToProps = (state) => {
    const {
        historyLoading,
        historyFailed,
        problemsHistory,
        historyError,
    } = get(state, 'problemsHistory');

    const {
        categories,
        categoriesLoading,
        categoriesFailure,
        categoriesError,
        statuses,
        statusesError,
        statusesLoading,
        statusesFailure,
    } = get(state, 'reportedProblems');

    const {
        user
    } = get(state, 'userData');

    return {
        historyLoading,
        historyFailed,
        problemsHistory,
        historyError,
        categories,
        categoriesLoading,
        categoriesFailure,
        categoriesError,
        statuses,
        statusesError,
        statusesLoading,
        statusesFailure,
        user,
    };
};

const mapDispatchToProps = (dispatch) => {
    const actions = bindActionCreators(
        {
            getSubscriberReportedProblemsHistory,
            getProblemsCategories,
            getProblemsStatuses
        },
        dispatch
    );
    return { actions };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(ProblemsHistoryContainer));