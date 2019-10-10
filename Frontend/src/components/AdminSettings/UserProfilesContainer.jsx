import React, { useState } from 'react';
import './UserProfileContainer.css';

import ProfileInfo from './components/ProfileInfo';
import UserDummyData from './UserDummyData';

const UserProfilesContainer = () => {
  const [data, setData] = useState(UserDummyData);
  const colors = ['#81B7E3', '#C4C4C4'];

  return (
    <div className="container">
      <button>Create Account</button>
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
  );
};

export default UserProfilesContainer;
