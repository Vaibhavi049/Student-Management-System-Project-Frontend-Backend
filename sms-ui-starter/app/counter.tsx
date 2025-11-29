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
