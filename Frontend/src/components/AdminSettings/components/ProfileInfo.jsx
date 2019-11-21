import React from 'react';
import './ProfileInfo.css';
import StyledDropdown from '../../globalStyles/StyledDropdown';

const UserProfilesContainer = props => {
  const { name, role } = props.userData;

  const titleOptions = ['ADMIN', 'STAFF'];

  const triggerDelete = () => {
    alert(`Are you sure you want to delete this profile? ${name}`);
  };

  const triggerPasswordChange = () => {
    alert(`Are you sure you want to change your password? ${name}`);
  };

  const updateUserRole = newRole => {
    console.log('new role:', newRole);
    //TODO: get request, all employees at admin's current location
  };

  return (
    <div style={{ backgroundColor: props.color }} className="infoContainer">
      <div className="employeeNameDisplay">{name}</div>
      <div className="dropdownContainer">
        <StyledDropdown
          dropdownOptions={titleOptions}
          header="Role"
          value={role}
          saveDropdownChange={updateUserRole}
        />
      </div>

      <button className="changePasswordButton" onClick={triggerPasswordChange}>
        Change Password
      </button>
      <button className="deleteUserButton" onClick={triggerDelete}>
        Delete
      </button>
    </div>
  );
};

export default UserProfilesContainer;
