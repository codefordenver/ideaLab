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
  const [authenticated, setAuthenticated] = useState(true);
  const [token, setToken] = useState(true);
  const [isAdmin, setIsAdmin] = useState(true);

  return (
    <div className="App grid-container">
      <AuthContext.Provider
        value={{
          authenticated: authenticated,
          token: token,
          setAuthenticated: setAuthenticated,
          setToken: setToken,
          isAdmin: isAdmin,
        }}
      >
        <HashRouter>
          <SidebarNavigation
            logout={() => {
              setToken('');
              setAuthenticated(false);
              setIsAdmin(false);
            }}
            isAdmin={isAdmin}
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
