import React from 'react';

const BasicInput = props => {
  return (
    <div>
      <input
        onChange={e => props.changeHandler(e.target.value)}
        type="text"
        placeholder={props.placeHolder}
      />
      <span>{props.error ? props.error : null}</span>
    </div>
  );
};

export default BasicInput;
