import React from 'react';
import { Bar } from 'react-chartjs-2';

const AnalysisGraph = props => {
  let labels = [];
  let datasets = {};
  let colors = [];
  let finalData = [];
  for (var key in props.graphData) {
    if (props.graphData.hasOwnProperty(key)) {
      labels.push(key);
      for (var colorKey in props.graphData[key]) {
        if (props.graphData[key].hasOwnProperty(colorKey)) {
          if (datasets[colorKey] !== undefined) {
            datasets[colorKey].push(props.graphData[key][colorKey]);
          } else {
            datasets[colorKey] = [props.graphData[key][colorKey]];
            colors.push(colorKey);
          }
        }
      }
    }
  }
  for (let i = 0; i < colors.length; i++) {
    finalData.push({
      label: colors[i],
      backgroundColor: colors[i],
      data: datasets[colors[i]],
    });
  }
  const allData = {
    labels: labels,
    datasets: finalData,
  };
  const options = {
    scales: {
      xAxes: [
        {
          stacked: true,
        },
      ],
      yAxes: [
        {
          stacked: true,
        },
      ],
    },
  };
  return (
    <div className="analysisContainer">
      <Bar data={allData} options={options} />
    </div>
  );
};

export default AnalysisGraph;
