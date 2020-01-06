import React, { useState } from 'react';
import './ProfileInfo.css';
import Backdrop from '../../globalStyles/Backdrop/Backdrop';
import ChangePasswordModal from './ChangePasswordModal/ChangePasswordModal';
import StyledDropdown from '../../globalStyles/StyledDropdown';
import RequestService from '../../../util/RequestService';

const UserProfilesContainer = props => {
  const [passwordChange, setPasswordChange] = useState(false);

  const { username, firstName, lastName, role } = props.userData;
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);
  const [success, setSuccess] = useState(false);

  const titleOptions = ['ADMIN', 'STAFF'];

  const triggerPasswordChange = () => {
    setPasswordChange(!passwordChange);
  };

  const triggerDeleteUser = () => {
    alert('placeholder delete user logic goes here');
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

  const changeModal = (
    <Backdrop showModal={passwordChange}>
      <ChangePasswordModal
        username={username}
        passwordChange={passwordChange}
        triggerPasswordChange={triggerPasswordChange}
      />
    </Backdrop>
  );

  return (
    <div style={{ backgroundColor: props.color }} className="infoContainer">
      <div className="employeeNameDisplay">{username}</div>
      <div className="userFullNameDisplay">
        {firstName} {lastName}
      </div>
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

      <button className="profilesButton change" onClick={triggerPasswordChange}>
        CHANGE PASSWORD
      </button>
      {changeModal}
    </div>
  );
};

export default UserProfilesContainer;
