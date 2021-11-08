import React, { useEffect, useState } from "react";

export default function LibrosLista() {
  const axios = require("axios");
  const [libros, setLibros] = useState([]);

  const obtenerListaLibros = async () => {
    await axios.get("http://localhost:8080/libros").then(function (response) {
      setLibros(response.data);
    });
  };
  useEffect(() => {
    obtenerListaLibros();
  }, [libros]);
  return (
    <div>
      <h1>Libros</h1>
      <table>
        <thead>
          <tr>
            <th>titulo</th>
          </tr>
        </thead>
        <tbody>
          {libros.map((libro) => (
            <tr key={libro.id}>
              <td>{libro.titulo}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
