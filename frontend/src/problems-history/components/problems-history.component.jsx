import {calcDistance, closeModal} from "../../services/services";
import {v4 as uuidv4} from "uuid";
import {useEffect, useState} from "react";
import Spinner from "../../common/spinner/Spinner";
import ProblemTable from "../../reported-problems/components/problem-table/problem-table";
import Modal from "../../common/modal/modal";
import ProblemCardComponent from "../../reported-problems/components/problem-card/problem-card.component";
import {getStatusName} from "../../reported-problems/components/reported-problem.component";

const ProblemsHistoryComponent = (
    {
        categories,
        categoriesLoading,
        categoriesFailure,
        categoriesError,
        statuses,
        statusesError,
        statusesLoading,
        statusesFailure,
        historyLoading,
        historyFailed,
        problemsHistory,
        historyError,
        user,
    }
) => {

    const [problemRows, setProblemRows] = useState([]);
    const [loading, setLoading] = useState(false);
    const [isModal, setIsModal] = useState(false);
    const [modalError, setModalError] = useState(false);
    const [modalContent, setModalContent] = useState({});



    const [problemCard, setProblemCard] = useState(null);
    const [userCategories, setUserCategories] = useState([]);

    useEffect(() => {
        setLoading(historyLoading);
        historyFailed && setModalError(historyError);
        if (categoriesFailure) {
            setModalError(categoriesError);
        }
        statusesFailure && setModalError(statusesError);
    },[setLoading, historyLoading, historyFailed, historyError,
        categoriesFailure, categoriesError, statusesFailure, statusesError]);


    useEffect(() => {
        setLoading(loading);
        if (problemsHistory && problemsHistory.length > 0) {
            const filteredProblems = problemsHistory
                .map(({
                          idReportedProblem,
                          reportedDateTime,
                          imageUrl,
                          problemStatusCode,
                          userDeviceId,
                          description,
                          problem: {category, problemName},
                          reportedProblemAddress: {address, latitude, longitude}
                      }) => {
                    const distance = calcDistance(user.latitude, user.longitude, latitude, longitude).toFixed(2)

                    return {
                        id: uuidv4(),
                        idReportedProblem,
                        date: reportedDateTime.split("T").join(" "),
                        address,
                        distance,
                        category,
                        problem: problemName,
                        description,
                        imageUrl,
                        problemStatusCode,
                        problemStatus: getStatusName(statuses, problemStatusCode),
                        userDeviceId,
                    }
                })
            setProblemRows(filteredProblems)
        }

    },[setProblemRows, problemsHistory, user, statuses]);



    useEffect(() => {
        setUserCategories(categories.filter(category => user.categories.includes(category.categoryName)))
    }, [categories, user]);


    const modal = isModal ? <Modal
            modalContent = {modalContent}
            modalError={modalError}
            close={() => closeModal(setIsModal)}/> :
        null;


    const problemNames = categories && categories.reduce((acc, category) => {
        const categoryProblems = category.problems.map((problem) => problem.problemName);
        return [...acc, ...categoryProblems];
    }, []);

    const handleAction = (rowData) => {
        setProblemCard(
            <ProblemCardComponent
                data={rowData}
                problemStatuses={statuses}
                open={true}
                onClose={() => setProblemCard(null)}
                isHistory={true}
            />);
    };

    return (
        <div className="problem-page">
            <h2 className="problem-title">Historia</h2>
            {modal}

            {loading ? <Spinner/> : <ProblemTable
                problems={problemRows}
                categories={userCategories.map(category => category.categoryName)}
                problemTypes={problemNames}
                problemStatuses={statuses}
                onClick={handleAction}
                isHistory
            />}

            {problemCard && problemCard}
        </div>
    );

};

export default ProblemsHistoryComponent;