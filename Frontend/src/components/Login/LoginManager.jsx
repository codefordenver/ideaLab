import React, { useState } from 'react';
import BasicInput from '../BasicInput';
import RequestService from '../../util/RequestService';
import AuthContext from '../../AuthContext';
import './LoginManager.css';

import ideaLABlogo from '../globalStyles/img/ideaLabLogo.png';

const LoginManager = props => {
  const [errors, setErrors] = useState({});
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  function thenCallback(callbacks) {
    return function actualCallback(response) {
      const token = response.headers ? response.headers.authorization : '';
      if (token) {
        callbacks.setToken(token);
        callbacks.setAuthenticated(true);
        RequestService.requestState.token = token;
        localStorage.setItem('ideaLab', token);
      } else {
        callbacks.setAuthenticated(false);
        setErrors({
          form: 'Unable to log in with the information provided',
        });
      }
    };
  }

  const onSubmit = (e, callbacks) => {
    e.preventDefault();

    const payload = {
      username: username,
      password: password,
    };

    RequestService.login(
      payload,
      thenCallback(callbacks),
      thenCallback(callbacks),
    );
  };

  return (
    <AuthContext.Consumer>
      {context => {
        return (
          <div className="container">
            {context.authenticated}
            <img className="ideaLabLogo" src={ideaLABlogo} alt="ideaLABLogo" />
            <h4>3D Printing and Upload Queue</h4>
            <h2>Sign In</h2>
            <form
              onSubmit={e => {
                const callbacks = {
                  setToken: context.setToken,
                  setAuthenticated: context.setAuthenticated,
                  setIsAdmin: context.setIsAdmin,
                };
                onSubmit(e, callbacks);
              }}
            >
              <span>{errors.form ? errors.form : null}</span>
              <BasicInput
                name="username"
                placeHolder="username"
                changeHandler={setUsername}
                error={errors.username}
              />
              <BasicInput
                name="password"
                placeHolder="password"
                type="password"
                changeHandler={setPassword}
                error={errors.password}
              />
              <button type="submit">SIGN IN</button>
            </form>
          </div>
        );
      }}
    </AuthContext.Consumer>
  );
};

export default LoginManager;
