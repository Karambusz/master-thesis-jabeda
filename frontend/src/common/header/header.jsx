import './header.scss';
import {useSelector, useDispatch} from 'react-redux';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUserPlus, faSignInAlt, faSignOutAlt } from "@fortawesome/free-solid-svg-icons";
import { logout } from '../../redux/user/user.action';
import { Link } from 'react-router-dom';

const Header = () => {
    const {userData: {isLogged}} = useSelector(state => state);
    const dispatch = useDispatch();

    const signUpIcon = <FontAwesomeIcon icon={faUserPlus} />;
    const signInIcon = <FontAwesomeIcon icon={faSignInAlt} />;
    const logOutIcon = <FontAwesomeIcon icon={faSignOutAlt} />;
    

    return (
        <div className="header">
            <Link to="/">
                <img className='logo' src={process.env.PUBLIC_URL + 'logo.png'} alt="logo"/> 
            </Link>
            <div className='options'>
                <div className="option">
                    {
                        isLogged  ?  <Link  to="/history">
                                Historia
                            </Link>
                            : null
                    }
                </div>
                <div className="option">
                    {
                        isLogged  ?  <Link  to="/profile">
                                        Profil
                                    </Link>
                                  : null
                    }
                </div>
                <div className="option">
                    {
                        isLogged  ?  <Link  to="/problems">
                                        Problemy
                                    </Link>
                                  : <Link to="/signup">
                                        Rejestracja {signUpIcon}
                                    </Link>
                    }
                </div>
                <div className="option">
                    {
                        isLogged  ?  <div className="logout" onClick={() => {
                            localStorage.removeItem("user");
                            localStorage.removeItem("acceptedRows");
                            dispatch(logout());

                        }}>
                            Wyloguj się {logOutIcon}
                        </div>
                        : <Link  to="/signin">
                            Logowanie {signInIcon}
                        </Link>
                    }
                </div>
            </div>
        </div>

    )
}

export default Header;