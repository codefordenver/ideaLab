import React, { useState } from 'react';
import RequestService from '../../util/RequestService';
import BasicInput from '../globalStyles/BasicInput';
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
  const [showingSuccess, showSuccess] = useState(false);

  function onSuccess() {
    showSuccess(true);
    setTimeout(() => showSuccess(false), 3000);
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

  const renderSuccessMessage = () => {
    const message = showingSuccess ? (
      <h3 className="successCreateAccount">Successfully Created Account!</h3>
    ) : null;
    return message;
  };

  function renderErrors() {
    const errorMessages = [
      'role',
      'firstName',
      'lastName',
      'username',
      'email',
      'password',
    ].map(field => {
      if (errors[field]) {
        return <p>{field + ' ' + errors[field]}</p>;
      } else {
        return null;
      }
    });
    return errorMessages;
  }

  return (
    <div className="create-account-container">
      <div className="create-account-col-one">
        <img
          src={ideaLABlogo}
          alt="ideaLABLogo"
          className="createAccountLogo"
        />
        <h4>3D Printing and Upload Queue</h4>
        <h2>Create an Account</h2>
        {renderErrors()}
        {renderSuccessMessage()}
      </div>
      <div>
        <form className="create-account-col-two" onSubmit={e => onSubmit(e)}>
          <BasicInput
            name="email"
            placeHolder="email"
            value={email}
            type="email"
            changeHandler={e => setEmail(e)}
          />
          <BasicInput
            name="username"
            placeHolder="username"
            value={username}
            type="text"
            changeHandler={e => setUsername(e)}
          />
          <BasicInput
            name="password"
            placeHolder="password"
            value={password}
            type="text"
            changeHandler={e => setPassword(e)}
          />
          <BasicInput
            name="firstName"
            placeHolder="first name"
            value={firstName}
            type="text"
            changeHandler={e => setFirstName(e)}
          />
          <BasicInput
            name="lastName"
            placeHolder="last name"
            value={lastName}
            type="text"
            changeHandler={e => setLastName(e)}
          />
          <div className="roleRadioButtons">
            <label>
              <input
                name="role"
                type="radio"
                autoComplete="off"
                value={'STAFF'}
                onChange={e => setRole(e.target.value)}
              />
              <span>STAFF</span>
            </label>
            <label>
              <input
                name="role"
                type="radio"
                autoComplete="off"
                value={'ADMIN'}
                onChange={e => setRole(e.target.value)}
              />
              <span>ADMIN</span>
            </label>
          </div>
          <button className="createNewAccountButton" type="submit">
            CREATE ACCOUNT
          </button>
        </form>
      </div>
    </div>
  );
};

export default CreateAccountManager;
