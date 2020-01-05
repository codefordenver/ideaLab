import React, { useEffect, useState } from 'react';
import { CirclePicker } from 'react-color';
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

  const changeClicked = hue => {
    // let index = allColors.findIndex(x => {
    //   return (
    //     x.substring(1).toUpperCase() === hue.hex.substring(1).toUpperCase()
    //   );
    // });
    // var avail = allColorsAvailable[index];
    // var id = allColorsId[index];
    // const confirmedClicked = window.confirm(
    //   'Please confirm you want to change the availability status of this color',
    // );
    // if (confirmedClicked) {
    //   var data = {
    //     color: id,
    //     body: { availability: !avail },
    //   };
    //   RequestService.putColorAvailability(data, onSuccess(index), onFailure);
    // }
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
    allColors.map(color => {
      const colorRectStyle = {
        backgroundColor: `${color.hue}`,
      };
      circleRender.push(
        <div className="customCircle">
          <div className="availRectDisplay" style={colorRectStyle} />
          <input type="checkbox" checked={color.available}></input>
        </div>,
      );
    });
    return circleRender;
  };

  return <div className="colorGrid">{renderCircles()}</div>;
};

export default ColorAvailability;
