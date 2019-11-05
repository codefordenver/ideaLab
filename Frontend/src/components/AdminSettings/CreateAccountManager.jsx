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
      <div className="row">
        <div className="col">
          <img className="ideaLabLogo" src={ideaLABlogo} alt="ideaLABLogo" />
          <h1>3D Printing and Upload Queue</h1>
          <h2>Create an Account</h2>
        </div>
        <div className="col">
          <div className="mx-auto">
            <form onSubmit={e => onSubmit(e)}>
              <div className="row">
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
              </div>
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
              <input
                name="email"
                placeholder="email"
                autoComplete="off"
                autoFocus
                value={email}
                onChange={e => setEmail(e.target.value)}
              />
              {/* <input
                name="username"
                placeholder="username"
                autoComplete="off"
                value={username}
                onChange={e => setUsername(e.target.value)}
              /> */}
              <input
                name="password"
                placeholder="password"
                type="password"
                autoComplete="off"
                value={password}
                onChange={e => setPassword(e.target.value)}
              />
              <br />
              <div>
                Location:
                <div>
                  <label>
                    <input name="location" type="radio" autoComplete="off" />
                    Downtown
                  </label>
                  <label>
                    <input name="location" type="radio" autoComplete="off" />
                    Hadley
                  </label>
                  <label>
                    <input name="location" type="radio" autoComplete="off" />
                    Hampden
                  </label>
                  <label>
                    <input name="location" type="radio" autoComplete="off" />
                    Montebello
                  </label>
                  <label>
                    <input name="location" type="radio" autoComplete="off" />
                    "Corky" Lab
                  </label>
                </div>
              </div>
              {renderErrors()}
              <button
                type="submit"
                className="btn btn-success btn-lg btn-block"
              >
                Create Account
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreateAccountManager;
