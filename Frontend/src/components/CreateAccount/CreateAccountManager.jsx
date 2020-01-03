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
            changeHandler={e => setEmail(e.target.value)}
          />
          <BasicInput
            name="username"
            placeHolder="username"
            value={username}
            type="text"
            changeHandler={e => setUsername(e.target.value)}
          />
          <BasicInput
            name="password"
            placeHolder="password"
            value={password}
            type="text"
            changeHandler={e => setPassword(e.target.value)}
          />
          <BasicInput
            name="firstName"
            placeHolder="First Name"
            value={firstName}
            type="text"
            changeHandler={e => setFirstName(e.target.value)}
          />
          <BasicInput
            name="lastName"
            placeHolder="Last Name"
            value={lastName}
            type="text"
            changeHandler={e => setLastName(e.target.value)}
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
          <button className="createNewAccountButton" type="submit">
            Create Account
          </button>
        </form>
      </div>
    </div>
  );
};

export default CreateAccountManager;
