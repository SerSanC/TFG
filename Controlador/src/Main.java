import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class Main {

    private JSONObject ubicacionVehiculoMovimiento = null;
    
    void setDistanciaVehiculo(JSONObject localizacion) {
        ubicacionVehiculoMovimiento = localizacion;
        JSONObject localizacionTLC = new JSONObject();
        localizacionTLC.put("lat",  39.203);
        localizacionTLC.put("lng", -0.390796);

        Calculo("http://tambori.dsic.upv.es:10050/tlc", localizacionTLC);
    }
 

    public JSONObject getUbicacionVehiculoMovimiento() {
        return ubicacionVehiculoMovimiento;
    }

    public static void main(String[] args) {
        Subscriber client = new Subscriber();
        client.start(); //hacemos la conexión      
    }
    
    /**
     * Metodo que se utiliza para realizar las acciones pertinentes en base
     * a la distancia entre el recurso móvil y el recurso IoT
     *
     * @param urlTLC
     * @param localizacionTLC
     */
    public void Calculo(String urlTLC, JSONObject localizacionTLC) {
        double latitudVehiculo = Double.parseDouble(ubicacionVehiculoMovimiento.get("lat").toString());
        double longitudVehiculo = Double.parseDouble(ubicacionVehiculoMovimiento.get("lng").toString());
        double latitudTLC = Double.parseDouble(localizacionTLC.get("lat").toString());
        double longitudTLC = Double.parseDouble(localizacionTLC.get("lng").toString());

        System.out.println("\nLatitudVehiculo: " + latitudVehiculo);
        System.out.println("LongitudVehiculo: " + longitudVehiculo);
        System.out.println("LatitudTLC: " + latitudTLC);
        System.out.println("LongitudTLC: " + longitudTLC);
        
        double distancia = distanciaCoord(latitudVehiculo, longitudVehiculo, latitudTLC, longitudTLC);

        System.out.println("Distancia: " + distanciaCoord(latitudVehiculo, longitudVehiculo, latitudTLC, longitudTLC) + "\n");

        if (distancia <= 300) {
            try {
                String payload = "{action:emergency}";
                ClientResource resource = new ClientResource("http://tambori.dsic.upv.es:10050/tlc");
                System.out.println(new JSONObject(resource.get().getText()));
                resource.put(payload);
                String response;
                try {
                    response = resource.get().getText();
                    if (response != null) {
                        //System.out.println(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String payload = "{action:init}";
            ClientResource resource = new ClientResource("http://tambori.dsic.upv.es:10050/tlc");
            resource.put(payload);
            String response;
            try {
                response = resource.get().getText();
                if (response != null) {
                    System.out.println(response);
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Metodo para calcular la distancia entre dos coordenadas
     *
     * @param lat1
     * @param lat2
     * @param lng1
     * @param lng2
     * @return double
     */
    public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {

        double radioTierra = 6371;//en kilómetros  
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;

        return distancia * 1000;
    }


}
