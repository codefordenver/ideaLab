import React from 'react';
import { Route, Redirect } from 'react-router-dom';

import AuthContext from '../../AuthContext';
import { Logo } from './Logo';

function PrivateRoute({ component: Component, ...rest }) {
  return (
    <AuthContext.Consumer>
      {context => {
        return (
          <Route
            {...rest}
            render={props =>
              context.authenticated ? (
                <div>
                  <Logo />
                  <Component {...props} />
                </div>
              ) : (
                <Redirect
                  to={{
                    pathname: '/login',
                    state: { from: props.location },
                  }}
                />
              )
            }
          />
        );
      }}
    </AuthContext.Consumer>
  );
}

export default PrivateRoute;
