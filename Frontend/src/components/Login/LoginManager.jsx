import React, { useState } from 'react';
import BasicInput from '../BasicInput';
import RequestService from '../../util/RequestService';
import AuthContext from '../../AuthContext';
import TokenParser from '../../util/TokenParser';
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
        const decoded = TokenParser(token);
        console.log('where is it:', decoded);
        RequestService.requestState.token = token;
        localStorage.setItem('ideaLab', token);
        callbacks.setState({
          token: token,
          authenticated: true,
          role: decoded.role,
          employeeId: decoded.employeeId,
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
