import React, { Fragment } from 'react';
import { CirclePicker } from 'react-color';

const ColorPickerContainer = props => {
  const {
    handleColorChange,
    color,
    colors,
    hoverState,
    handleMouseEnter,
    colorCircleStyle,
  } = props;

  return (
    <div>
      <div
        className="colorCircle"
        style={colorCircleStyle}
        onMouseEnter={handleMouseEnter}
      />

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
