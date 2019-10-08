import React, { useState } from 'react';
import RequestService from '../../util/RequestService';
import './CreateAccountManager.css';

import ideaLABlogo from './../../ideaLABlogo.png';

const CreateAccountManager = () => {
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
    <div className="container">
      <div className="banner">
        <img src={ideaLABlogo} alt="ideaLABLogo" />
        <h1>3D Printing and Upload Queue</h1>
        <h2>Create an Account</h2>
      </div>
      <div className="form">
        <form onSubmit={e => onSubmit(e)}>
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
          <div className="select-role">
            <input
              id="staff"
              name="role"
              type="radio"
              autoComplete="off"
              value={'STAFF'}
              onChange={e => setRole(e.target.value)}
            />
            <label htmlFor="staff">Staff</label>
            <input
              id="admin"
              name="role"
              type="radio"
              autoComplete="off"
              value={'ADMIN'}
              onChange={e => setRole(e.target.value)}
            />
            <label htmlFor="admin">Admin</label>
          </div>
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
          {renderErrors()}
          <div className="select-location">
            <h3>Location</h3>
            <input id="downtown" type="radio" />
            <label htmlFor="downtown">Downtown</label>
            <input id="hampden" type="radio" />
            <label htmlFor="downtown">Downtown</label>
            <input id="corky-lab" type="radio" />
            <label htmlFor="corky-lab">"Corky" Lab</label>
            <input id="hadley" type="radio" />
            <label htmlFor="hadley">Hadley</label>
            <input id="montebello" type="radio" />
            <label htmlFor="montebello">Montebello</label>
          </div>
          <button type="submit">Create Account</button>
        </form>
      </div>
    </div>
  );
};

export default CreateAccountManager;
