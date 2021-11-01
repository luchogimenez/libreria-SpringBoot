import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <div >
      <div className="mt-4 dropdown">
        <div className="btn-group">
          <Link exact="true" to="/" className="btn">
            Home
          </Link>
        </div>

        <div className="btn-group dropdown">
          <div
            className="dropdown-toggle btn"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            Autores
          </div>
          <div className="dropdown-menu" aria-labelledby="navbarDropdown">
            <div className="dropdown-item">
              <Link exact="true" to="/autores/crear" className="btn">
                Crear
              </Link>
            </div>
            <div className="dropdown-item">
              <Link exact="true" to="/autores/mostrar" className="btn">
                Mostrar
              </Link>
            </div>
          </div>
        </div>
        <div className="btn-group dropdown">
          <div
            className="dropdown-toggle btn"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            Editoriales
          </div>
          <div className="dropdown-menu" aria-labelledby="navbarDropdown">
            <div className="dropdown-item">
              <Link
                exact="true"
                to="/editoriales/crear"
                className="btn"
              >
                Crear
              </Link>
            </div>
            <div className="dropdown-item">
              <Link
                exact="true"
                to="/editoriales/mostrar"
                className="btn"
              >
                Mostrar
              </Link>
            </div>
          </div>
        </div>
        <div className="btn-group dropdown">
          <div
            className="dropdown-toggle btn"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            Libros
          </div>
          <div className="dropdown-menu" aria-labelledby="navbarDropdown">
            <div className="dropdown-item">
              <Link exact="true" to="/libros/crear" className="btn">
                Crear
              </Link>
            </div>
            <div className="dropdown-item">
              <Link exact="true" to="/libros/mostrar" className="btn">
                Mostrar
              </Link>
            </div>
          </div>
        </div>
        <hr />
      </div>
    </div>
  );
}
