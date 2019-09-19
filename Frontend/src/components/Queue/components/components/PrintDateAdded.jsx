import React from 'react'

const PrintDateAdded = ({data}) => {
    const dateDigit = data.dateAdded;
    const dateAddedFull = new Date(dateDigit);
    const dateAdded = `${dateAddedFull.getMonth()}/${dateAddedFull.getDay()}/${dateAddedFull.getFullYear()}`

    return (
        <div>
            {dateAdded}
        </div>
    )
}

export default PrintDateAdded
