import React from 'react'

const PrintDateAdded = (props) => {
    const dateAddedFull = new Date(props.submitted);
    const dateAdded = `${dateAddedFull.getMonth()}/${dateAddedFull.getDay()}/${dateAddedFull.getFullYear()}`

    return (
        <div>
            {dateAdded}
        </div>
    )
}

export default PrintDateAdded
