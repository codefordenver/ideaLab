import React, { useState } from 'react';
import { CirclePicker } from 'react-color';
import './AdminSettings.css';

const AdminSettings = () => {
  return (
    <div className="adminSettingsContainer">
      <body>
        <div className="headerContainer">Header Goes Here</div>
        <div className="colorPickerContainer">
          <CirclePicker />
        </div>
        <div className="sectionContainer">
          <div className="adminSettingsSectionHeader">Sample List of Things</div>
          <hr className="horizontalLine" />
          <div className="listContainer">
            <li className="sectionListItem">Item 1</li>
            <li className="sectionListItem">Item 2</li>
            <li className="sectionListItem">Item 3</li>
          </div>
        </div>
        <input className="adminSettingsInput" placeholder="Sample input" label="Input" />
        <div className="adminSettingsButton">Button</div>

      </body>
    </div>
  );
};

export default AdminSettings;
