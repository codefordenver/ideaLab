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

  function parseJwt(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(function(c) {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join(''),
    );
    return JSON.parse(jsonPayload);
  }

  function thenCallback(callbacks) {
    return function actualCallback(response) {
      const token = response.headers ? response.headers.authorization : '';
      if (token) {
        const decoded = parseJwt(token);
        RequestService.requestState.token = token;
        callbacks.setState({
          token: token,
          authenticated: true,
          role: decoded.role
        });
      } else {
        callbacks.setState({ authenticated: false });
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
          <div className="loginContainer">
            {context.authenticated}
            <img className="ideaLabLogo" src={ideaLABlogo} alt="ideaLABLogo" />
            <h4>3D Printing and Upload Queue</h4>
            <h2>Sign In</h2>
            <form
              onSubmit={e => {
                const callbacks = {
                  setState: context.setState,
                };
                onSubmit(e, callbacks);
              }}
            >
              <span>{errors.form ? errors.form : null}</span>
              <BasicInput
                name="username"
                placeHolder="username"
                type="text"
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
              <button type="submit" className="accountSubmitButton">
                SIGN IN
              </button>
            </form>
          </div>
        );
      }}
    </AuthContext.Consumer>
  );
};

export default LoginManager;
