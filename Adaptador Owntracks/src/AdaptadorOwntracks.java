/**
 *
 * @author sergisanz
 */
public class AdaptadorOwntracks {
    static Subscriber cliente;
    String cadena;
    

    /**
     * Se inicializa el objeto Subscriber cliente 
     * @param args 
     */
    public static void main(String[] args) {
        // TODO code application logic here
        cliente = new Subscriber();
        cliente.start(); //hacemos la conexión

    }
    
}
