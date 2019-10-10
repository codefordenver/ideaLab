import React, { useState } from 'react';
import './Dropdown.css';

const UserProfilesContainer = props => {
  return (
    <select>
      {props.dropdownOptions.map((dropdownOption, index) => (
        <option value={dropdownOption} key={index}>
          {dropdownOption}
        </option>
      ))}
    </select>
  );
};

export default UserProfilesContainer;
