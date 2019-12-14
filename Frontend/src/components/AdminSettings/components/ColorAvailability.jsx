import React, { useEffect, useState } from 'react';
import { CirclePicker } from 'react-color';
import RequestService from '../../../util/RequestService';
import './ColorAvailability.css';


const ColorAvailability = props => {
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
          element.innerHTML = 'L';
        }
        
    
        /*var updatedColors = allColorsAvailable;
        updatedColors[index] = !updatedColors[index];
        setAllColorsAvailable(updatedColors);*/
    
    
      }

    const onFailure = () => {
        console.log("The color availability status did not change succesfully.");
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
                textVariable = 'L';
              }
              const text = document.createTextNode(textVariable);
              testText.appendChild(text);
              circle.appendChild(testText);
            });
        }
      }
      }, [allColors, allColorsAvailable, allColorsId]);


      return (<CirclePicker
        circleSpacing={20}
        circleSize={48}
        colors={allColors}
        onChangeComplete={changeClicked}
      />);
}

export default ColorAvailability;