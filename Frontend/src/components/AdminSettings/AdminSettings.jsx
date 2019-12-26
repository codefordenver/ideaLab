import React from 'react';
import './AdminSettings.css';
import ColorAvailability from './components/ColorAvailability';
import UpdateEmail from './components/UpdateEmail';
import UpdateFileToken from './components/UpdateFileToken';
import { ToastProvider } from 'react-toast-notifications';

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
        <div className="adminSettingsSectionHeader">Current Locations</div>
        <div className="currentLocationsContainer">
          <div className="adminSettingsButton">Add Location</div>
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
