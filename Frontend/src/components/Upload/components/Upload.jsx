import React, { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';

function Upload(props) {
  const [filename, setFilename] = useState('');
  const onDrop = useCallback(acceptedFiles => {
    setFilename(acceptedFiles[0].name);
    props.callback(acceptedFiles);
  }, []);
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  return (
    <div className={'upload'} {...getRootProps()}>
      <input {...getInputProps()} />
      {isDragActive ? <p>upload or drag STL files</p> : <p>upload STL files</p>}
      {filename}
    </div>
  );
}

export default Upload;
