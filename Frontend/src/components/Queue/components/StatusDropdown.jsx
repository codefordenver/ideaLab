import React from 'react';
import './StatusDropdown.css';

const StatusDropdown = ({ currentStatus, statusChanged }) => {
  return (
    <div className="statusDropdown">
      <select
        className="statusDropdownSelect"
        name="status"
        defaultValue={currentStatus}
        onChange={statusChanged}
      >
        <option
          value="QUEUEING"
          selected={currentStatus === 'QUEUEING' ? 'selected' : ''}
        >
          QUEUEING
        </option>
        <option
          value="PRINTING"
          selected={currentStatus === 'PRINTING' ? 'selected' : ''}
        >
          PRINTING
        </option>
        <option
          value="SUCCESS"
          selected={currentStatus === 'SUCCESS' ? 'selected' : ''}
        >
          SUCCESS
        </option>
        <option
          value="FAILED"
          selected={currentStatus === 'FAILED' ? 'selected' : ''}
        >
          FAILED
        </option>
        <option
          value="FAILED_AGAIN"
          selected={currentStatus === 'FAILED_AGAIN' ? 'selected' : ''}
        >
          FAILED AGAIN
        </option>
      </select>
    </div>
  );
};

export default StatusDropdown;
