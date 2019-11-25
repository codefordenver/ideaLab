import React from 'react';
import Dropdown from 'react-bootstrap/Dropdown';

const StatusDropdown = ({ currentStatus, statusChanged, id }) => {
  return (
    <Dropdown title={currentStatus}>
      <Dropdown.Toggle variant="secondary" id={id}>
        {currentStatus}
      </Dropdown.Toggle>
      <Dropdown.Menu>
                <Dropdown.Item>Queueing</Dropdown.Item>
                <Dropdown.Item>Printing</Dropdown.Item>
                <Dropdown.Item>Failed</Dropdown.Item>
        <Dropdown.Item>Pending Customer Response</Dropdown.Item>
        <Dropdown.Item>Complete</Dropdown.Item>
      </Dropdown.Menu>
          
    </Dropdown>
  );
};

export default StatusDropdown;
