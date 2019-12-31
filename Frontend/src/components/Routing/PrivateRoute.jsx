import React from 'react';
import { Route, Redirect } from 'react-router-dom';

import AuthContext from '../../AuthContext';

function PrivateRoute({ component: Component, ...rest }) {
  return (
    <AuthContext.Consumer>
      {context => {
        return (
          <Route
            {...rest}
            render={props =>
              context.authenticated ? (
                <Component {...props} />
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
