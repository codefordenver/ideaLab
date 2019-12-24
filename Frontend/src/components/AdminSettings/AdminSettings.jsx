import React from 'react';
import './AdminSettings.css';
import ColorAvailability from './components/ColorAvailability';

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
