import javax.swing.*;
import java.awt.*;

public interface GUI {
    Color cellColor1 = new Color(236, 238, 212);
    Color cellColor2 = new Color(116, 150, 84);
    Color selectedColor1 = new Color(246, 246, 105);
    Color selectedColor2 = new Color(186, 202, 43);

    JComponent render();
}
