import {useEffect} from "react";
import ProblemsHistoryComponent from "./components/problems-history.component";

const ProblemsHistoryContainer = (
    {
        actions: {
            getSubscriberReportedProblemsHistory,
            getProblemsCategories,
            getProblemsStatuses,
        },
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
        user: {token, id},
    }
) => {

    useEffect(() => {
        getProblemsCategories();
        getProblemsStatuses();
    }, [getProblemsCategories, getProblemsStatuses])

    useEffect(() => {
        getSubscriberReportedProblemsHistory(token, id);
    }, [getSubscriberReportedProblemsHistory, token, id]);

    return (
        <div>
            <ProblemsHistoryComponent
                categories={categories}
                categoriesLoading={categoriesLoading}
                categoriesFailure={categoriesFailure}
                categoriesError={categoriesError}
                statusesError={statusesError}
                statusesLoading={statusesLoading}
                statusesFailure={statusesFailure}
                statuses={statuses}
                historyLoading={historyLoading}
                historyFailed={historyFailed}
                problemsHistory={problemsHistory}
                historyError={historyError}
                user={user}
            />
    </div>)
}

export default ProblemsHistoryContainer;