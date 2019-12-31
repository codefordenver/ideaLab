import React, { useState } from 'react';
import RequestService from '../../util/RequestService';
import './CreateAccountManager.css';

import ideaLABlogo from '../globalStyles/img/ideaLabLogo.png';

const CreateAccountManager = () => {
  const [role, setRole] = useState('STAFF');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});

  function onSuccess(response) {
    console.log(response.data.message);
  }

  function onFailure(error) {
    const newErrorState = RequestService.validationErrorGetter(error);
    setErrors(newErrorState);
  }

  const onSubmit = e => {
    e.preventDefault();
    const payload = {
      email: email,
      password: password,
      firstName: firstName,
      lastName: lastName,
      role: role,
      username: username,
    };

    RequestService.signUp(payload, onSuccess, onFailure);
  };

  const renderErrors = () => {
    return null;
  };

  const renderSuccessMessage = () => {
    return null;
  };

  // function renderErrors() {
  //   const errorMessages = [
  //     'role',
  //     'firstName',
  //     'lastName',
  //     'username',
  //     'email',
  //     'password',
  //   ].map(field => {
  //     if (errors[field]) {
  //       return <p>{field + ' ' + errors[field]}</p>;
  //     } else {
  //       return null;
  //     }
  //   });
  //   return errorMessages;
  // }

  return (
    <div className="create-account-container">
      <div className="create-account-col-one">
        <img src={ideaLABlogo} alt="ideaLABLogo" />
        <h1>3D Printing and Upload Queue</h1>
        <h2>Create an Account</h2>
        {renderErrors()}
        {renderSuccessMessage()}
      </div>
      <div className="create-account-col-two">
        <form onSubmit={e => onSubmit(e)}>
          <input
            name="email"
            placeholder="email"
            autoComplete="off"
            autoFocus
            value={email}
            onChange={e => setEmail(e.target.value)}
          />
          <input
            name="username"
            placeholder="username"
            autoComplete="off"
            value={username}
            onChange={e => setUsername(e.target.value)}
          />
          <input
            name="password"
            placeholder="password"
            type="password"
            autoComplete="off"
            value={password}
            onChange={e => setPassword(e.target.value)}
          />
          <input
            name="firstName"
            placeholder="First Name"
            type="input"
            autoComplete="off"
            value={firstName}
            onChange={e => setFirstName(e.target.value)}
          />
          <input
            name="lastName"
            placeholder="Last Name"
            type="input"
            autoComplete="off"
            value={lastName}
            onChange={e => setLastName(e.target.value)}
          />
          <div>
            <label>
              <input
                name="role"
                type="radio"
                autoComplete="off"
                value={'STAFF'}
                onChange={e => setRole(e.target.value)}
              />
              Staff
            </label>
            <label>
              <input
                name="role"
                type="radio"
                autoComplete="off"
                value={'ADMIN'}
                onChange={e => setRole(e.target.value)}
              />
              Admin
            </label>
          </div>
          <button type="submit">Create Account</button>
        </form>
      </div>
    </div>
  );
};

export default CreateAccountManager;
