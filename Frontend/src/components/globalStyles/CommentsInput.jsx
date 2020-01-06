import React from 'react';
import './BasicInput.css';

const CommentsInput = props => {
  return (
    <div className="basicInput">
      <textarea
        onChange={e => props.changeHandler(e.target.value)}
        rows={props.rows}
        value={props.value}
        placeholder={props.placeholder}
      />
    </div>
  );
};

export default CommentsInput;
