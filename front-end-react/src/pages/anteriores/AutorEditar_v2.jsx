import { Table, Button } from "react-bootstrap";
import { useParams } from "react-router";
import { useState } from "react";
import { useHistory } from "react-router-dom";

export default function AutorEditar() {
  const { autor } = useParams();
  const autorJSON = JSON.parse(autor);
  const [nombre, setNombre] = useState({ nombre: autorJSON.nombre });

  let history = useHistory();

  const handleChange = (e) => {
    setNombre({ ...nombre, [e.target.name]: e.target.value });
  };

  const guardar = (e) => {
    e.preventDefault();
    var url = `http://localhost:8080/autores/editar/${autorJSON.id}`;
    //var data = { username: "example" };

    fetch(url, {
      method: "PUT", // or 'PUT'
      body: JSON.stringify(nombre), // data can be `string` or {object}!
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => res.json())
      .catch((error) => console.error("Error:", error))
      .then((response) => console.log("Success:", response));

      history.push("/autores")
  };


  return (
    <div>
      <form onSubmit={guardar}>
        <Table  striped bordered hover>
          <thead>
            <tr>
              <th>Id</th>
              <th>Nombre</th>
              <th>Habilitado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{autorJSON.id}</td>
              <td>
                <input
                  name="nombre"
                  type="text"
                  defaultValue={autorJSON.nombre}
                  onChange={handleChange}
                />
              </td>
              <td>{autorJSON.alta.toString()}</td>
              <td>
                <Button type="submit" className="btn-secondary">
                  Guardar
                </Button>
              </td>
            </tr>
          </tbody>
        </Table>
      </form>
    </div>
  );
}
