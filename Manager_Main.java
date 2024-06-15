import com.example.ubike.DatabaseHelper;
import com.example.ubike.UBikeMaintenanceGUI;

public class Manager_Main {
    public static void main(String[] args) {


        // 啟動 GUI
        launchGUI();
    }


    private static void launchGUI() {
        // 使用 SwingUtilities.invokeLater 來確保 GUI 創建和更新在事件調度線程 (EDT) 中執行
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UBikeMaintenanceGUI gui = new UBikeMaintenanceGUI();
                gui.setVisible(true);
            }
        });
    }
}

