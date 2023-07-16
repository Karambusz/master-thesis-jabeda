import React, {useEffect} from "react";
import { useNavigate } from "react-router-dom";
import {useSelector} from "react-redux";
const ProtectedRoute = ({element, authorizationRequired}) => {
    const navigate = useNavigate();

    const {userData: { isLogged } } = useSelector(state => state);

    useEffect(() => {
        if ((authorizationRequired && !isLogged) || (!authorizationRequired && isLogged)) {
            return navigate('/');
        }
    }, [isLogged, authorizationRequired]);

    return (
        <React.Fragment>
            {
                authorizationRequired
                    ? (isLogged ?  element : null)
                    : (!isLogged ? element : null)
            }
        </React.Fragment>
    );
}
export default ProtectedRoute;