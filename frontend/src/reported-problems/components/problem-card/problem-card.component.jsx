import React, {useEffect, useState} from 'react';
import "./problem-card.component.scss";
import {
    Box,
    Card,
    CardActions,
    CardContent,
    CardMedia,
    Modal,
    Typography
} from "@mui/material";
import Select from "react-select";
import CustomButton from "../../../common/button/custom-button";

const ProblemCardComponent = ({ data, open, problemStatuses, onClose, updateStatus, banUser, isHistory }) => {

    const [status, setStatus] = useState();
    const [statusOptions, setStatusOptions] = useState();

    const handleStatusChange = (s) => {
        setStatus(s);
    };

    const updateProblemStatus = () => {
        if (status && status.value !== data.problemStatusCode) {
            const {idProblemStatus} = problemStatuses && problemStatuses.find(s => s.statusCode === status.value);
            updateStatus && updateStatus(data.idReportedProblem, idProblemStatus);
        }
        onClose();
    }

    useEffect(() => {
        const statusOptions = problemStatuses.map(({statusName, statusCode}) =>  {return {value: statusCode, label: statusName};})
        setStatusOptions(statusOptions);
        setStatus(statusOptions.find( s => s.value === data.problemStatusCode))
    }, [problemStatuses]);

    return (
        <Modal open={open} onClose={onClose} sx={{ overflow: 'auto' }} >
            <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh'}}>
                <Card sx={{width: '600px'}}>
                    <div className="image-holder">
                        <CardMedia component="img" height="400" image={data.imageUrl} alt="Problem" />
                    </div>
                    <CardContent>
                        <Typography
                            gutterBottom
                            variant="h6"
                            textTransform="uppercase"
                            component="div"
                            color="#222b3b"
                        >
                            {data.problem}
                        </Typography>
                        <Typography variant="body2" color="#222b3b">
                            <strong>Kategoria:</strong> {data.category}
                        </Typography>
                        <Typography variant="body2" color="#222b3b">
                            <strong>Data:</strong> {data.date}
                        </Typography>
                        <Typography variant="body2" color="#222b3b">
                            <strong>Lokacja:</strong> {data.address}
                        </Typography>
                        <Typography variant="body2" color="#222b3b">
                            <strong>Ilość odrzuconych zgłoszeń urzytkownika:</strong> {data.rejectedProblemsCount}
                        </Typography>
                        <br/>
                        <Typography variant="body2" color="#222b3b">
                            <strong>Opis:</strong>  {data.description}
                        </Typography>
                        <br/>
                        {!isHistory
                            ? <div>
                                <Typography variant="body2" color="#222b3b">
                                    <strong>Status:</strong>
                                </Typography>
                                <Select
                                    className="basic-single"
                                    classNamePrefix="select"
                                    defaultValue={status}
                                    placeholder={"Status"}
                                    onChange={handleStatusChange}
                                    clearIndicator={false}
                                    dropdownIndicator={false}
                                    options={statusOptions}
                                    styles={{ menuPortal: base => ({ ...base, zIndex: 9999 }) }}
                                    menuPortalTarget={document.body}
                                />
                            </div>
                            :
                            <Typography variant="body2" color="#222b3b">
                                <strong>Status:</strong> {status && status.label}
                            </Typography>
                        }
                    </CardContent>
                    <CardActions style={{justifyContent: 'center'}}>
                        {!isHistory && <CustomButton  additionalClass="card" size="small" onClick={updateProblemStatus}>Zapisz</CustomButton>}
                        {!isHistory && <CustomButton  additionalClass="card" size="small" onClick={() => banUser(data.userDeviceId)}>Ban</CustomButton>}
                        <CustomButton additionalClass="card" size="small" onClick={onClose}>Zamknij</CustomButton>
                    </CardActions>

                </Card>
            </Box>
        </Modal>
    );
};

export default ProblemCardComponent;
