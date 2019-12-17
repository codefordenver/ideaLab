import React, { useState } from 'react';
import './ProfileInfo.css';
import StyledDropdown from '../../globalStyles/StyledDropdown';
import RequestService from '../../../util/RequestService';

const UserProfilesContainer = props => {
  const { name, role } = props.userData;
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);
  const [success, setSuccess] = useState(false);

  const titleOptions = ['ADMIN', 'STAFF'];

  const triggerPasswordChange = () => {
    window.confirm(`Are you sure you want to change your password? ${name}`);
  };

  const updateUserRole = newRole => {
    setLoading(true);
    setError(false);
    setSuccess(false);
    RequestService.updateUsers(
      {
        username: props.userData.username,
        role: newRole,
      },

      response => {
        response.data.success ? setSuccess(true) : setError(true);
      },
      error => {
        console.log(error);
        setError(true);
      },
    );
    setLoading(false);
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
        <div className="roleUpdateStatus">
          {success ? 'Updated Role Successfully' : null}
          {error ? 'Unable to update role' : null}
        </div>
      </div>

      <button className="changePasswordButton" onClick={triggerPasswordChange}>
        Change Password
      </button>
    </div>
  );
};

export default UserProfilesContainer;
