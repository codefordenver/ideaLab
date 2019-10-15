import React, { useState } from 'react';
import './App.css';
import AuthContext from './AuthContext';
import QueueContainer from './components/Queue/QueueContainer';
import UploadContainer from './components/Upload/UploadContainer';
import LoginManager from './components/Login/LoginManager';
import AdminContainer from './components/AdminSettings/AdminContainer';
import SidebarNavigation from './SidebarNavigation';
import PrivateRoute from './components/Routing/PrivateRoute';

import { HashRouter, Switch, Route, Redirect } from 'react-router-dom';

function App() {
  const [authenticated, setAuthenticated] = useState(false);
  const [token, setToken] = useState(null);
  const [role, setRole] = useState('STAFF');

  return (
    <div className="App grid-container">
      <AuthContext.Provider
        value={{
          authenticated: authenticated,
          token: token,
          role: role,
          setAuthenticated: setAuthenticated,
          setToken: setToken,
          setRole: setRole,
        }}
      >
        <HashRouter>
          <SidebarNavigation
            logout={() => {
              setToken('');
              setAuthenticated(false);
              setRole('STAFF');
            }}
          />
          <Switch>
            <PrivateRoute exact path="/queue" component={QueueContainer} />
            <PrivateRoute
              exact
              path="/manageaccounts"
              component={AdminContainer}
            />
            <PrivateRoute exact path="/upload" component={UploadContainer} />
            <Route
              path="/login"
              render={props =>
                authenticated ? (
                  <Redirect
                    to={{
                      pathname: '/queue',
                      state: { from: props.location },
                    }}
                  />
                ) : (
                  <LoginManager {...props} />
                )
              }
            />

            <PrivateRoute path="/account" component={AdminContainer} />
            <PrivateRoute path="/" component={UploadContainer} />
          </Switch>
        </HashRouter>
      </AuthContext.Provider>
    </div>
  );
}

export default App;
