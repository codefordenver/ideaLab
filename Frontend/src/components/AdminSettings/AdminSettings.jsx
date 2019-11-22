import React, { useState } from 'react';
import { CirclePicker } from 'react-color';
import './AdminSettings.css';

const AdminSettings = () => {
  return (
    <div className="adminSettingsContainer">
      <body>
        <div className="sectionContainer">
          <div className="adminSettingsSectionHeader">Current Colors</div>
          <div className="colorPickerContainer">
            <CirclePicker />
          </div>
        </div>
        <input
          className="adminSettingsInput"
          placeholder="Sample input"
          label="Input"
        />
        <div className="adminSettingsButton">Button</div>
      </body>
    </div>
  );
};

export default AdminSettings;
