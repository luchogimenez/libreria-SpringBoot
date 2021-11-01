import { useEffect, useState } from "react";
import { Table } from "react-bootstrap";
import { Link } from "react-router-dom";
export default function AutorPages() {
  const [autores, setAutores] = useState([]);
  
  useEffect(() => {
    fetch("http://localhost:8080/autores/lista")
      .then((data) => data.json())
      .then((data) => setAutores(data))
      .catch((e) => console.log(e));
  },[autores]);

  const changeAlta = (aut) => {
    var url = `http://localhost:8080/autores/changeAlta`;
    //var data = { username: "example" };
    fetch(url, {
      method: "POST", // or 'PUT'
      body: JSON.stringify(aut), // data can be `string` or {object}!
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => res.json())
      .catch((error) => console.error("Error:", error))
      .then((response) => console.log("Success:", response));

  };
  return (
    <div>
      <h1>Autores</h1>
      <div>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Id</th>
              <th>Nombre</th>
              <th>Habilitado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {autores.map((aut) => (
              <tr key ={aut.id}>
                <td>{aut.id}</td>
                <td>{aut.nombre}</td>
                <td>{aut.alta.toString()}</td>
                <td>
                  <Link to={`/autores/editar/${aut.id}`}>
                    <button type="button" className="btn btn-secondary">
                      Editar
                    </button>
                  </Link>

                  <button
                    type="button"
                    className="btn btn-danger"
                    onClick={() => changeAlta(aut)}
                  >
                    Deshabilitar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
}