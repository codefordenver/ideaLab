import React, { useState } from 'react';
import './ProfileInfo.css';
import Backdrop from '../../globalStyles/Backdrop/Backdrop';
import ChangePasswordModal from './ChangePasswordModal/ChangePasswordModal';
import StyledDropdown from '../../globalStyles/StyledDropdown';

const UserProfilesContainer = props => {
  const [ passwordChange, setPasswordChange ] = useState(false); 

  const { name, role } = props.userData;

  const titleOptions = ['ADMIN', 'STAFF'];

  const triggerDelete = () => {
    alert(`Are you sure you want to delete this profile? ${name}`);
  };

  const triggerPasswordChange = () => {
    console.log(props.userData);
    setPasswordChange(!passwordChange);
  };

  const updateUserRole = newRole => {
    console.log('new role:', newRole);
    //TODO: get request, all employees at admin's current location
  };

  const changeModal = (
    <Backdrop passwordChange={ passwordChange }>
      <ChangePasswordModal 
        name={ name }
        passwordChange={ passwordChange }
        triggerPasswordChange={ triggerPasswordChange }
      />
    </Backdrop>
  )

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
      
      {changeModal}
    </div>
  );
};

export default UserProfilesContainer;
