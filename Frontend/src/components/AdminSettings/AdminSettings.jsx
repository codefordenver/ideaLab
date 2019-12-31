import React from 'react';
import { ToastProvider } from 'react-toast-notifications';
import './AdminSettings.css';
import ColorAvailability from './components/ColorAvailability';
import EmailMessage from './components/EmailMessage';
import UpdateEmail from './components/UpdateEmail';
import UpdateFileToken from './components/UpdateFileToken';

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
        <ToastProvider>
          <UpdateEmail />
        </ToastProvider>
      </div>
      <div className="sectionContainer">
        <div className="adminSettingsSectionHeader">Dropbox Token Settings</div>
        <ToastProvider>
          <UpdateFileToken />
        </ToastProvider>
      </div>
    </div>
  );
};

export default AdminSettings;
