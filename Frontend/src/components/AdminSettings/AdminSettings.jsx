import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
import { CirclePicker } from 'react-color';
import './AdminSettings.css';

const onFailure = () => {
  console.log("The color availability status did not change succesfully.");
}



const AdminSettings = () => {
  const [allColors, setAllColors] = useState([]);
  const [allColorsAvailable, setAllColorsAvailable] = useState([]);
  const [allColorsId, setAllColorsId] = useState([]);

  const onSuccess = index => {
    console.log("This color availability status was succesfully changed.");
    const element = document.getElementsByClassName('color-availability-mark')[allColors[index].toUpperCase()];

    var updatedColors = allColorsAvailable;
    updatedColors[index] = !updatedColors[index];
    setAllColorsAvailable(updatedColors);
    console.log("Check ");
    console.log(element);
    console.log(allColorsAvailable[index]);
    console.log(allColorsAvailable[index] === false);
    console.log(allColorsAvailable[index] === true);
    console.log(allColors);
    console.log(allColorsAvailable);

    if (allColors[index] === false) {
      element.innerHTML = 'X';
    } else {
      element.innerHTML = '';
    }

    /*var updatedColors = allColorsAvailable;
    updatedColors[index] = !updatedColors[index];
    setAllColorsAvailable(updatedColors);*/


  }

  const changeClicked = hue => {
    
    let index = allColors.findIndex(x => {
      return x.substring(1).toUpperCase() === hue.hex.substring(1).toUpperCase()
    });

    var avail = allColorsAvailable[index];
    var id = allColorsId[index];

    const confirmedClicked = window.confirm('Please confirm you want to change the availability status of this color',);
    if (confirmedClicked) {

      var data = {
        color: id,
        body: {availability: !avail}
      }
      RequestService.putColorAvailability(data, onSuccess(index), onFailure);
    }
  };

  useEffect(() => {
    RequestService.getAllColors(
      response => {
        const data = response.data.data;
        let colorList = [];
        let available = [];
        let idList = [];
        data.map(color => {
          idList.push(color.id);
          colorList.push(color.color);
          available.push(color.available);
        });
        setAllColors(colorList);
        setAllColorsAvailable(available);
        setAllColorsId(idList);
      },
      error => console.error('GET COLORS ERR: ', error),
    );
  }, []);

  useEffect(() => {
    console.log("Using the effect");
    const availabilityMark = document.getElementsByClassName('color-availability-mark');
    if (! (availabilityMark && availabilityMark.length)) {
      if (allColors && allColorsAvailable && allColors.length && allColorsAvailable.length) {
        const colorCircles = document.getElementsByClassName('circle-picker ')[0];
        Array.from(colorCircles.children).forEach((circle, i) => {
          const testText = document.createElement('div');
          testText.setAttribute('class', 'color-availability-mark');
          testText.setAttribute('id', allColors[i]);
          let textVariable;
          if (allColorsAvailable[i] === false) {
            textVariable = 'X';
          } else {
            textVariable = '';
          }
          const text = document.createTextNode(textVariable);
          testText.appendChild(text);
          circle.appendChild(testText);
        });
    }
  }
  }, [allColors, allColorsAvailable, allColorsId]);

  return (
    <div className="adminSettingsContainer">
      <div className="sectionContainer">
        <div className="adminSettingsSectionHeader">Color Availability</div>
        <div className="colorPickerContainer">
          <CirclePicker
            circleSpacing={20}
            circleSize={48}
            colors={allColors}
            onChangeComplete={changeClicked}
          />
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
