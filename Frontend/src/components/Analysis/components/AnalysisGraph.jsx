import React from 'react';
import { Bar } from 'react-chartjs-2';

const AnalysisGraph = props => {
  console.log(props.graphData);
  let labels = [];
  let datasets = {
    red: [],
    blue: [],
    green: [],
  };
  for (var key in props.graphData) {
    if (props.graphData.hasOwnProperty(key)) {
      labels.push(key);
      datasets['red'].push(props.graphData[key]['red']);
      datasets['blue'].push(props.graphData[key]['blue']);
      datasets['green'].push(props.graphData[key]['green']);
    }
  }
  console.log(labels);
  console.log(datasets);
  const allData = {
    labels: labels,
    datasets: [
      {
        label: 'Red',
        backgroundColor: 'rgb(240, 52, 52)',
        data: datasets['red'],
      },
      {
        label: 'Blue',
        backgroundColor: 'rgb(92, 151, 191)',
        data: datasets['blue'],
      },
      {
        label: 'Green',
        backgroundColor: 'rgb(35, 203, 167)',
        data: datasets['green'],
      },
    ],
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
