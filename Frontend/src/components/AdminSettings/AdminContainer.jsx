import React, { useState } from 'react';
import MenuBar from '../globalStyles/MenuBar';
import { MenuTabs } from '../globalStyles/MenuTabs';
import UserProfilesContainer from './UserProfilesContainer';
import AdminSettings from './AdminSettings';

const AdminContainer = () => {
  const [adminView, setView] = useState('settings');
  let currentView =
    adminView === 'settings' ? <AdminSettings /> : <UserProfilesContainer />;
  return (
    <div>
      <MenuBar
        selectedTab={adminView}
        tabOptions={MenuTabs.ManageAccountTabs}
        setView={setView}
      />
      {currentView}
    </div>
  );
};

export default AdminContainer;
