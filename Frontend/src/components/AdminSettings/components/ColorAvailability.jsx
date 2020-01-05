import React, { useEffect, useState } from 'react';
import RequestService from '../../../util/RequestService';

const ColorAvailability = () => {
  const [allColors, setAllColors] = useState([]);
  const [hexList, setHexList] = useState([]);

  const onSuccess = index => {
    // var updatedColors = allColorsAvailable;
    // updatedColors[index] = !updatedColors[index];
    // setAllColors(updatedColors);
  };

  const onFailure = () => {
    console.log('The color availability status did not change succesfully.');
  };

  const updateColorAvail = event => {
    event.preventDefault();
    const colorIndex = parseInt(event.target.name) + 1;
    const isAvail = event.target.value;
    const confirmedClicked = window.confirm(
      'Please confirm you want to change the availability status of this color',
    );
    if (confirmedClicked) {
      var data = {
        color: colorIndex,
        body: { availability: !isAvail },
      };
      RequestService.putColorAvailability(
        data,
        onSuccess(colorIndex),
        onFailure,
      );
    }
  };

  useEffect(() => {
    let colorList = [];
    let hexArray = [];
    RequestService.getAllColors(
      response => {
        const data = response.data.data;
        data.map((color, i) => {
          colorList.push({
            hue: color.color,
            available: color.available,
            id: i,
          });
          hexArray.push(color.color);
        });
        setAllColors(colorList);
        setHexList(hexArray);
      },
      error => console.error(error),
    );
  }, []);

  const renderCircles = () => {
    let circleRender = [];
    allColors.map((color, i) => {
      const colorRectStyle = {
        backgroundColor: `${color.hue}`,
      };
      circleRender.push(
        <div className="customCircle" key={i}>
          <div className="availRectDisplay" style={colorRectStyle} />
          <input
            type="checkbox"
            name={i}
            value={color.available}
            checked={color.available}
            onChange={event => updateColorAvail(event)}
          ></input>
        </div>,
      );
    });
    return circleRender;
  };

  return <div className="colorGrid">{renderCircles()}</div>;
};

export default ColorAvailability;
