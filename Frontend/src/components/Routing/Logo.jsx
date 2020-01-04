import React, { useCallback } from 'react';
import ideaLABlogo from '../globalStyles/img/ideaLabLogo.png';
import './Logo.css';

const Logo = () => (
  <img className="logo" src={ideaLABlogo} alt={'ideaLab logo'}></img>
);

export { Logo };
