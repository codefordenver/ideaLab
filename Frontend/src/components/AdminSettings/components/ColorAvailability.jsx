import React, { useEffect, useState } from 'react';
import RequestService from '../../../util/RequestService';
import './ColorAvailability.css';

const ColorAvailability = () => {
  const [allColors, setAllColors] = useState([]);

  const onSuccess = index => {
    const colorIndex = index - 1;
    let updatedColors = allColors;
    updatedColors[colorIndex].available = !updatedColors[colorIndex].available;
    setAllColors(updatedColors);
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
    RequestService.getAllColors(
      response => {
        const data = response.data.data;
        const colorList = data.map((color, i) => {
          return {
            hue: color.color,
            available: color.available,
            id: i,
          };
        });
        setAllColors(colorList);
      },
      error => console.error(error),
    );
  }, [allColors]);

  const renderCircles = () => {
    const circleRender = allColors.map((color, i) => {
      const colorRectStyle = {
        backgroundColor: `${color.hue}`,
      };
      return (
        <div className="customCircle" key={i}>
          <div className="availRectDisplay" style={colorRectStyle} />
          <div className="checkbox-wrapper">
            <input
              className="checkbox-original"
              type="checkbox"
              name={i}
              value={color.available}
              checked={color.available}
              onChange={event => updateColorAvail(event)}
            ></input>
          </div>
        </div>
      );
    });
    return circleRender;
  };

  return <div className="colorGrid">{renderCircles()}</div>;
};

export default ColorAvailability;
