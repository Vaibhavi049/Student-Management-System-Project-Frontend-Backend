import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { Link } from 'expo-router'
const about = () => {
  return (
    <View>
      <Text style={styles.textstyle}>About Us</Text>
            <Link href="/">HOME</Link>
    </View>
  )
}

export default about

const styles = StyleSheet.create({
    textstyle:{
        fontSize:30,
        color:"red",
        justifyContent:"center"
    }
})