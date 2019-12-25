import React from 'react';
import './AdminSettings.css';
import ColorAvailability from './components/ColorAvailability';
import EmailMessage from './components/EmailMessage';

import { ToastProvider, useToasts } from 'react-toast-notifications';

const AdminSettings = () => {
  return (
    <div className="adminSettingsContainer">
      <div className="sectionContainer">
        <div className="adminSettingsSectionHeader">Color Availability</div>
        <div className="colorPickerContainer">
          <ColorAvailability />
        </div>
      </div>
      <div className="sectionContainer">
        <div className="adminSettingsSectionHeader">Email Messages</div>
        <div className="emailMessageContainer">
          <ToastProvider>
            <EmailMessage status="FAILED" />
          </ToastProvider>
          <ToastProvider>
            <EmailMessage status="COMPLETED" />
          </ToastProvider>
        </div>
      </div>
      <div className="sectionContainer">
        <div className="adminSettingsSectionHeader">Email Settings</div>
        <div className="emailSettingsContainer">
          <div className="emailSettingsInputDisplay">
            <input
              className="adminSettingsInput"
              placeholder="username"
              label="Input"
            />
            <input
              className="adminSettingsInput"
              placeholder="password"
              label="Input"
            />
          </div>
          <div className="adminSettingsButton emailSettingsButton">Save</div>
        </div>
      </div>
    </div>
  );
};

export default AdminSettings;
