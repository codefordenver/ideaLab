import React, { useState } from 'react';
import './ProfileInfo.css';

import Dropdown from './components/Dropdown';

const UserProfilesContainer = props => {
  const locationOptions = [
    'Downtown',
    'Central',
    'Bear Valley',
    'Park Hill',
    'Hampden',
  ];
  const titleOptions = ['Admin', 'Staff'];

  return (
    <div style={{ backgroundColor: props.color }} className="infoContainer">
      <h3>{props.userData.name}</h3>
      <div className="dropdownContainer">
        <Dropdown dropdownOptions={titleOptions} />
        <Dropdown dropdownOptions={locationOptions} />
      </div>
      <button className="passwordBtn">Change Password</button>
      <button className="deleteBtn">Delete</button>
    </div>
  );
};

export default UserProfilesContainer;
