import React, { useState } from 'react';

import './ChangePasswordModal.css';

import RequestService from '../../../../util/RequestService';
import { loginUsername, loginPassword } from '../../../Login/LoginManager';

const ChangePasswordModal = props => {
  // const [body, setBody] = useState(formBody)
  const [confirmNewPassword, setConfirmNewPassword] = useState();
  const [error, setError] = useState();
  const [newPassword, setNewPassword] = useState();
  const [oldPassword, setOldPassword] = useState();
  const [username, setUsername] = useState();

  const resetError = () => {
    const wait = ms => new Promise(resolve => setTimeout(resolve, ms));
      wait(8000).then(() => {
        setError('');
      });
  }

  const cancel = e => {
    e.target.parentElement.parentElement.elements.username.value = '';
    e.target.parentElement.parentElement.elements.oldPassword.value = '';
    e.target.parentElement.parentElement.elements.newPassword.value = '';
    e.target.parentElement.parentElement.elements.confirmNewPassword.value = '';
    props.triggerPasswordChange();
  }

  const onSuccess = response => {
    // Add message confirming the password change.  Must have a btn that calls triggerPasswordChange after resetting the input values like in cancel();
    successMessage = (
      <div className='success'>

      </div>
    )
    props.triggerPasswordChange();
  };

  const onFailure = error => {
    console.log('error: ', error);
  }

  const onSubmit = e => {
    console.log(loginUsername, loginPassword)
    e.preventDefault();
    const payload = {
      username: username,
      oldPassword: oldPassword,
      newPassword: newPassword,
      confirmNewPassword: confirmNewPassword
    }

    if (
      username === loginUsername &&
      oldPassword === loginPassword &&
      newPassword === confirmNewPassword &&
      oldPassword !== newPassword
    ) {
      return RequestService.changePassword(payload, onSuccess, onFailure)
    } else if (username !== loginUsername) {
      setError(
        'The username you entered is incorrect.  Please re-enter the username.'
      )
      e.target.elements.username.value = '';
    } else if (oldPassword !== loginPassword) {
      setError(
        'The old password you entered in incorrect.  Please re-enter the old password.'
      )
      e.target.elements.oldPassword.value = '';
    } else if (oldPassword === newPassword) {
      setError(
        'The new password is the same as the old password.  Please enter different new password.'
      )
      e.target.elements.oldPassword = '';
    } else if (newPassword !== confirmNewPassword) {
      setError(
        '"New Password" and "Confirm New Password" do not match.  Please re-enter these values.'
      );
      e.target.elements.newPassword.value = '';
      e.target.elements.confirmNewPassword.value = '';
      resetError();
    }
    resetError();
  };

  const formBody = (
    <form 
      className="form"
      onSubmit={onSubmit}
    >
      <div className="form__item">
        <label htmlFor="username">Username:</label>
        <input
          autoComplete="off"
          name="username"
          onChange={e => setUsername(e.target.value)}
          placeholder="username"
          required
        />
      </div>
      <div className="form__item">
        <label htmlFor="oldPassword">Old Password:</label>
        <input
          autoComplete="off"
          name="oldPassword"
          onChange={e => setOldPassword(e.target.value)}
          placeholder="old password"
          required
          type="password"
        />
      </div>
      <div className="form__item">
        <label htmlFor="newPassword">New Password:</label>
        <input
          autoComplete="off"
          name="newPassword"
          onChange={e => setNewPassword(e.target.value)}
          placeholder="new password"
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
          placeholder="new password"
          required
          type="password"
        />
      </div>
      <br/>
      <div className="form__action">
        <button onClick={cancel}>Cancel</button>
        <button type="submit">Submit</button>
      </div>
      <br/>
    </form>
  )

  // let changePasswordBody;
  let successMessage;
  let errorMessage;

  if (error) {
    errorMessage = (<span>{error}</span>)
  }

  return (
    <div 
      className="form__container"
      style={{
        opacity: props.passwordChange ? '1' : '0',
        transform: props.passwordChange ? 'translateY(0)' : 'translateY(-100vh)' 
      }}
    >
      <h4>Change Password</h4>
      <br/>
      {formBody}
      {successMessage}
      {errorMessage}
    </div>
  )
}

export default ChangePasswordModal;