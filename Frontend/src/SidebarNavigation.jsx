import React from 'react';
import AuthContext from './AuthContext';
import './App.css';
import { NavLink } from 'react-router-dom';
import { FiUpload, FiUserPlus, FiList, FiLogOut } from 'react-icons/fi';

class SidebarNavigation extends React.Component {
  render() {
    return (
      <AuthContext.Consumer>
        {context => {
          return (
            <nav className={'grid-item-nav'}>
              <div className={'navList'}>
                <li>
                  <NavLink to="/upload" activeClassName={'selected'}>
                    <div className="sidebarIcon" title="Upload">
                      <FiUpload />
                    </div>
                  </NavLink>
                </li>
                <li>
                  <NavLink to="/queue" activeClassName={'selected'}>
                    <div className="sidebarIcon" title="Queue">
                      <FiList />
                    </div>
                  </NavLink>
                </li>
                <li className={context.isAdmin ? '' : 'hidden'}>
                  <NavLink to="/manageaccounts" activeClassName={'selected'}>
                    <div className="sidebarIcon" title="Manage Accounts">
                      <FiUserPlus />
                    </div>
                  </NavLink>
                </li>
                <li onClick={context.authenticated ? this.props.logout : null}>
                  <NavLink to="/login" activeClassName={'selected'}>
                    <div
                      className="sidebarIcon"
                      title={context.authenticated ? 'log out' : 'log in'}
                    >
                      <FiLogOut />
                    </div>
                  </NavLink>
                </li>
              </div>
            </nav>
          );
        }}
      </AuthContext.Consumer>
    );
  }
}

export default SidebarNavigation;
