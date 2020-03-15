import { AppRegistry, StyleSheet, View, TouchableOpacity, Text, NativeModules } from 'react-native';
import React from 'react';
import { Provider } from 'react-redux';
import App from './App';
import { name as appName } from './app.json';
import { setHeartBeat, store } from './store';
let permissio;
const MyHeadlessTask = async (e) => {
  console.log(e);
  console.log('HearthBeat Testing!');
  permissio=e.isLocation;
};

const showModal = () => {
  while (!permissio) {
    return (
      <View style={{...StyleSheet.absoluteFillObject, backgroundColor: "rgba(0,0,0,0.2)", zIndex: 99, justifyContent: 'center', alignItems: 'center',}}>
        <View style={{width: "75%", height: "75%", backgroundColor: "#FFF", justifyContent: 'center', alignItems: 'center',}} >
          <Text style={{fontSize: 18, color: "#000", fontWeight: "bold"}}>location permission required!</Text>
          <TouchableOpacity onPress={() =>  NativeModules.Heartbeat.getPermission(e => console.log(e))} style={{width: 100, height: 50, backgroundColor: "#0000b4", justifyContent: 'center', alignItems: 'center',}}>
            <Text style={{color: "#FFF"}} >İzin Ver</Text>
          </TouchableOpacity>
        </View>
      </View>
    )
  }
}

const RNRedux = () => (
  <Provider store={store}>
    <App />
    {showModal()}
  </Provider>
);


AppRegistry.registerHeadlessTask('Heartbeat', () => MyHeadlessTask);
AppRegistry.registerComponent(appName, () => RNRedux);
