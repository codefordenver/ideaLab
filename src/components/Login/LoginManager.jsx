import React, { useState } from 'react';
import Dropdown from '../globalStyles/Dropdown';
import RequestService from '../../util/RequestService';
import './LoginManager.css';

import ideaLABlogo from './../../ideaLABlogo.png';

const LoginManager = () => {
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

	const thenCallback = function(response){
		const token = response.headers ? response.headers.authorization : '';
		if (token) {
			console.log(token);
		} else {
			// Something should happen
			console.error("Token missing from response headers");
		};
	}

	const onSubmit = e => {
		e.preventDefault();
		const payload = {
			username: username,
			password: password
		};

		RequestService.login(payload, thenCallback, thenCallback);
	};

	return (
		<div className='container'>
			<img src={ideaLABlogo} alt='ideaLABLogo' />
			<h4>3D Printing and Upload Queue</h4>
			<h2>Sign In</h2>
			<form onSubmit={e => onSubmit(e)}>
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
	);
};

export default LoginManager;
