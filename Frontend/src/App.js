import React, { useState, useEffect } from 'react';
import './App.css';
import AuthContext from './AuthContext';
import QueueContainer from './components/Queue/QueueContainer';
import UploadContainer from './components/Upload/UploadContainer';
import LoginManager from './components/Login/LoginManager';
import AdminContainer from './components/AdminSettings/AdminContainer';
import CreateAccountManager from './components/AdminSettings/CreateAccountManager';
import AnalysisContainer from './components/Analysis/AnalysisContainer';
import SidebarNavigation from './SidebarNavigation';
import PrivateRoute from './components/Routing/PrivateRoute';

import { ToastProvider } from 'react-toast-notifications';

import { HashRouter, Switch, Route, Redirect } from 'react-router-dom';
import RequestService from './util/RequestService';
import TokenParser from './util/TokenParser';

function App() {
  const initialState = {
    authenticated: false,
    token: null,
    role: 'STAFF',
    employeeId: null,
  };

  const [state, setState] = useState(initialState);

  useEffect(() => {
    const storedToken = localStorage.getItem('ideaLab');

    if (storedToken) {
      RequestService.requestState.token = storedToken;
      const decoded = TokenParser(storedToken);
      const now = Date.now() / 1000;
      if (now < decoded.exp) {
        setState({
          authenticated: true,
          token: storedToken,
          role: decoded.role,
          employeeId: decoded.employeeId,
        });
      } else {
        localStorage.removeItem('ideaLab');
      }
    }
  }, []);

  const queueContainer = () => {
    return (
      <ToastProvider>
        <QueueContainer />
      </ToastProvider>
    );
  };

  return (
    <div className="App grid-container">
      <AuthContext.Provider
        value={{
          authenticated: state.authenticated,
          token: state.token,
          role: state.role,
          employeeId: state.employeeId,
          setState: setState,
        }}
      >
        <HashRouter>
          <SidebarNavigation
            logout={() => {
              localStorage.removeItem('ideaLab');
              setState(initialState);
            }}
          />
          <Switch>
            <PrivateRoute exact path="/queue" component={queueContainer} />
            <PrivateRoute
              exact
              path="/manageaccounts"
              component={AdminContainer}
            />
            <PrivateRoute exact path="/upload" component={UploadContainer} />
            <Route
              path="/login"
              render={props => {
                return state.authenticated ? (
                  <Redirect to={'/queue'} />
                ) : (
                  <LoginManager {...props} />
                );
              }}
            />

            <PrivateRoute path="/account" component={AdminContainer} />
            <PrivateRoute path="/create" component={CreateAccountManager} />
            <PrivateRoute
              exact
              path="/analysis"
              component={AnalysisContainer}
            />
            <PrivateRoute path="/" component={QueueContainer} />
          </Switch>
        </HashRouter>
      </AuthContext.Provider>
    </div>
  );
}

export default App;
