import React, { useState } from 'react';
import './ProfileInfo.css';

import Dropdown from './components/Dropdown';

const UserProfilesContainer = props => {
  const { name } = props.userData;

  const locationOptions = [
    'Downtown',
    'Central',
    'Bear Valley',
    'Park Hill',
    'Hampden',
  ];
  const titleOptions = ['Admin', 'Staff'];

  const triggerDelete = () => {
    alert(`Are you sure you want to delete this profile? ${name}`);
  };

  const triggerPasswordChange = () => {
    alert(`Are you sure you want to change your password? ${name}`);
  };

  return (
    <div style={{ backgroundColor: props.color }} className="infoContainer">
      <h3>{name}</h3>
      <div className="dropdownContainer">
        <Dropdown dropdownOptions={titleOptions} />
        <Dropdown dropdownOptions={locationOptions} />
      </div>

      <button className="passwordBtn" onClick={triggerPasswordChange}>
        Change Password
      </button>
      <button className="deleteBtn" onClick={triggerDelete}>
        Delete
      </button>
    </div>
  );
};

export default UserProfilesContainer;
