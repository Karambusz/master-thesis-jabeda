import './reported-problem.component.scss';
import Spinner from "../../common/spinner/Spinner";
import ProblemTable from "./problem-table/problem-table";
import RadioButtons from "../../common/radio-group/radio-group";
import {useEffect, useState} from "react";
import {getWithAuthorization} from "../../util/http";
import {calcDistance, closeModal} from "../../services/services";
import {v4 as uuidv4} from "uuid";
import Modal from "../../common/modal/modal";
import ProblemCardComponent from "./problem-card/problem-card.component";

const ReportedProblemComponent = (
    {
        updateReportedProblemStatus,
        banUser,

        problemUpdateLoading,
        problemUpdateFailed,
        categoriesLoading,
        categoriesFailure,
        categoriesError,
        statusesError,
        statusesLoading,
        statusesFailure,
        categories,
        problemStatuses,
        user,
        banSuccess,
        banLoading,
        banError,
    }
) => {

    const radioButtons = {
        buttons: [
            {
                value: 60000,
                label: "Co minutę"
            },
            {
                value: 9000000,
                label: "Co 15 minut"
            },
            {
                value: 18000000,
                label: "Co 30 minut"
            }
        ]
    }

    const [loading, setLoading] = useState(false);
    const [isModal, setIsModal] = useState(false);
    const [modalError, setModalError] = useState(false);
    const [modalContent, setModalContent] = useState({});


    const [problemRows, setProblemRows] = useState([]);
    const [requestTime, setRequestTime] = useState(60000);
    const [problemCard, setProblemCard] = useState(null);
    const [userCategories, setUserCategories] = useState([]);


    const fetchProblems = () => {
        setLoading(true);
        const query = user.categories.map(category => `category=${category}`).join('&') + `&subscriberId=${user.id}`;

        getWithAuthorization(`${process.env.REACT_APP_API_ROOT_URL}/reported-problems?${query}`, user.token)
            .then(problems => {
                if (problems.length > 0) {
                    const filteredProblems = problems
                        .map(({
                                  idReportedProblem,
                                  reportedDateTime,
                                  imageUrl,
                                  problemStatusCode,
                                  userDeviceId,
                                  description,
                                  problem: {category, problemName},
                                  reportedProblemAddress: {address, latitude, longitude},
                                  rejectedProblemsCount
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
                                problemStatus: getStatusName(problemStatuses, problemStatusCode),
                                userDeviceId,
                                rejectedProblemsCount,
                            }
                        })
                    setLoading(false);
                    setProblemRows(filteredProblems)
                } else {
                    setLoading(false);
                    setProblemRows([])
                }

            });
    }

    useEffect(() => {
        fetchProblems();
        setLoading(loading || categoriesLoading || statusesLoading);
    }, [categoriesLoading, statusesLoading])


    useEffect(() => {
        const intervalId = setInterval(() => {
            fetchProblems();
        }, requestTime);
        return () => clearInterval(intervalId); //This is important
    }, [problemRows])

    useEffect(() => {
        setUserCategories(categories.filter(category => user.categories.includes(category.categoryName)))
    }, [categories, user]);

    const handleRequestTime = (event) => {
        setRequestTime(event.target.value);
    };

    const modal = isModal ? <Modal
            modalContent = {modalContent}
            modalError={modalError}
            close={() => closeModal(setIsModal)}/> :
        null;


    const problemNames = categories.reduce((acc, category) => {
        const categoryProblems = category.problems.map((problem) => problem.problemName);
        return [...acc, ...categoryProblems];
    }, []);

    const handleAction = (rowData) => {
        setProblemCard(
            <ProblemCardComponent
                data={rowData}
                problemStatuses={problemStatuses}
                open={true}
                onClose={() => setProblemCard(null)}
                updateStatus={updateReportedProblemStatus}
                banUser={banUser}
            />);
    };


    return(
        <div className="problem-page">
            <h2 className="problem-title">Lista problemów</h2>
            {modal}

            {loading ? <Spinner/> : <ProblemTable
                problems={problemRows}
                categories={userCategories.map(category => category.categoryName)}
                problemTypes={problemNames}
                problemStatuses={problemStatuses}
                onClick={handleAction}
            />}


            {loading ? null :
                <RadioButtons
                    legend="Wyświetlenie nowych zgłoszeń:"
                    ariaLabel="request-time"
                    value={requestTime}
                    handle={handleRequestTime}
                    radioButtons={radioButtons}/> }
            {problemCard && problemCard}
        </div>
    );
};

export const getStatusName = (problemStatuses, problemStatusCode) => {
    const status = problemStatuses && problemStatuses.find(s => s.statusCode === problemStatusCode);
    return status ? status.statusName : '';
}

export default ReportedProblemComponent;