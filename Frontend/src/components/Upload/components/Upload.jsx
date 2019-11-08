import React, { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';

function Upload(props) {
  const onDrop = useCallback(acceptedFiles => {
    props.setFilename(acceptedFiles[0].name);
    props.callback(acceptedFiles);
  }, [props]);
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  return (
    <div className={'upload'} {...getRootProps()}>
      <input {...getInputProps()} />
      {isDragActive ? <p>upload or drag STL files</p> : <p>upload STL files</p>}
      {props.filename}
    </div>
  );
}

export default Upload;
