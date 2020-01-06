import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
import AuthContext from '../../AuthContext';
import './AnalysisContainer.css';
import AnalysisGraph from './components/AnalysisGraph';

const AnalysisContainer = () => {
  const [loading, setLoading] = useState(false);
  const [graphData, setGraphData] = useState([]);

  useEffect(() => {
    setLoading(true);
    RequestService.getGraphDataByColors(
      response => {
        console.log(response.data.data);
        setGraphData(response.data.data);
        setLoading(false);
      },
      error => console.error('Error loading graph data: ' + error),
    );
    //setGraphData(sampleGraphData);
    setLoading(false);
  }, []);

  return (
    <AuthContext.Consumer>
      {context => {
        return (
          <div className="analysisContainer">
            <div className="flexbox">
              <AnalysisGraph loading={loading} graphData={graphData} />
            </div>
          </div>
        );
      }}
    </AuthContext.Consumer>
  );
};

export default AnalysisContainer;
