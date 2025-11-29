import { View, Text, StyleSheet } from "react-native";
import { Link } from "expo-router";

export default function Index() {
  return (
    <View style={styles.container}>
      <Text>Welcome to Expo Router </Text>
      <Link href="/about">Go to About</Link>
      <Link href="/contact">Go to Contact</Link>
      {/* <Link href="/counter">Counter</Link> */}
      <Link href="/login">Login</Link>
      
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#fff",
  },
});
