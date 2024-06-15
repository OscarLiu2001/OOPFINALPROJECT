import com.example.ubike.DatabaseHelper;
import com.example.ubike.UBikeMaintenanceGUI;

public class Manager_Main {
    public static void main(String[] args) {


        // �Ұ� GUI
        launchGUI();
    }


    private static void launchGUI() {
        // �ϥ� SwingUtilities.invokeLater �ӽT�O GUI �ЫةM��s�b�ƥ�ի׽u�{ (EDT) ������
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UBikeMaintenanceGUI gui = new UBikeMaintenanceGUI();
                gui.setVisible(true);
            }
        });
    }
}

