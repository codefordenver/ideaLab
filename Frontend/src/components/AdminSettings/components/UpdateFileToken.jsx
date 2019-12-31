import React, { useState } from 'react';
import RequestService from '../../../util/RequestService';
import { useToasts } from 'react-toast-notifications';

const UpdateFileToken = () => {
  const [newToken, setNewToken] = useState('');

  const { addToast } = useToasts();

  const onFail = error => {
    console.log('Error changing file upload token: ' + error);
    addToast('File upload token was not updated.', {
      appearance: 'warning',
      autoDismiss: true,
    });
  };

  const onSuccess = () => {
    addToast('File upload token updated successfully.', {
      appearance: 'success',
      autoDismiss: true,
    });
  };

  const onSave = () => {
    const body = {
      token: newToken,
    };
    RequestService.updateDropboxTokenInfo(body, onSuccess, onFail);
  };

  return (
    <div className="emailSettingsContainer">
      <div className="emailSettingsInputDisplay">
        <input
          className="adminSettingsInput"
          placeholder="token"
          label="Input"
          onChange={e => {
            setNewToken(e.target.value);
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

export default UpdateFileToken;
