package errores;

public class ErrorServicio extends Exception {

    
    public ErrorServicio() {
    }

    
    public ErrorServicio(String msg) {
        super(msg);
    }
}
