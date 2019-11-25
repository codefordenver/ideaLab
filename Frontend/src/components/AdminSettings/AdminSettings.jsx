import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
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
  const [availableColors, setAvailableColors] = useState();

  useEffect(() => {
    RequestService.getActiveColors(
      response => {
        const data = response.data.data;
        var colorList = [];
        data.map(color => {
          colorList.push(color.color);
        });
        setAvailableColors(colorList);
      },
      error => console.error('GET COLORS ERR: ', error),
    );
  }, [availableColors]);

  useEffect(() => {
    const colorCircles = document.getElementsByClassName('circle-picker ')[0];
    Array.from(colorCircles.children).forEach(function(circle) {
      const testText = document.createElement('div');
      const text = document.createTextNode('X');
      testText.appendChild(text);
      circle.appendChild(testText);
    });
  }, []);
  return (
    <div className="adminSettingsContainer">
      <body>
        <div className="sectionContainer">
          <div className="adminSettingsSectionHeader">Current Colors</div>
          <div className="colorPickerContainer">
            <CirclePicker
              onChangeComplete={onColorClick}
              circleSpacing={20}
              colors={availableColors}
            />
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
