import React from 'react';

import './Backdrop.css';

const Backdrop = props => {
  return (
    <div 
      className="Backdrop"
      style={{
        opacity: props.passwordChange ? '1' : '0',
        zIndex: props.passwordChange ? '100' : '-1'
      }}
    >
      { props.children }   
    </div>
  )
}

export default Backdrop;