import React, { useState, useEffect } from 'react';
// import RequestService from '../../util/RequestService';
import AuthContext from '../../AuthContext';
import './AnalysisContainer.css';
import AnalysisGraph from './components/AnalysisGraph';

const AnalysisContainer = () => {
  const [loading, setLoading] = useState(false);
  const [graphData, setGraphData] = useState([]);
  const sampleGraphData = require('./sampleGraphData.json');
  useEffect(() => {
    setLoading(true);
    // RequestService.getGraphData(
    //   response => {
    //     setGraphData(response.data);
    //     setLoading(false);
    //   },
    //   error => console.error(error),
    // );
    setGraphData(sampleGraphData);
    setLoading(false);
  }, []);

  return (
    <AuthContext.Consumer>
      {context => {
        return <AnalysisGraph loading={loading} graphData={graphData} />;
      }}
    </AuthContext.Consumer>
  );
};

export default AnalysisContainer;
