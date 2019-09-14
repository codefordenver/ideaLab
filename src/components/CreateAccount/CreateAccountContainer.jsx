import React, { useState } from 'react';
import RequestService from '../../util/RequestService'
import './CreateAccountContainer.css';

import ideaLABlogo from './../../ideaLABlogo.png';

const LoginManager = () => {
	const [role, setRole] = useState('STAFF');
	const [firstName, setFirstName] = useState('');
	const [lastName, setLastName] = useState('');
	const [username, setUsername] = useState('');
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');
	const [errors, setErrors] = useState({});

	function onSuccess(response) {
		// Maybe display the modal or some kind of redirect?
		console.log(response.data.message);
	 }

	function onFailure(error) {
		const validationErrors = RequestService.validationErrorGetter(error);
		const newErrorState = {};
		validationErrors.forEach(error => {
			newErrorState[error.field] = error.defaultMessage;
		});
		setErrors(newErrorState);
	};


	const onSubmit = e => {
		e.preventDefault();
		const payload = {
			"email": email,
			"password": password,
			"firstName": firstName,
			"lastName": lastName,
			"role": role,
			"username": username
		}

		RequestService.signUp(payload, onSuccess, onFailure);
	};

	function renderErrors(){
		const errorMessages = ['role', 'firstName', 'lastName', 'username', 'email', 'password'].map((field) => {
			if (errors[field]) {
				return <p>{field + ' ' + errors[field]}</p>
			} else {
				return null;
			}
		});
		return errorMessages;
	}

	return (
		<div className='container'>
			<div className='card'>
				<img src={ideaLABlogo} alt='ideaLABLogo' />
				<h1>3D Printing and Upload Queue</h1>
				<h2>Create an Account</h2>
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
						name='username'
						placeholder='username'
						autoComplete='off'
						value={username}
						onChange={e => setUsername(e.target.value)}
					/>
					<input
						name='password'
						placeholder='password'
						type='password'
						autoComplete='off'
						value={password}
						onChange={e => setPassword(e.target.value)}
					/>
					<input
						name='firstName'
						placeholder='First Name'
						type='input'
						autoComplete='off'
						value={firstName}
						onChange={e => setFirstName(e.target.value)}
					/>
					<input
						name='lastName'
						placeholder='Last Name'
						type='input'
						autoComplete='off'
						value={lastName}
						onChange={e => setLastName(e.target.value)}
					/>
					<div>
						<label>
							<input
								name='role'
								type='radio'
								autoComplete='off'
								value={'STAFF'}
								onChange={e => setRole(e.target.value)}
							/>
							Staff
						</label>
						<label>
							<input
								name='role'
								type='radio'
								autoComplete='off'
								value={'ADMIN'}
								onChange={e => setRole(e.target.value)}
							/>
							Admin
						</label>
					</div>
					{renderErrors()}
					<button type='submit'>Create Account</button>
				</form>
				<p>This is the email state: {email}</p>
				<p>This is the password state: {password}</p>
			</div>
		</div>
	);
};

export default LoginManager;
