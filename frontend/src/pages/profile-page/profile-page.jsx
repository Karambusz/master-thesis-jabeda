import './profile-page.scss'
import CustomButton from '../../common/button/custom-button';
import {useSelector} from 'react-redux';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser, faPhone, faEnvelope, faAddressCard, faTasks} from "@fortawesome/free-solid-svg-icons";
import {Link} from 'react-router-dom';

const Profile = () => {

    const {
        userData: { user: {
            firstName,
            lastName,
            number,
            email,
            categories,
            city,
            street,
            buildingNumber
        }
    }} = useSelector(state => state);

    const userIcon = <FontAwesomeIcon icon={faUser}/>;
    const phomeIcon = <FontAwesomeIcon icon={faPhone}/>;
    const mailIcon = <FontAwesomeIcon icon={faEnvelope}/>;
    const addressIcon = <FontAwesomeIcon icon={faAddressCard}/>;
    const categoryIcon = <FontAwesomeIcon icon={faTasks}/>;

    return (
        <div className="profile-page">
            <h2 className="profile-title">Szczegóły użytkownika</h2>
            <ul className="user-information">
                <div className="user-info-columns">
                    <div>
                        <li className="information-item">
                            <p><strong>{userIcon} Imię Nazwisko</strong></p>
                            <p>{firstName} {lastName}</p>
                        </li>
                        <li className="information-item">
                            <p><strong>{phomeIcon} Numer telefonu</strong></p>
                            <p>{number}</p>
                        </li>
                        <li className="information-item">
                            <p><strong>{mailIcon} Email</strong></p>
                            <p>{email}</p>
                        </li>
                        <li className="information-item">
                            <p><strong>{addressIcon} Adres </strong></p>
                            <p>{`${city}, ${street}, ${buildingNumber}`}</p>
                        </li>
                    </div>
                    <div>
                        <li className="information-item">
                            <p><strong>{categoryIcon} Kategoria(e)</strong></p>
                            {categories.map(category => {
                                return <p>{category}</p>
                            })}

                        </li>
                    </div>
                </div>
                <div className="profile-page-btn">
                    <Link to='/profile/edit' >
                        <CustomButton
                            type="button"
                            additionalClass="edit">
                            Modyfikuj profil
                        </CustomButton>
                    </Link>
                </div>
            </ul>
        </div>
    )
}

export default Profile;