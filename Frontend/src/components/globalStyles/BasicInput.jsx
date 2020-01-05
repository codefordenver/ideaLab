import React from 'react';
import './BasicInput.css';

const BasicInput = props => {
  return (
    <div className="basicInput">
      <input
        value={props.value}
        onChange={e => props.changeHandler(e.target.value)}
        type={props.type}
        placeholder={props.placeHolder}
      />
      <span>{props.error ? props.error : null}</span>
    </div>
  );
};

export default BasicInput;
