var http = require('http');
var fetch = require("node-fetch");
var Accessory, Service, Characteristic, UUIDGen;

module.exports = function(homebridge) {
  console.log("homebridge API version: " + homebridge.version);

  // Accessory must be created from PlatformAccessory Constructor
  Accessory = homebridge.platformAccessory;

  // Service and Characteristic are from hap-nodejs
  Service = homebridge.hap.Service;
  Characteristic = homebridge.hap.Characteristic;
  UUIDGen = homebridge.hap.uuid;
  
  // For platform plugin to be considered as dynamic platform plugin,
  // registerPlatform(pluginName, platformName, constructor, dynamic), dynamic must be true
  homebridge.registerPlatform("homebridge-ihc", "IhcPlatform", IhcPlatform, true);
}

// Platform constructor
// config may be null
// api may be null if launched from old homebridge version
function IhcPlatform(log, config, api) {
  log("IhcPlatform Init");
  var platform = this;
  this.log = log;
  this.config = config;
  this.accessories = [];

  if (api) {
      // Save the API object as plugin needs to register new accessory via this object
      this.api = api;

      // Listen to event "didFinishLaunching", this means homebridge already finished loading cached accessories.
      // Platform Plugin should only register new accessory that doesn't exist in homebridge after this event.
      // Or start discover new accessories.
      this.api.on('didFinishLaunching', function() {
        platform.log("DidFinishLaunching");
        //this.registerIhcAccessories()
      }.bind(this));
  }
}

IhcPlatform.prototype.registerIhcAccessories = function() {
  this.log("Add IHC devices as accessories");
  fetch('http://localhost:8080/devices')
      .then(response => {
        return response.json();
      })
      .then(myJson => {
        myJson.values.forEach(device => this.addAccessory(device))
      });

}

// Function invoked when homebridge tries to restore cached accessory.
// Developer can configure accessory at here (like setup event handler).
// Update current value.
IhcPlatform.prototype.configureAccessory = function(accessory) {
  this.log(accessory.displayName, "Configure Accessory");
  var platform = this;
  var device = accessory.context.device;

  // Set the accessory to reachable if plugin can currently process the accessory,
  // otherwise set to false and update the reachability later by invoking 
  // accessory.updateReachability()
  accessory.reachable = true;

  accessory.on('identify', function(paired, callback) {
    platform.log(accessory.displayName, "Identify!!!");
    callback();
  });

  if (device.type === "dimmable-light" || device.type === "binary-light") {
    this.configureLightbulb(accessory);
  } else if (device.type === "security-alarm") {
    this.configureSecuritySystem(accessory);
  } else {
    console.log("Unknown device type: " + device.type)
    return;
  }

  this.accessories.push(accessory);
}


IhcPlatform.prototype.configureLightbulb = function(accessory) {
  var service = accessory.getService(Service.Lightbulb);
  var platform = this;
  var device = accessory.context.device;

  service.getCharacteristic(Characteristic.On)
    .on('get', function(callback) {
      fetch('http://localhost:8080/devices/' + device.id)
        .then(response => { return response.json(); })
        .then(json => { return json.on; })
        .then(value => {
          callback(null, value)
        })
    })
    .on('set', function(value, callback) {
      fetch('http://localhost:8080/devices/' + device.id + '/on', {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ on: value })
      }).then(response => {
        callback();
      });
    });

  if (device.type === "dimmable-light") {
    service.getCharacteristic(Characteristic.Brightness)
      .on('get', function(callback) {
        fetch('http://localhost:8080/devices/' + device.id)
          .then(response => { return response.json(); })
          .then(json => { return json.dim; })
          .then(value => {
            callback(null, value)
          })
      })
      .on('set', function(value, callback) {
        fetch('http://localhost:8080/devices/' + device.id + '/dim', {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ dim: value })
        }).then(response => {
          callback();
        });
      });
  }
}



IhcPlatform.prototype.configureSecuritySystem = function(accessory) {
  var service = accessory.getService(Service.SecuritySystem);
  var platform = this;
  var device = accessory.context.device;

  service.getCharacteristic(Characteristic.SecuritySystemCurrentState)
    .on('get', function(callback) {
      fetch('http://localhost:8080/devices/' + device.id)
        .then(response => { return response.json(); })
        .then(json => { return json.currentState; })
        .then(value => {
          callback(null, value)
        })
    });

  service.getCharacteristic(Characteristic.SecuritySystemTargetState)
    .on('get', function(callback) {
      fetch('http://localhost:8080/devices/' + device.id)
        .then(response => { return response.json(); })
        .then(json => { return json.targetState; })
        .then(value => {
          callback(null, value)
        })
    })
    .on('set', function(value, callback) {
      fetch('http://localhost:8080/devices/' + device.id + '/targetState', {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ targetState: value })
        }).then(response => {
          service.getCharacteristic(Characteristic.SecuritySystemCurrentState).setValue(value);
          callback();
        });
    });
}


// Sample function to show how developer can add accessory dynamically from outside event
IhcPlatform.prototype.addAccessory = function(device) {
  this.log("Add Accessory " + device.name);
  var platform = this;
  var uuid;

  uuid = UUIDGen.generate(device.name);

  var newAccessory = new Accessory(device.name, uuid);
  newAccessory.on('identify', function(paired, callback) {
    platform.log(newAccessory.displayName, "Identify!!!");
    callback();
  });

  newAccessory.context.device = device;
  // Plugin can save context on accessory to help restore accessory in configureAccessory()
  // newAccessory.context.something = "Something"
  
  // Make sure you provided a name for service, otherwise it may not visible in some HomeKit apps
  if (device.type === "dimmable-light" || device.type === "binary-light") {
    newAccessory.addService(Service.Lightbulb, device.name);
    this.configureLightbulb(newAccessory)
  } else if (device.type === "security-alarm") {
    newAccessory.addService(Service.SecuritySystem, device.name)
    this.configureSecuritySystem(newAccessory)
  } else {
    console.log("Unknown device type: " + device.type)
    return;
  }

  this.accessories.push(newAccessory);
  this.api.registerPlatformAccessories("homebridge-ihc", "IhcPlatform", [newAccessory]);
}






IhcPlatform.prototype.updateAccessoriesReachability = function() {
  this.log("Update Reachability");
  for (var index in this.accessories) {
    var accessory = this.accessories[index];
    accessory.updateReachability(false);
  }
}




// Sample function to show how developer can remove accessory dynamically from outside event
IhcPlatform.prototype.removeAccessory = function() {
  this.log("Remove Accessory");
  this.api.unregisterPlatformAccessories("homebridge-ihc", "IhcPlatform", this.accessories);

  this.accessories = [];
}
