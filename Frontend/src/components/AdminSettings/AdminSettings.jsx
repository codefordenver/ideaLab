import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
import { CirclePicker } from 'react-color';
import './AdminSettings.css';

const moveColorToUnavailable = color => {
  window.alert(color + ' is no longer available.');
};

const deleteClicked = hue => {
  const confirmedClicked = window.confirm(
    'Please confirm this color is no longer available',
  );
  if (confirmedClicked) {
    moveColorToUnavailable(hue.hex);
  }
};

const AdminSettings = () => {
  const [availableColors, setAvailableColors] = useState([]);
  const [unavailableColors, setUnavailableColors] = useState([]);

  useEffect(() => {
    RequestService.getActiveColors(
      response => {
        const data = response.data.data;
        let availableColorList = [];
        data.map(color => {
          availableColorList.push(color.color);
        });
        setAvailableColors(availableColorList);
      },
      error => console.error('GET COLORS ERR: ', error),
    );
    RequestService.getInactiveColors(
      response => {
        const data = response.data.data;
        let unavailableColorList = [];
        data.map(color => {
          unavailableColorList.push(color.color);
        });
        setUnavailableColors(unavailableColorList);
      },
      error => console.error('GET UNUSED COLORS ERR: ', error),
    );
  }, []);

  useEffect(() => {
    const colorCircles = document.getElementsByClassName('circle-picker ')[0];
    Array.from(colorCircles.children).forEach((circle, i) => {
      const testText = document.createElement('div');
      testText.setAttribute('class', 'color-ex');
      testText.setAttribute('id', availableColors[i].substr(1));
      const text = document.createTextNode('X');
      testText.appendChild(text);
      circle.appendChild(testText);
    });
  }, [availableColors]);

  return (
    <div className="adminSettingsContainer">
      <div className="sectionContainer">
        <div className="adminSettingsSectionHeader">Available Colors</div>
        <div className="colorPickerContainer">
          <CirclePicker
            circleSpacing={20}
            circleSize={48}
            colors={availableColors}
            onChangeComplete={deleteClicked}
          />
        </div>
        <div className="sectionContainer">
          <div className="adminSettingsSectionHeader">Unavailable Colors</div>
          <div className="colorPickerContainer">
            <CirclePicker
              circleSpacing={5}
              circleSize={24}
              colors={unavailableColors}
            />
          </div>
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
