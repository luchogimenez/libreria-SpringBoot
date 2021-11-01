import { BrowserRouter as Router, Route, Switch} from "react-router-dom"
import Navbar from "../components/Navbar"
import AutorForm from "../pages/AutorForm"
import HomePages from "../pages/HomePages"
import NotFoundPages from "../pages/NotFoundPages"
import EditorialLista from "../pages/EditorialLista"
import EditorialForm from "../pages/EditorialForm"
import LibrosLista from "../pages/LibrosLista"
import LibrosForm from "../pages/LibrosForm"
import AutorLista from "../pages/AutorLista"
import AutorEditar from "../pages/AutorEditar"

export default function AppRouter() {

    return (
        <Router>
            <Navbar/>
            <Switch>
                <Route excact path="/autores/mostrar" component={AutorLista} />
                <Route excact path="/autores/crear" component={AutorForm} />
                <Route excact path="/autores/editarAutor/:id" component={AutorEditar} />
                <Route excact path="/editoriales/mostrar" component={EditorialLista} />
                <Route excact path="/editoriales/crear" component={EditorialForm} />
                <Route excact path="/libros/mostrar" component={LibrosLista} />
                <Route excact path="/libros/crear" component={LibrosForm} />
                <Route excact path="/" component={HomePages} />
                <Route path="*" component={NotFoundPages} />
            </Switch>
        </Router>
    )
}
