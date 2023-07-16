import ReportedProblemComponent from "./components/reported-problem.component";
import {useEffect} from "react";

export const ReportedProblemContainer = (props) => {

    const {
        actions:
            {
                updateReportedProblemStatus,
                getProblemsCategories,
                getProblemsStatuses,
                banUser,
            },
        problemUpdateLoading,
        problemUpdateFailed,
        categoriesLoading,
        categoriesFailure,
        categoriesError,
        statusesError,
        statusesLoading,
        statusesFailure,
        categories,
        statuses,
        user,
        user: {id, token},
        banSuccess,
        banLoading,
        banError,
    } = props;


    useEffect(() => {
        getProblemsCategories();
        getProblemsStatuses();
    }, [getProblemsCategories, getProblemsStatuses])

    const updateStatusReportedProblem = (reportedProblemId, problemStatusId) => {
        updateReportedProblemStatus && updateReportedProblemStatus(token, reportedProblemId, problemStatusId, id);

    }

    const banUserByDeviceId = (deviceId) => {
      banUser && banUser(token, deviceId);
      console.log('here');
    }

    return(
        <ReportedProblemComponent
            updateReportedProblemStatus={updateStatusReportedProblem}
            categories={categories}
            categoriesLoading={categoriesLoading}
            categoriesFailure={categoriesFailure}
            categoriesError={categoriesError}
            statusesError={statusesError}
            statusesLoading={statusesLoading}
            statusesFailure={statusesFailure}
            problemStatuses={statuses}
            problemUpdateLoading={problemUpdateLoading}
            problemUpdateFailed={problemUpdateFailed}
            user={user}
            banUser={banUserByDeviceId}
            banSuccess={banSuccess}
            banLoading={banLoading}
            banError={banError}
        />
    );
}


