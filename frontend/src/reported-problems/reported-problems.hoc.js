import {bindActionCreators} from "redux";
import {
    banUser,
    getProblemsCategories,
    getProblemsStatuses,
    updateReportedProblemStatus
} from "./reported-problems.actions";
import {connect} from "react-redux";
import {ReportedProblemContainer} from "./reported-problem.container";
import {withRouter} from "../util/with-router";
import {get} from "lodash";

const mapStateToProps = (state) => {
    const {
        categories,
        categoriesLoading,
        categoriesFailure,
        categoriesError,
        statuses,
        statusesError,
        statusesLoading,
        statusesFailure,
        problemUpdateLoading,
        problemUpdateFailed,
        reportedProblem,
        reportedProblemError,
        banSuccess,
        banLoading,
        banError,
    } = get(state, 'reportedProblems');

    const {
        user
    } = get(state, 'userData');



    return {
        categories,
        categoriesError,
        categoriesLoading,
        categoriesFailure,
        statuses,
        statusesError,
        statusesLoading,
        statusesFailure,
        problemUpdateLoading,
        problemUpdateFailed,
        reportedProblem,
        reportedProblemError,
        user,
        banSuccess,
        banLoading,
        banError,
    };
}
const mapDispatchToProps = (dispatch) => {
    const actions = bindActionCreators(
        {
            updateReportedProblemStatus,
            getProblemsCategories,
            getProblemsStatuses,
            banUser
        },
        dispatch
    );
    return { actions };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(ReportedProblemContainer));