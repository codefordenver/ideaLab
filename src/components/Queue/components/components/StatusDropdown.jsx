import React, { useState } from 'react';
import './StatusDropdown.css';

const StatusDropdown = ({ data }) => {
  const [printStatus, setPrintStatus] = useState(data.status);

  return (
    <div className="statusDropdown">
      <select
        className="statusDropdownSelect"
        name="printStatus"
        defaultValue={printStatus}
      >
        <option>PRINTING</option>
        <option>QUEUEING</option>
        <option>FAILED</option>
        <option>FAILED AGAIN</option>
      </select>
    </div>
  );
};

export default StatusDropdown;
