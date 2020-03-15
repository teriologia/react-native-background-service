import React, {Component} from 'react';
import {
  StyleSheet, Text, View, TouchableOpacity, AsyncStorage, NativeModules, ScrollView
} from 'react-native';
import { connect } from 'react-redux';
import Heartbeat from './Heartbeat';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'white',
  },
  view: {
    flex: 0.5,
    justifyContent: 'center',
    alignItems: 'center',
  },
  button: {
    backgroundColor: 'gray',
    padding: 10,
    margin: 10,
  },
  text: {
    fontSize: 20,
    color: 'white',
  },
});

class App extends Component {
  constructor(props){
    super(props)
    this.state={
      test: 1
    }
  }
  show = (e) => {
    console.log(e)
  }

  render() {
    return (
      <ScrollView>
      <View style={styles.container}>
        <View style={styles.view}>
          <TouchableOpacity style={styles.button} onPress={() => Heartbeat.startService(2000)}>
            <Text style={styles.instructions}>Start</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => Heartbeat.stopService()}>
            <Text style={styles.instructions}>Stop</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={async() => {
            this.setState({test: this.state.test + 1})
            await AsyncStorage.setItem(`denemeCache${this.state.test}`, "Deneme cache")
          }}>
            <Text style={styles.instructions}>kaydet</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={async() => {
            const data = await AsyncStorage.getItem("denemeCache1")
            this.show(data)
          }}>
            <Text style={styles.instructions}>show</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => NativeModules.Heartbeat.clearCache((e) => console.log(e))}>
            <Text style={styles.instructions}>clear</Text>
          </TouchableOpacity>
          <Text>{this.state.test}</Text>

          <TouchableOpacity style={styles.button} onPress={() => Heartbeat.getPermission(e => console.log(e))}>
            <Text style={styles.instructions}>getlocationInfo</Text>
          </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={() => Heartbeat.openOnOffGpsSettings()}>
              <Text style={styles.instructions}>Open Settings</Text>
            </TouchableOpacity>

        </View>
      </View>
      </ScrollView>
    )
  }
}


const mapStateToProps = store => ({
  heartBeat: store.App.heartBeat,
});

export default connect(mapStateToProps)(App);
