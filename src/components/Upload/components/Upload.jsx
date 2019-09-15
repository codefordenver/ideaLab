import React, { useCallback } from 'react'
import { useDropzone } from 'react-dropzone'

function MyDropzone() {
    const onDrop = useCallback(acceptedFiles => {
    }, [])
    const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop })

    return (
        <div  className={"upload"} {...getRootProps()}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                    <p>upload or drag STL files</p> :
                    <p>upload STL files</p>
            }
        </div>
    )
}

export default MyDropzone;