import React, { useEffect, useState } from "react";
import axios from "axios";

function App() {
  const [message, setMessage] = useState("");

  useEffect(() => {
    axios.get("http://localhost:8080/api/mensaje")
      .then(response => setMessage(response.data))
      .catch(error => console.error("Error:", error));
  }, []);

  return (
    <div>
      <h1>React + Spring Boot</h1>
      <p>Mensaje del backend: {message}</p>
    </div>
  );
}

export default App;