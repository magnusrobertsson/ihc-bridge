import React from 'react';
import LightSwitch from './LightSwitch/LightSwitch.js';

import 'normalize.css'

class App extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			result: { values: [] }
		}
	}

	componentWillMount() {
		fetch('/devices')
			.then(function(response) {
				return response.json();
			})
			.then((myJson) => {
                this.setState({ result: myJson })
			});
	}

    isSwitch(device) {
        return device.type == 'switch';
    }

	render() {
		return (
			<div>
		 { this.state.result.values.filter(this.isSwitch).map(device => {
				return ( <LightSwitch on={device.on} /> );
		 }) }
			</div>
		);
	}
}

export default App;