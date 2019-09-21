import React from 'react';

const AuthContext = React.createContext({ authenticated: false, token: null });

export default AuthContext;