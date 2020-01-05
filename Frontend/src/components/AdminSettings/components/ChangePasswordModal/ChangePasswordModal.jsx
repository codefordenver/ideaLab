import React, { useState } from 'react';

import './ChangePasswordModal.css';

import RequestService from '../../../../util/RequestService';
import Loader from '../../../globalStyles/Loader';

const ChangePasswordModal = props => {
  const [confirmNewPassword, setConfirmNewPassword] = useState();
  const [error, setError] = useState();
  const [fail, setFail] = useState(false);
  const [loading, setLoading] = useState(false);
  const [newPassword, setNewPassword] = useState();
  const [response, setResponse] = useState(false);
  const [success, setSuccess] = useState(false);

  const onCancel = e => {
    e.target.parentElement.parentElement.elements.newPassword.value = '';
    e.target.parentElement.parentElement.elements.confirmNewPassword.value = '';
    props.triggerPasswordChange();
  };

  const onSubmit = e => {
    e.preventDefault();
    setLoading(true);
    const payload = {
      username: props.username,
      newPassword: newPassword,
      confirmNewPassword: confirmNewPassword,
    };

    if (newPassword === confirmNewPassword) {
      return RequestService.changePassword(payload, onSuccess, onFailure);
    } else {
      setError(
        'The new password and confirm new password do not match.  Please re-enter these values.',
      );
      e.target.elements.newPassword.value = '';
      e.target.elements.confirmNewPassword.value = '';
    }
    resetError();
    setLoading(false);
  };

  const formBody = (
    <form className="form" onSubmit={onSubmit}>
      <div className="form__item">
        <label htmlFor="newPassword">New Password:</label>
        <input
          autoComplete="off"
          name="newPassword"
          onChange={e => setNewPassword(e.target.value)}
          required
          type="password"
        />
      </div>
      <div className="form__item">
        <label htmlFor="confirmNewPassword">Confirm New Password:</label>
        <input
          autoComplete="off"
          name="confirmNewPassword"
          onChange={e => setConfirmNewPassword(e.target.value)}
          required
          type="password"
        />
      </div>
      <br />
      <div className="form__action">
        <button type="submit">Submit</button>
        <button onClick={onCancel}>Cancel</button>
      </div>
      <br />
    </form>
  );

  const onAccept = () => {
    setResponse(false);
    setSuccess(false);
    props.triggerPasswordChange();
  };

  const successBody = (
    <div className="success">
      <h6>Password changed succesfully</h6>
      <br />
      <button onClick={onAccept}>Ok</button>
    </div>
  );

  const onAcceptErr = () => {
    setResponse(false);
    setFail(false);
  };

  const failBody = (
    <div className="fail">
      <h6>An error has occurred. Please try again.</h6>
      <br />
      <button onClick={onAcceptErr}>Ok</button>
    </div>
  );

  const loadingBody = (
    <div className="loading">
      <Loader />
    </div>
  );

  const onSuccess = response => {
    setResponse(true);
    setSuccess(true);
    setLoading(false);
  };

  const onFailure = error => {
    setResponse(true);
    setFail(true);
    setLoading(false);
  };

  let errorMessage;
  error
    ? (errorMessage = <span className="password-err">{error}</span>)
    : (errorMessage = null);

  const resetError = () => {
    const wait = ms => new Promise(resolve => setTimeout(resolve, ms));
    wait(8000).then(() => {
      setError('');
    });
  };

  let divBody;
  if (loading) {
    divBody = loadingBody;
  } else if (!response) {
    divBody = formBody;
  } else if (response && success) {
    divBody = successBody;
  } else if (response && fail) {
    divBody = failBody;
  }

  return (
    <div
      className="form__container"
      style={{
        opacity: props.passwordChange ? '1' : '0',
        transform: props.passwordChange
          ? 'translateY(0)'
          : 'translateY(-100vh)',
      }}
    >
      <h4>Change Password</h4>
      <br />
      {divBody}
      {errorMessage}
    </div>
  );
};

export default ChangePasswordModal;
