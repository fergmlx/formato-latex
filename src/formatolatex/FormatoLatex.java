package formatolatex;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class FormatoLatex {

    public FormatoLatex() {
    }

    public static boolean renderizarFormula(String formulaLatex, float textSize, String nombreArchivo) {
        try {
            TeXFormula formula = new TeXFormula(formulaLatex);
            TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, textSize);
            BufferedImage image = new BufferedImage(icon.getIconWidth(),
                    icon.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = image.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
            g2.setColor(Color.BLACK);
            icon.paintIcon(null, g2, 0, 0);
            g2.dispose();

            // Guardar imagen
            ImageIO.write(image, "PNG", new File(nombreArchivo + ".png"));

            System.out.println("Formula renderizada: " + nombreArchivo + ".png");
            return true;

        } catch (Exception e) {
            System.err.println("Error al renderizar formula: " + e.getMessage());
            return false;
        }
    }

    public static boolean establecerMathTex(JLabel label, String formulaLatex, float tamaño) {
        try {
            TeXFormula formula = new TeXFormula(formulaLatex);
            TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, tamaño);

            label.setIcon(icon);
            label.setText("");

            return true;

        } catch (Exception e) {
            System.err.println("Error al establecer fórmula en JLabel: " + e.getMessage());
            return false;
        }
    }

    public static JLabel crearMathTex(String formulaLatex, float tamaño) {
        JLabel label = new JLabel();
        establecerMathTex(label, formulaLatex, tamaño);
        return label;
    }

    public static void mostrarFormulaEnVentana(String formulaLatex, float tamaño) {
        JFrame ventana = new JFrame("Formula latex");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setSize(400, 200);
        ventana.setLocationRelativeTo(null);

        JLabel etiqueta = crearMathTex(formulaLatex, tamaño);
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        etiqueta.setVerticalAlignment(SwingConstants.CENTER);
        
        ventana.add(etiqueta);
        ventana.setVisible(true);
    }
}
