import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 */
public class GuiController implements ActionListener
{
    // Risk model
    private RiskGame model;

    // GUI View
    private GUI view;

    public GuiController(RiskGame m, GUI v)
    {
        this.model = m;
        this.view = v;
    }

    public void actionPerformed(ActionEvent e)
    {

    }
}
