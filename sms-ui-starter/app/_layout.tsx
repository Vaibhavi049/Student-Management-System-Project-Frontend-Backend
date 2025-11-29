import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import {Slot, Stack} from 'expo-router'
const _layout = () => {
  return (
    // <View style={styles.rootLayout}>
    //     <Text style={styles.myText}>Header</Text>
    //     <Slot/>
    //   <Text style={styles.myText}>Footer</Text>
    // </View>
        <Stack>
            <Stack.Screen
             name="index" 
             options={{ 
                title: 'Home'
                }}
             />
            <Stack.Screen 
                name="about" 
                options={{
                    title:'ABOUT US',
                    headerShown:false
                    }}
            />
            <Stack.Screen 
                name="counter" 
                options={{
                    title:'counter',
                    }}
            />
        </Stack>
  )
}

export default _layout

const styles = StyleSheet.create({
    rootLayout:{
        // flex:1
        justifyContent:"center",
        alignItems:"center"
    },
    myText:{
        fontSize:30
    }
})