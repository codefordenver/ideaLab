import React, { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';

function Upload(props) {
  const [fileSizeError, setFileSizeError] = useState(false);
  const onDrop = useCallback(acceptedFiles => {
    if (acceptedFiles.length > 0) {
      props.callback(acceptedFiles);
    } else {
      setFileSizeError(true);
    }
  }, []);
  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop: onDrop,
    maxSize: 100000000,
    multiple: false,
  });

  return (
    <div className={'upload'} {...getRootProps()}>
      <input {...getInputProps()} />
      {isDragActive ? <p>upload or drag STL files</p> : <p>upload STL files</p>}
      <span>Max file size is 100MB</span>
      {fileSizeError ? <span className={"error"}>Max file size exceeded</span> : false}
    </div>
  );
}

export default Upload;
