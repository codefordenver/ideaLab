import React from 'react';
import Menu from './Menu';
import DropdownItem from './DropdownItem';
import './Dropdown.css'


const Dropdown = (props) => {
    return (
        <div>
            <Menu>
                <DropdownItem>
                    Links >
                <Menu>
                        <DropdownItem>
                            <a href="https://google.com">Google it</a>
                        </DropdownItem>
                        <DropdownItem>
                            <a href="https://yahoo.com">Yahoo</a>
                        </DropdownItem>
                    </Menu>

                </DropdownItem>
                <DropdownItem>
                    Test has nested
                <Menu>
                        <li>Under construction...</li>
                        <DropdownItem>
                            A nested menu
                        <Menu>
                                <li onClick={() => console.log("Log something else")}>Log something else</li>
                                <li><a href={"https://bing.com"}>Bing it</a></li>
                            </Menu>
                        </DropdownItem>
                    </Menu>
                </DropdownItem>
                <DropdownItem>Loggers
                <Menu>
                        <button onClick={() => console.log('You can log things')}>Log it</button>
                    </Menu>
                </DropdownItem>
            </Menu>
        </div>
    )


}
export default Dropdown;