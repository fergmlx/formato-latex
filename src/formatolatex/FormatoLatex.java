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

public final class FormatoLatex {
    
    public FormatoLatex() {
    }
    
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

    public static JLabel crearMathTex(String formulaLatex, float textSize) {
        JLabel label = new JLabel();
        establecerMathTex(label, formulaLatex, textSize);
        return label;
    }
    
    public static JLabel crearTex(String text, float textSize) {
        JLabel label = new JLabel();
        establecerTex(label, text, textSize);
        return label;
    }

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
