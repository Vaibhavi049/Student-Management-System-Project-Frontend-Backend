// import React, { useState, useEffect } from 'react';
// import { StyleSheet, Text, View, Button } from 'react-native';

// export default function CounterScreen() {
//   // state for count
//   const [count, setCount] = useState(0);

//   // side effect: log whenever count changes
//   useEffect(() => {
//     console.log('Count changed:', count);
//   }, [count]);

//   return (
//     <View style={styles.container}>
//       <Text style={styles.text}>Count: {count}</Text>

//       <View style={styles.buttons}>
//         <Button title="➕ Increment" onPress={() => setCount(count + 1)} />
//         <Button title="➖ Decrement" onPress={() => setCount(count - 1)} />
//       </View>
//     </View>
//   );
// }

// const styles = StyleSheet.create({
//   container: {
//     flex: 1,
//     justifyContent: 'center',
//     alignItems: 'center',
//     backgroundColor: '#f5f5f5',
//   },
//   text: {
//     fontSize: 24,
//     marginBottom: 20,
//   },
//   buttons: {
//     flexDirection: 'row',
//     gap: 10,
//   },
// });
import React, { useState } from "react";

export default function CounterApp() {
  // state for counter
  const [count, setCount] = useState(0);

  return (
    <div style={{ textAlign: "center", marginTop: "50px" }}>
      <h1>Counter: {count}</h1>
      <button 
        onClick={() => setCount(count + 1)} 
        style={{ marginRight: "10px", padding: "10px 20px" }}
      >
        Increment
      </button>
      <button 
        onClick={() => setCount(count - 1)} 
        style={{ padding: "10px 20px" }}
      >
        Decrement
      </button>
    </div>
  );
}