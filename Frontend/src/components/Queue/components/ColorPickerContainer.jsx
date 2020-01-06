import React, { Fragment, useState } from 'react';
import { CirclePicker } from 'react-color';
import './ColorPickerContainer.css';

const ColorPickerContainer = props => {
  const {
    handleColorChange,
    color,
    colors,
    colorCircleStyle,
    allowBlank,
  } = props;
  const [hoverState, setHoverState] = useState(false);

  const returnNoColorSymbol = () => {
    if (allowBlank && color === '') {
      return ' ✗ ';
    }
    return '';
  };

  return (
    <div
      className="mouseOverColorPickerContainer"
      onMouseLeave={() => setHoverState(false)}
    >
      <div>
        <div
          className="colorCircle"
          style={colorCircleStyle}
          onMouseEnter={() => setHoverState(true)}
        >
          {returnNoColorSymbol()}
        </div>
      </div>

      <div className="colorSelectionStatus">
        {color ? null : ' No Color Selected'}
      </div>

      {hoverState ? (
        <div className="colorPickerContainer">
          <CirclePicker
            onChangeComplete={handleColorChange}
            color={color}
            colors={colors}
            width="250px"
            circleSize={18}
            circleSpacing={8}
          />
        </div>
      ) : (
        <Fragment />
      )}
    </div>
  );
};

export default ColorPickerContainer;
