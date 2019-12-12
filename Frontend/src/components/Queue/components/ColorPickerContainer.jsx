import React, { Fragment, useState } from 'react';
import { CirclePicker } from 'react-color';

const ColorPickerContainer = props => {
  const { handleColorChange, color, colors, colorCircleStyle } = props;

  const [hoverState, setHoverState] = useState(false);

  return (
    <div onMouseLeave={() => setHoverState(false)}>
      <div
        className="colorCircle"
        style={colorCircleStyle}
        onMouseEnter={() => setHoverState(true)}
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
