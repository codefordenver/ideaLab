import React, { useState } from 'react';
import MenuBar from '../globalStyles/MenuBar';
import AuthContext from '../../AuthContext';
import { MenuTabs } from '../globalStyles/MenuTabs';
import UserProfilesContainer from './UserProfilesContainer';
import AdminSettings from './AdminSettings';

const AdminContainer = () => {
  const [adminView, setView] = useState('settings');
  let currentView =
    adminView === 'settings' ? <AdminSettings /> : <UserProfilesContainer />;
  return (
    <AuthContext.Consumer>
      {context => {
        return context.role === 'ADMIN' ? (
          <div className="adminContainer">
            <MenuBar
              selectedTab={adminView}
              tabOptions={MenuTabs.ManageAccountTabs}
              setView={setView}
            />
            {currentView}
          </div>
        ) : (
          <div className="permissionDeniedWarning">
            You do not have permissions to view this page
          </div>
        );
      }}
    </AuthContext.Consumer>
  );
};

export default AdminContainer;
