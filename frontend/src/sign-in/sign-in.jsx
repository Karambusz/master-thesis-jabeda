import "./sign-in.scss";

import { useState } from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {setUser} from '../redux/user/user.action';

import CustomButton from '../common/button/custom-button';
import Spinner from '../common/spinner/Spinner';
import Modal from '../common/modal/modal';
import FormInput from '../common/form-input/form-input.jsx';
import { closeModal,
    createModalContent, setModalAndLoading } from '../services/services';
import {validateEmailField, validatePasswordField} from "../util/validation";
import {postData} from "../util/http";


const SignIn = () => {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const {userData: { user, isLogged}} = useSelector(state => state);
    const dispatch = useDispatch();

    const [errorEmail, setErrorEmail] = useState({errorState: false, messagge: " (Musi być w postaci *@*.*)"});
    const [errorPassword, setErrorPassword] = useState({errorState: false, messagge: " (Musi zawierać co najmniej 6 znaków)"});

    const [loading, setLoading] = useState(false);
    const [isModal, setIsModal] = useState(false);
    const [modalError, setModalError] = useState(false);
    const [modalContent, setModalContent] = useState({});


    const validateFields = () => {
        let valid = true;
        if (!validatePasswordField(password)) {
            setErrorPassword({...errorPassword, errorState : true});
            valid = false;
        }
        if (!validateEmailField(email)) {
            setErrorEmail({...errorEmail, errorState : true});
            valid = false;
        }
        return valid;
    }

    const handleChange = (event, setter) => {
        const {value} = event.target;
        setter(value);
    }

    const clearErrorAfterFocus = (value, setter) => {
        setter({...value, errorState: false});
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        setLoading(true);

        const data = {
            email,
            password,
        }
        
        if (validateFields()) {
            postData(`${process.env.REACT_APP_API_ROOT_URL}/auth/signin`, JSON.stringify(data))
            .then(res => {
                const {status, data} = res;
                if (status === 401) {
                    const messages = [];
                    messages.push("Brak użytkownika w bazie, proszę sprawdzic email i hasło i spróbować jeszcze raz lub zalożyć nowe konto"); 
                    setModalContent(createModalContent("Błąd", messages));

                    setModalAndLoading(true, true, false, setIsModal, setModalError, setLoading);
                } else {
                    if (!user && !isLogged) {
                        dispatch(setUser(data))
                        localStorage.setItem("user", JSON.stringify(data));
                    }
                    
                }
            })
            .catch(e => console.log(e));
        } else {
            setLoading(false);
        }
    }

    const modal = isModal ? <Modal 
    modalContent = {modalContent}
    modalError={modalError}
    close={() => closeModal(setIsModal)}/> : null

    const lastElement = loading ?                 
    <div className="spinner-wrapper">
        <Spinner/>
    </div> :
    <CustomButton 
        type="submit"
        additionalClass="submit">
            Zaloguj się
    </CustomButton>

    return (
        <div className="sign-in-wrapper">
            <h2 className="sign-in-header">Logowanie</h2>
            <form id="login-subscriber-form" method="post" onSubmit={handleSubmit}>
            <FormInput
                    handleChange={(e) => handleChange(e, setEmail)}
                    clearError={() => clearErrorAfterFocus(errorEmail , setErrorEmail)}
                    error={errorEmail}
                    name="email"
                    type="text"
                    label="E-mail"
                    value={email}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setPassword)}
                    clearError={() => clearErrorAfterFocus(errorPassword ,setErrorPassword)}
                    error={errorPassword}
                    name="password"
                    type="password"
                    label="Hasło"
                    value={password}
                    required
                    autoComplete="on"
                />
                {lastElement}
            </form>
            {modal}
        </div>
    )
}

export default SignIn;