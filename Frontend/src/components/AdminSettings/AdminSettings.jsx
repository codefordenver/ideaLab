import React, { useEffect } from 'react';
import { CirclePicker } from 'react-color';
import './AdminSettings.css';

const deleteColorFromDatabase = color => {
  window.alert('POST here. deleted ', color);
};

const onColorClick = event => {
  const confirmedClicked = window.confirm('Are you sure you want to delete?');
  if (confirmedClicked) {
    deleteColorFromDatabase(event.hex);
  }
};

const AdminSettings = () => {
  useEffect(() => {
    const colorCircles = document.getElementsByClassName('circle-picker ')[0];
    const testText = document.createElement('span');
    const text = document.createTextNode('X');
    testText.appendChild(text);
    console.log(colorCircles.children);
    Array.from(colorCircles.children).forEach(function(circle) {
      console.log('cir', circle);
      circle.appendChild(testText);
    });
    // colorCircles.children.filter((circle) => {
    //   circle.appendChild(testText);
    // })
  }, []);
  return (
    <div className="adminSettingsContainer">
      <body>
        <div className="sectionContainer">
          <div className="adminSettingsSectionHeader">Current Colors</div>
          <div className="colorPickerContainer">
            <CirclePicker onChangeComplete={onColorClick} circleSpacing={20} />
          </div>
          <div className="adminSettingsButton">Add Color</div>
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
      </body>
    </div>
  );
};

export default AdminSettings;
