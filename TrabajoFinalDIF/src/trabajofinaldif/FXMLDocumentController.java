package trabajofinaldif;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * @author sergisanz
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Text puertaSimple_txt;
    @FXML
    private JFXToggleButton puertaSimple_controller;
    @FXML
    private JFXToggleButton puertaDoble_controller;
    @FXML
    private Text info1;
    @FXML
    private Text info2;
    @FXML
    private JFXTextField altura;
    @FXML
    private JFXTextField anchura;
    @FXML
    private JFXTextField profundidad;
    @FXML
    private JFXButton buttonGuardar;
    @FXML
    private JFXButton buttonVisualizar;
    @FXML
    private Text info3;
    private boolean t1, t2, t3, guardar = false, visualizar = false;
    private Window stage;
    private FileChooser f;
    File file;
    double x, y, z;
    private ImageView imagen = null;
    PrintWriter salida = null;
    int calc;
    @FXML
    private Text puertaDoble_txt1;
    @FXML
    private Text patas_txt;
    @FXML
    private JFXToggleButton patas_Controller;
    private boolean patasDato;
    @FXML
    private ImageView im1;
    @FXML
    private ImageView im2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FileInputStream input = null;
        FileInputStream input2 = null;
        try {
            input = new FileInputStream("../TrabajoFinalDIF/img/armario1puerta.jpg");
            input2 = new FileInputStream("../TrabajoFinalDIF/img/armario2puertas.jpg");

            im1.setImage(new Image(input));
            im2.setImage(new Image(input2));
            x = 0;
            y = 0;
            z = 0;
            patasDato = false;
            info1.setText(" ");
            info2.setText(" ");
            info3.setText(" ");
            buttonVisualizar.setVisible(false);
            buttonGuardar.setVisible(false);

            patas_txt.setVisible(false);
            patas_Controller.setVisible(false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void PuertaSimpleAction(ActionEvent event) {
        if (puertaSimple_controller.isSelected()) {
            puertaDoble_controller.setSelected(false);
            buttonGuardar.setVisible(true);
            buttonVisualizar.setVisible(true);
            patas_txt.setVisible(true);
            patas_Controller.setVisible(true);
        } else if (!puertaSimple_controller.isSelected()) {
            buttonGuardar.setVisible(false);
            buttonVisualizar.setVisible(false);
            patas_txt.setVisible(false);
            patas_Controller.setVisible(false);
        }
    }

    @FXML
    private void PuertaDobleAction(ActionEvent event) {
        if (puertaDoble_controller.isSelected()) {
            puertaSimple_controller.setSelected(false);
            buttonGuardar.setVisible(true);
            buttonVisualizar.setVisible(true);
            patas_txt.setVisible(true);
            patas_Controller.setVisible(true);
        } else if (!puertaDoble_controller.isSelected()) {
            buttonGuardar.setVisible(false);
            buttonVisualizar.setVisible(false);
            patas_txt.setVisible(false);
            patas_Controller.setVisible(false);
        }
    }

    @FXML
    private void CheckAltura(ActionEvent event) {
        Comprobador(altura);
    }

    @FXML
    private void CheckAnchura(ActionEvent event) {
        Comprobador(anchura);
    }

    @FXML
    private void CheckProfundidad(ActionEvent event) {
        Comprobador(profundidad);
    }

    @FXML
    private void PatasController(ActionEvent event) {
        if (patas_Controller.isSelected()) {
            patasDato = true;
        } else {
            patasDato = false;
        }
    }

    /**
     * Metodo para guardar el archivo STL
     */
    @FXML
    private void Guardar(ActionEvent event) {
        try {
            stage = null;
            f = new FileChooser();
            file = f.showSaveDialog(stage);
            int largo = file.getName().length();
            System.out.println(" " + largo);
            if (file.getName().substring(largo - 4).equals(".stl")) {
                if (puertaSimple_controller.isSelected()) {
                    EscribirSTLUnaPuerta();
                    info1.setText("Archivo creado!!");
                }
                if (puertaDoble_controller.isSelected()) {
                    EscribirSTLDosPuertas();
                    info1.setText("Archivo creado!!");
                }

            } else {
                info1.setText("Utiliza .stl");
            }
        } catch (Exception e) {
        }
    }

    /**
     * Este metodo realiza la previsualización del archivo STL abriendo el
     * navegador web
     */
    @FXML
    private void Visualizar(ActionEvent event) {
        URL url = null;
        try {
            url = new URL("https://www.viewstl.com");
            Desktop.getDesktop().browse(url.toURI());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Metodo que realiza una comprobación de que se cumple las restricciones en
     * la introducción de datos del mueble
     */
    private void Comprobador(JFXTextField s) {
        if (!isNumeric(s.getText())) {

            buttonGuardar.setVisible(false);
            if (s.getId().equals("altura")) {
                t1 = true;
                info1.setText("Introduce un valor numerico en Altura");
            }
            if (s.getId().equals("anchura")) {
                t2 = true;
                info2.setText("Introduce un valor numerico en Anchura");
            }
            if (s.getId().equals("profundidad")) {
                t3 = true;
                info3.setText("Introduce un valor numerico en Profundidad");
            }
        } else {
            try {
                if (s.getId().equals("altura")) {
                    calc = Integer.parseInt(s.getText());

                    if (calc <= 300) {
                        t1 = false;
                        info1.setText(" ");
                    } else {
                        t1 = true;
                        info1.setText("Recuerde que la altura maxima es de 300cm");
                    }
                }
                if (s.getId().equals("anchura")) {
                    calc = Integer.parseInt(s.getText());
                    if (calc <= 300) {
                        t2 = false;
                        info2.setText(" ");
                    } else {
                        t2 = true;
                        info2.setText("Recuerde que la anchura maxima es de 300cm");
                    }
                }
                if (s.getId().equals("profundidad")) {
                    calc = Integer.parseInt(s.getText());

                    if (calc <= 300) {
                        t3 = false;
                        info3.setText(" ");
                    } else {
                        t3 = true;
                        info3.setText("Recuerde que la altura maxima es de 300cm");
                    }
                }
            } catch (NumberFormatException e) {
            }
        }

    }

    /**
     * Este metodo comprueba si una cadena de texto es un numero o no
     * @param cadena
     * @return boolean
     */
    public static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    /**
     * Metodo para implementar el mueble con una sola puerta, ademas hace una
     * llamada al metodo Cubo, y en caso de ser necesario a todos los metodos de
     * Patas
     */
    private void EscribirSTLUnaPuerta() {
        try {

            x = Double.parseDouble(anchura.getText());
            z = Double.parseDouble(altura.getText());
            y = Double.parseDouble(profundidad.getText());

            salida = new PrintWriter(file.getAbsolutePath()); //se crea el fichero
            salida.println("solid " + file.getName());
            Cubo(salida);
            PomoPuertaIzq(salida);
            if (patasDato == true) {
                PataIzq(salida);
                PataDer(salida);
                PataIzqTrasera(salida);
                PataDerTrasera(salida);
            }
            salida.println("endsolid " + file.getName());

            salida.flush();
        } catch (FileNotFoundException | NumberFormatException e) {
            System.out.println(e.getMessage());
        } finally {
            salida.close();
        }
    }

    /**
     * Metodo para implementar el mueble con dos puertas, ademas hace una
     * llamada al metodo CuboParteIzq,CuboParteDer, PomoPuertaIzq,PomoPuertaDer
     * y si se ha seleccionado en el Toggle, se pondran las patas
     */
    private void EscribirSTLDosPuertas() {
        try {
            System.out.println("Valor de x antes : " + x + "\n Valor de i antes: " + y + "\n Valor de z antes:" + z);

            x = Double.parseDouble(anchura.getText());
            z = Double.parseDouble(altura.getText());
            y = Double.parseDouble(profundidad.getText());

            System.out.println("Valor de x : " + x + "\n Valor de y : " + y + "\n Valor de z:" + z);

            salida = new PrintWriter(file.getAbsolutePath()); //se crea el fichero
            PrintWriter p = new PrintWriter("..//TrabajoFinalDIF/copy/puertaDoble.stl");
            p.println("solid " + file.getName());
            CuboParteIzq(p);
            CuboParteDer(p);
            PomoIzqAdap(p);
            PomoPuertaDer(p);
            p.println("endsolid " + file.getName());
            //Cubo();

            salida.println("solid " + file.getName());
            CuboParteIzq(salida);
            CuboParteDer(salida);
            PomoIzqAdap(salida);
            PomoPuertaDer(salida);
            if (patasDato == true) {
                PataIzq(salida);
                PataDer(salida);
                PataIzqTrasera(salida);
                PataDerTrasera(salida);
            }
            salida.println("endsolid " + file.getName());

            p.flush();
            salida.flush();
        } catch (FileNotFoundException | NumberFormatException e) {
            System.out.println(e.getMessage());
        } finally {
            salida.close();
        }
    }

    public void Cubo(PrintWriter dato) {
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + "0" + " " + z + " ");
        dato.println("    vertex " + "0" + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + x + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + y + " " + z + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + x + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + x + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + z + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + z + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + "0" + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + y + " " + z + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + "0" + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");

    }

    public void PomoPuertaIzq(PrintWriter dato) {
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "5" + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "10" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "5" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "5" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "10" + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "10" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "10" + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
    }

    public void PomoPuertaDer(PrintWriter dato) {
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 10) + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 5) + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 10) + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 5) + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 10) + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 10) + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 10) + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 10) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 10) + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 10) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 10) + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 10) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 10) + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 5) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 5) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 10) + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 10) + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 5) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 10) + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 10) + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 10) + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 10) + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 10) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 10) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 5) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + (y + 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + (x - 5) + " " + (y + 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
    }

    public void CuboParteIzq(PrintWriter dato) {
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + "0" + " " + z + " ");
        dato.println("    vertex " + "0" + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + (y - 5) + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + z + " ");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + (y - 5) + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + y + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + z + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + z + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + z + " ");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + z + " ");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + z + " ");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + z + " ");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + z + " ");
        dato.println("    vertex " + "0" + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + y + " " + z + " ");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + z + " ");
        dato.println("    vertex " + "0" + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");

    }

    public void CuboParteDer(PrintWriter dato) {
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + "0" + " ");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + z + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + "0" + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + y + " " + z + " ");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("    vertex " + x + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + x + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + z + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x / 2) + " " + "0" + " " + z + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + y + " " + z + " ");
        dato.println("    vertex " + x + " " + "0" + " " + z + " ");
        dato.println("    vertex " + (x / 2) + " " + y + " " + z + " ");
        dato.println("  endloop");
        dato.println("endfacet");

    }

    public void PomoIzqAdap(PrintWriter dato) {
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + (y - 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "5" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "5" + " " + y + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "5" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "10" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + (y - 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "10" + " " + (y - 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + (y - 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + (y - 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "5" + " " + y + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + (y - 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "10" + " " + (y - 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + (y - 5) + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + (z / 2) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "10" + " " + y + " " + (z / 2) + " ");
        dato.println("    vertex " + "10" + " " + (y - 5) + " " + ((z / 2) - 10) + " ");
        dato.println("    vertex " + "10" + " " + y + " " + ((z / 2) - 10) + " ");
        dato.println("  endloop");
        dato.println("endfacet");
    }

    private void PataIzq(PrintWriter dato) {
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + y + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + y + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + y + " " + "-10" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + y + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + y + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + y + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + (y - 5) + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

    }

    private void PataDer(PrintWriter dato) {
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + (y - 5) + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + y + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + (y - 5) + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + y + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + (y - 5) + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + y + " " + "0" + " ");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + y + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + x + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + (y) + " " + "0" + " ");
        dato.println("    vertex " + x + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + y + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + x + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + x + " " + (y - 5) + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + (y - 5) + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + (y - 5) + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + x + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + y + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + y + " " + "0" + " ");
        dato.println("    vertex " + x + " " + (y - 5) + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + (y - 5) + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

    }

    private void PataIzqTrasera(PrintWriter dato) {
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + "0" + " " + "5" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + "0" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + "5" + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + "0" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + "0" + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + "0" + " " + "5" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "5" + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + "5" + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + "5" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + "5" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + "5" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "5" + " " + "5" + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + "0" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + "0" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + "5" + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + "5" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + "0" + " " + "5" + " " + "0" + " ");
        dato.println("    vertex " + "0" + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + "5" + " " + "5" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

    }

    private void PataDerTrasera(PrintWriter dato) {
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + "5" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 -1");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + "0" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + "5" + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + "0" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal -1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + "5" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + "5" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "5" + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + "5" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + (x - 5) + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + "5" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + "5" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 1 0 0");
        dato.println("  outer loop");
        dato.println("    vertex " + x + " " + "5" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "5" + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + "0" + " " + "-10" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 -1 0");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + "-10" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + (x - 5) + " " + "5" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");
        dato.println("facet normal 0 0 1");
        dato.println("  outer loop");
        dato.println("    vertex " + (x - 5) + " " + "5" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "0" + " " + "0" + " ");
        dato.println("    vertex " + x + " " + "5" + " " + "0" + " ");
        dato.println("  endloop");
        dato.println("endfacet");

    }

}
