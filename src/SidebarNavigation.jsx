import React from 'react';
import AuthContext from './AuthContext';
import './App.css';
import { NavLink } from 'react-router-dom';

class SidebarNavigation extends React.Component {
  render() {
    const authenticatedNav = (
      <>
        <li>
          <NavLink to="/upload" activeClassName={'selected'}>
            upload
          </NavLink>
        </li>
        <li>
          <NavLink to="/queue" activeClassName={'selected'}>
            queue
          </NavLink>
        </li>
        <li>
          <NavLink to="/manageaccounts" activeClassName={'selected'}>
            manage accounts
          </NavLink>
        </li>
        <li>
          <NavLink to="/analytics" activeClassName={'selected'}>
            analytics
          </NavLink>
        </li>
      </>
    );

    return (
      <AuthContext.Consumer>
        {context => {
          return (
            <nav className={'grid-item-nav'}>
              <ul className={'navList'}>
                {context.authenticated && authenticatedNav}

                <li onClick={context.authenticated ? this.props.logout : null}>
                  <NavLink to="/login" activeClassName={'selected'}>
                    {context.authenticated ? 'log out' : 'log in'}
                  </NavLink>
                </li>
              </ul>
            </nav>
          );
        }}
      </AuthContext.Consumer>
    );
  }
}

export default SidebarNavigation;
