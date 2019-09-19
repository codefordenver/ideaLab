import React, { useState, useEffect } from 'react';
import dummyData from '../dummyData';
import PrintCardContainer from './components/PrintCardContainer';
import './QueueContainer.css';
import SearchBar from './components/SearchBar';

const QueueContainer = () => {
	const [data] = useState(dummyData);
	const [filteredData, setFilteredData] = useState(data);
	const [stringedValues, setStringedValues] = useState([]);
	const [statusView, setStatusView] = useState('QUEUEING');

	useEffect(() => {
		const filteredKeys = [
			'name',
			'email',
			'color',
			'status',
			'fileName',
			'comments'
		];
		const searchValues = data.map((printJob, index) => {
        	let valueString = '';
            for (var key in printJob) {
                if (filteredKeys.indexOf(key) !== -1) {
                    valueString = valueString + ' ' + printJob[key];
                }
            } 
            return printJob[index] = valueString.toLowerCase();
        });
		const queuedCards = data.filter(card => {
			var sameStatus = card.status === statusView;
			var doneAndFailed = statusView === 'DONE' && (card.status === 'DONE' || card.status === 'FAILED');
			return sameStatus || doneAndFailed;
		});
		setFilteredData(queuedCards);
		setStringedValues(searchValues);
		console.log(queuedCards);
	}, [data, statusView]);

	const filterByTerm = searchTerm => {
		const filteredSearch = data.filter((printJob, i) => {
			return stringedValues[i].indexOf(searchTerm.toLowerCase()) !== -1;
		});
		setFilteredData(filteredSearch);
	};

	const renderPrintCards = filteredData.map((el, i) => (
		<PrintCardContainer data={el} key={i} />
	));

	return (
		<div>
			<div className='queueFilterInfo'>
				<ul className='statusMenu'>
					<li className={statusView === 'QUEUEING' ? 'selectedTab' : ''}>
						<button onClick={() => setStatusView('QUEUEING')}>Queue</button>
					</li>
					<li className={statusView === 'DONE' ? 'selectedTab' : ''}>
						<button onClick={() => setStatusView('DONE')}>Recently Completed</button>
					</li>
					<li className={statusView === 'PRINTING' ? 'selectedTab' : ''}>
						<button onClick={() => setStatusView('PRINTING')}>In Progress</button>
					</li>
				</ul>
				<SearchBar filterByTerm={filterByTerm} />
			</div>
			<ul className='queueBanner'>
				<li className='col10'></li>
				<li className='col20'>File Name</li>
				<li className='col20'>Color</li>
				<li className='col20'>Submitted</li>
				<li className='col20'>Status</li>
			</ul>
			{renderPrintCards.length > 0 ? renderPrintCards : `No items are currently ${statusView.toLowerCase()}`}
		</div>
	);
};

export default QueueContainer;