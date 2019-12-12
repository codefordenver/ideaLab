import RequestService from './RequestService';

const processActiveColors = () => {
  let colorList = [];

  RequestService.getActiveColors(
    response => {
      const data = response.data.data;
      data.map(color => {
        colorList.push(color.color);
      });
    },
    error => console.error(error),
  );

  return colorList;
};

export { processActiveColors };
