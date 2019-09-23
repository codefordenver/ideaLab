import React, { useState } from 'react';
import Dropdown from '../globalStyles/Dropdown';
import RequestService from '../../util/RequestService';
import AuthContext from '../../AuthContext';
import './LoginManager.css';

import ideaLABlogo from './../../ideaLABlogo.png';

const LoginManager = (props) => {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const locations = [
		'Denver Central Library',
		'Blair-Caldwell',
		'Ford-Warren',
		'Ross-Cherry',
		'Park Hill'
	]
	const [location, setLocation] = useState(locations[0]);

	function thenCallback(callbacks) {
		return function actualCallback(response) {
			const token = response.headers ? response.headers.authorization : '';
			if (token) {
				callbacks.setToken(token);
				callbacks.setAuthenticated(true);
				RequestService.requestState.token = token;
			} else {
				// Something should happen
				console.error("Token missing from response headers");
				callbacks.setAuthenticated(false);
			};
		}
	}

	const onSubmit = (e, callbacks) => {
		e.preventDefault();

		const payload = {
			username: username,
			password: password
		};

		RequestService.login(payload, thenCallback(callbacks), thenCallback(callbacks));
	};

	return (
		<AuthContext.Consumer>
			{context => {
				return (
					<div className='container'>
						{context.authenticated}
						<img src={ideaLABlogo} alt='ideaLABLogo' />
						<h4>3D Printing and Upload Queue</h4>
						<h2>Sign In</h2>
						<form onSubmit={e => {
							const callbacks = {
								setToken: context.setToken,
								setAuthenticated: context.setAuthenticated
							};
							onSubmit(e, callbacks)
						}}>
							<input
								name='username'
								placeholder='username'
								autoComplete='off'
								autoFocus
								value={username}
								onChange={e => setUsername(e.target.value)}
							/>
							<Dropdown options={locations} optionsName={'locations'} currentValue={location} />
							<input
								name='password'
								placeholder='password'
								type='password'
								autoComplete='off'
								value={password}
								onChange={e => setPassword(e.target.value)}
							/>
							<button type='submit'>SIGN IN</button>
						</form>
					</div>
				)
			}}
		</AuthContext.Consumer>
	);
};

export default LoginManager;
