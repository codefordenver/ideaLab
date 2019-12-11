import React, { useState } from 'react';

import './ChangePasswordModal.css';

import RequestService from '../../../../util/RequestService';

const ChangePasswordModal = props => {
  const [username, setUsername] = useState();
  const [oldPassword, setOldPassword] = useState();
  const [newPassword, setNewPassword] = useState();
  const [confirmNewPassword, setConfirmNewPassword] = useState();


  const cancel = e => {
    e.target.parentElement.parentElement.elements.username.value = '';
    e.target.parentElement.parentElement.elements.oldPassword.value = '';
    e.target.parentElement.parentElement.elements.newPassword.value = '';
    e.target.parentElement.parentElement.elements.confirmNewPassword.value = '';
    props.triggerPasswordChange();
  }

  const onSuccess = response => {
    console.log(response);
  };

  const onFailure = error => {
    console.log('error: ', error);
  }

  const onSubmit = e => {
    e.preventDefault();
    const payload = {
      username: username,
      oldPassword: oldPassword,
      newPassword: newPassword,
      confirmNewPassword: confirmNewPassword
    }
    console.log(payload);
    RequestService.changePassword(payload, onSuccess, onFailure)
  };

  return (
    <form 
      onSubmit={onSubmit}
      className="form"
      style={{
        opacity: props.passwordChange ? '1' : '0',
        transform: props.passwordChange ? 'translateY(0)' : 'translateY(-100vh)' 
      }}>
      <h4>Change Password</h4>
      <br/>
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
    </form>
  )
}

export default ChangePasswordModal;