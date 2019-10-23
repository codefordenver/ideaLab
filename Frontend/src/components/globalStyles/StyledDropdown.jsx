import React, { useState } from 'react';

const StyledDropdown = props => {
  const [currentVal, updateValue] = useState(props.value);
  const { value, dropdownOptions, saveDropdownChange } = props;
  const dropdownUpdated = event => {
    updateValue(event.target.value);
    saveDropdownChange(event.target.value);
  };
  return (
    <div className="styledDropdown">
      <select value={value} onChange={dropdownUpdated} value={currentVal}>
        {dropdownOptions.map((dropdownOption, index) => (
          <option value={dropdownOption} key={index}>
            {dropdownOption}
          </option>
        ))}
      </select>
    </div>
  );
};

export default StyledDropdown;
