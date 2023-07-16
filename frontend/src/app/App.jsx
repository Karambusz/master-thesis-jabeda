import './App.scss';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';

import HomePage from '../pages/home-page/home-page';
import SignUpPage from '../pages/sign-up-page/sign-up-page';
import SignInPage from '../pages/sign-in-page/sign-in-page';
import ProblemPage from '../pages/problem-page/problem-page';
import Profile from '../pages/profile-page/profile-page';
import EditPage from '../pages/edit-page/edit-page';
import Page404 from '../pages/page-404/page-404';
import Header from '../common/header/header';
import ProtectedRoute from "../util/protected-route";
import HistoryPage from "../pages/history-page/history-page";


function App() {

	return (
		<Router>
		<div className="App">
			<Header/>
			<div className="content-wrapper">
				<Routes>
					<Route path="/" element={<HomePage/>}/>
					<Route path='/profile' element={<ProtectedRoute element={<Profile/>} authorizationRequired/>}/>
					<Route path='/history' element={<ProtectedRoute element={<HistoryPage/>} authorizationRequired/>}/>
					<Route path='/profile/edit' element={<ProtectedRoute element={<EditPage/>} authorizationRequired/>}/>
					<Route path='/signup' element={<ProtectedRoute element={<SignUpPage/>} />} />
					<Route path='/signin' element={<ProtectedRoute element={<SignInPage/>} />} />
					<Route path='/problems' element={<ProtectedRoute element={<ProblemPage/>} authorizationRequired/>}/>
					<Route path="*" element={<Page404/>}/>
				</Routes>
			</div>
		</div>
		</Router>
	);
}	

export default App;
