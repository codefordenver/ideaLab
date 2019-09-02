import React from 'react';
import './App.css';
import { NavLink } from 'react-router-dom';

class SidebarNavigation extends React.Component {
	constructor(props) {
		super(props);
	}

    render() {
        return (
            <nav className={"grid-item-nav"}>
                <ul className={"navList"}>
                    <li><NavLink to="/upload" activeClassName={"selected"}>upload</NavLink></li>
                    <li><NavLink to="/queue" activeClassName={"selected"}>queue</NavLink></li>
                    <li><NavLink to="/manageaccounts" activeClassName={"selected"}>manage accounts</NavLink></li>
                    <li><NavLink to="/analytics" activeClassName={"selected"}>analytics</NavLink></li>
                    <li onClick={this.props.logout}><NavLink to="/login" activeClassName={"selected"}>log out</NavLink></li>
                </ul>
            </nav>
        );
    }
}

export default SidebarNavigation;
