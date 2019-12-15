import React, { useEffect, useState } from 'react';
import Loader from '../globalStyles/Loader';
import RequestService from '../../util/RequestService';
import ProfileInfo from './components/ProfileInfo';
import './UserProfileContainer.css';

const UserProfilesContainer = () => {
  const [data, setData] = useState();
  const [loading, setLoading] = useState(true);
  const colors = ['#81B7E3', '#C4C4C4'];

  useEffect(() => {
    setLoading(true);
    RequestService.getUsers(
      response => {
        if (response.data.data) {
          const data = response.data.data;
          const formattedData = data.map(user => {
            const { firstName, lastName, username, role } = user;
            return {
              name: `${firstName} ${lastName} (${username})`,
              username: username,
              role: role,
            };
          });
          setData(formattedData);
        }
        setLoading(false);
      },
      error => console.error(error),
    );
  }, []);

  return (
    <div className="profilesStyles">
      <div className="createAccountButton">
        <a href="#/create">
          <button>CREATE ACCOUNT</button>
        </a>
      </div>
      {loading ? (
        <div className={'loader-container'}>
          Loading Fresh Data...
          <Loader />
        </div>
      ) : (
        false
      )}
      <div className="userProfileContainer">
        <div className="profilesContainer">
          {data
            ? data.map((userData, index) => (
                <ProfileInfo
                  color={colors[index % colors.length]}
                  userData={userData}
                  key={index}
                />
              ))
            : null}
        </div>
      </div>
    </div>
  );
};

export default UserProfilesContainer;
