import React from 'react';

import './Backdrop.css';

const Backdrop = props => {
  return (
    <div
      className="Backdrop"
      style={{
        opacity: props.showModal ? '1' : '0',
        zIndex: props.showModal ? '100' : '-1',
      }}
    >
      {props.children}
    </div>
  );
};

export default Backdrop;
