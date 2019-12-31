import React, { useState } from 'react';
import RequestService from '../../../util/RequestService';
import { useToasts } from 'react-toast-notifications';

const UpdateEmail = () => {
  const [newEmail, setNewEmail] = useState('');
  const [newPassword, setNewPassword] = useState('');

  const { addToast } = useToasts();

  const onFail = error => {
    console.log('Error changing email information: ' + error);
    addToast('Email information was not updated.', {
      appearance: 'warning',
      autoDismiss: true,
    });
  };

  const onSuccess = () => {
    addToast('Email information updated successfully.', {
      appearance: 'success',
      autoDismiss: true,
    });
  };

  const onSave = () => {
    const body = {
      email: newEmail,
      password: newPassword,
    };
    RequestService.updateEmailInfo(body, onSuccess, onFail);
  };

  return (
    <div className="emailSettingsContainer">
      <div className="emailSettingsInputDisplay">
        <input
          className="adminSettingsInput"
          placeholder="email"
          label="Input"
          onChange={e => {
            setNewEmail(e.target.value);
          }}
        />
        <input
          className="adminSettingsInput"
          placeholder="password"
          label="Input"
          type="password"
          onChange={e => {
            setNewPassword(e.target.value);
          }}
        />
      </div>
      <div className="adminSettingsButton emailSettingsButton" onClick={onSave}>
        Save
      </div>
      <div>
        *The application will need to be restarted in order to take effect.
      </div>
    </div>
  );
};

export default UpdateEmail;
