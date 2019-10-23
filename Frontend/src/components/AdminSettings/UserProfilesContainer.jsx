import React, { useState } from 'react';
import './UserProfileContainer.css';

import ProfileInfo from './components/ProfileInfo';
import UserDummyData from './UserDummyData';

const UserProfilesContainer = () => {
  const [data, setData] = useState(UserDummyData);
  const colors = ['#81B7E3', '#C4C4C4'];

  return (
    <div className="profilesStyles">
      <div className="createAccountButton">
        <button>CREATE ACCOUNT</button>
      </div>
      <div className="userProfileContainer">
        <div className="profilesContainer">
          {data.map((userData, index) => (
            <ProfileInfo
              color={colors[index % colors.length]}
              userData={userData}
              key={index}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default UserProfilesContainer;
