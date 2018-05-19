import React from 'react';

import './LightSwitch.css'

class LightSwitch extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			on: this.props.on
		}
	}

	componentWillMount() {
		
	}

	render() {
		const className = this.state.on ? "switchOn" : "switchOff"
		return (
			<div className={className}>ON</div>
		);
	}
}

export default LightSwitch;