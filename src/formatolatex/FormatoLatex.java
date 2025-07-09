package formatolatex;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase de utilidad para renderizar fórmulas LaTeX en componentes Swing o guardarlas como imágenes.
 * Proporciona métodos para convertir expresiones matemáticas en formato LaTeX a representaciones visuales.
 *
 * @author Fer
 * @author Jonathan
 */
public final class FormatoLatex {
    
    /**
     * Constructor por defecto.
     */
    public FormatoLatex() {
    }
    
    /**
     * Renderiza una fórmula LaTeX como imagen PNG y la guarda en la ruta especificada.
     * Utiliza colores predeterminados: fondo blanco y texto negro.
     *
     * @param formulaLatex La expresión LaTeX que se desea renderizar
     * @param texSize El tamaño del texto
     * @param ruta La ruta completa donde se guardará el archivo PNG
     */
    public static void renderizarFormula(String formulaLatex, float texSize, String ruta) {
        try {
            TeXFormula formula = new TeXFormula(formulaLatex);
            TeXIcon icon = formula.new TeXIconBuilder().setStyle(TeXConstants.STYLE_DISPLAY).setSize(texSize).build();
            icon.setInsets(new Insets(5, 5, 5, 5));
            
            BufferedImage image = new BufferedImage(icon.getIconWidth(), 
                                                    icon.getIconHeight(), 
                                                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
            JLabel etiqueta = new JLabel();
            etiqueta.setForeground(Color.BLACK);
            icon.paintIcon(etiqueta, g2, 0, 0);
            g2.dispose();

            // Guardar imagen
            File salida = new File(ruta);
            if (salida.exists()) {
                int respuesta = JOptionPane.showConfirmDialog(null,
                                              "El archivo ya existe. ¿Desea sobrescribirlo?", 
                                              "Sobrescribir", 
                                              JOptionPane.YES_NO_OPTION, 
                                              JOptionPane.WARNING_MESSAGE);
                if (respuesta != JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Renderización cancelada.");
                    return;
                }
            }
            ImageIO.write(image, "PNG", salida);
            
            System.out.println("Formula renderizada en: " + salida.getAbsolutePath());
            
        } catch (IOException e) {
            System.err.println("Error al renderizar formula: " + e.getMessage());
        }
    }
    
    /**
     * Renderiza una fórmula LaTeX como imagen PNG y la guarda en la ruta especificada,
     * permitiendo personalizar los colores de fondo y texto.
     *
     * @param formulaLatex La expresión LaTeX que se desea renderizar
     * @param texSize El tamaño del texto
     * @param ruta La ruta completa donde se guardará el archivo PNG
     * @param background El color de fondo de la imagen
     * @param foreground El color del texto de la fórmula
     */
    public static void renderizarFormula(String formulaLatex, float texSize, String ruta, Color background, Color foreground) {
        try {
            TeXFormula formula = new TeXFormula(formulaLatex);
            TeXIcon icon = formula.new TeXIconBuilder().setStyle(TeXConstants.STYLE_DISPLAY).setSize(texSize).build();
            icon.setInsets(new Insets(5, 5, 5, 5));
            
            BufferedImage image = new BufferedImage(icon.getIconWidth(), 
                                                    icon.getIconHeight(), 
                                                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            g2.setColor(background);
            g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
            JLabel etiqueta = new JLabel();
            etiqueta.setForeground(foreground);
            icon.paintIcon(etiqueta, g2, 0, 0);
            g2.dispose();

            // Guardar imagen
            File salida = new File(ruta);
            if (salida.exists()) {
                int respuesta = JOptionPane.showConfirmDialog(null,
                                              "El archivo ya existe. ¿Desea sobrescribirlo?", 
                                              "Sobrescribir", 
                                              JOptionPane.YES_NO_OPTION, 
                                              JOptionPane.WARNING_MESSAGE);
                if (respuesta != JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Renderización cancelada.");
                    return;
                }
            }
            ImageIO.write(image, "PNG", salida);
            
            System.out.println("Formula renderizada en: " + salida.getAbsolutePath());
            
        } catch (IOException e) {
            System.err.println("Error al renderizar formula: " + e.getMessage());
        }
    }

    /**
     * Establece una fórmula matemática LaTeX como icono de un JLabel.
     * Reemplaza el texto del JLabel con la representación visual de la fórmula.
     *
     * @param label El JLabel donde se mostrará la fórmula
     * @param formulaLatex La expresión LaTeX que se desea mostrar
     * @param textSize El tamaño del texto
     */
    public static void establecerMathTex(JLabel label, String formulaLatex, float textSize) {
        try {
            TeXFormula formula = new TeXFormula(formulaLatex);
            TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, textSize);

            label.setIcon(icon);
            label.setText("");

        } catch (Exception e) {
            System.err.println("Error al establecer fórmula en JLabel: " + e.getMessage());
        }
    }
    
    /**
     * Establece texto normal en formato LaTeX como icono de un JLabel.
     * Utiliza el comando \text{} de LaTeX para formatear el texto.
     *
     * @param label El JLabel donde se mostrará el texto
     * @param tex El texto que se desea formatear y mostrar
     * @param texSize El tamaño del texto
     */
    public static void establecerTex(JLabel label, String tex, float texSize) {
        try {
            String latex = "\\text{";
            latex += tex + "}";
            TeXFormula text = new TeXFormula(latex);
            TeXIcon icon = text.new TeXIconBuilder()
                    .setStyle(TeXConstants.STYLE_DISPLAY)
                    .setSize(texSize)
                    .setWidth(TeXConstants.UNIT_PIXEL, 356f, TeXConstants.ALIGN_CENTER)
                    .setIsMaxWidth(true).setInterLineSpacing(TeXConstants.UNIT_PIXEL, 10f)
                    .build();
            icon.setInsets(new Insets(5, 5, 5, 5));

            label.setIcon(icon);
            label.setText("");

        } catch (Exception e) {
            System.err.println("Error al establecer fórmula en JLabel: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo JLabel con una fórmula matemática LaTeX.
     *
     * @param formulaLatex La expresión LaTeX que se desea mostrar
     * @param textSize El tamaño del texto
     * @return Un JLabel con la fórmula LaTeX renderizada como icono
     */
    public static JLabel crearMathTex(String formulaLatex, float textSize) {
        JLabel label = new JLabel();
        establecerMathTex(label, formulaLatex, textSize);
        return label;
    }
    
    /**
     * Crea un nuevo JLabel con texto normal formateado en LaTeX.
     *
     * @param text El texto que se desea formatear y mostrar
     * @param textSize El tamaño del texto
     * @return Un JLabel con el texto formateado en LaTeX como icono
     */
    public static JLabel crearTex(String text, float textSize) {
        JLabel label = new JLabel();
        establecerTex(label, text, textSize);
        return label;
    }

    /**
     * Muestra una fórmula matemática LaTeX en una ventana independiente.
     *
     * @param formulaLatex La expresión LaTeX que se desea mostrar
     * @param textSize El tamaño del texto
     */
    public static void mostrarFormulaEnVentana(String formulaLatex, float textSize) {
        JFrame ventana = new JFrame("Formula latex");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setSize(400, 200);
        ventana.setLocationRelativeTo(null);

        JLabel etiqueta = crearMathTex(formulaLatex, textSize);
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        etiqueta.setVerticalAlignment(SwingConstants.CENTER);
        
        ventana.add(etiqueta);
        ventana.setVisible(true);
    }
    
    /**
     * Muestra texto normal formateado en LaTeX en una ventana independiente.
     *
     * @param text El texto que se desea formatear y mostrar
     * @param textSize El tamaño del texto
     */
    public static void mostrarTexEnVentana(String text, float textSize) {
        JFrame ventana = new JFrame("Formula latex");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setSize(400, 200);
        ventana.setLocationRelativeTo(null);

        JLabel etiqueta = crearTex(text, textSize);
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        etiqueta.setVerticalAlignment(SwingConstants.CENTER);
        
        ventana.add(etiqueta);
        ventana.setVisible(true);
    }
 
    /**
     * Muestra una matriz en formato LaTeX en una ventana independiente.
     * Utiliza el entorno bmatrix de LaTeX para representar la matriz.
     *
     * @param matriz Una matriz bidimensional de cadenas, donde cada elemento es una fila de la matriz
     * @param tamaño El tamaño del texto
     */
    public static void mostrarMatrizVentana(String[][] matriz, float tamaño) {
        try {
            StringBuilder latex = new StringBuilder();
            latex.append("\\begin{bmatrix}");

            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    latex.append(matriz[i][j]);
                    if (j < matriz[i].length - 1) {
                        latex.append(" & ");
                    }
                }
                if (i < matriz.length - 1) {
                    latex.append(" \\\\ ");
                }
            }

            latex.append("\\end{bmatrix}");

            mostrarFormulaEnVentana(latex.toString(), tamaño);

        } catch (Exception e) {
            System.err.println("Error al mostrar la matriz: " + e.getMessage());
        }
    }
}