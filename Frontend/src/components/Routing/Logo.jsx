import React from 'react';
import ideaLABlogo from '../globalStyles/img/ideaLabLogo.png';
import './Logo.css';

const Logo = props => (
  <div>
    <img className="logo" src={ideaLABlogo} alt={'ideaLab logo'}></img>
    <h2 className="header">{props.title}</h2>
  </div>
);

export { Logo };
