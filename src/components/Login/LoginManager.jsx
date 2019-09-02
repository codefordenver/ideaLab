import React, { useState } from 'react';
import './LoginManager.css';

import ideaLABlogo from './../../ideaLABlogo.png';

const LoginManager = () => {
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');

	const onSubmit = e => {
		e.preventDefault();
	};

	return (
		<div className='container'>
			<img src={ideaLABlogo} alt='ideaLABLogo' />
			<h4>3D Printing and Upload Queue</h4>
			<h2>Sign In</h2>
			<form onSubmit={e => onSubmit(e)}>
				<input
					name='email'
					placeholder='email'
					autoComplete='off'
					autoFocus
					value={email}
					onChange={e => setEmail(e.target.value)}
				/>
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
