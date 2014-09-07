package com.convey;

import com.convey.GUI.MainFrame;
import com.convey.utils.ProcessRunner;

/**
 * @projectName TEDxGame
 * @package com.convey.GUI
 * @filename Main.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 04/09/2014 21:25:33
 */
public class Main {

    private static final String WAMP_PROCESS_ID = "wampmanager.exe";
    private static final String WAMP_PATH = "C:\\wamp\\wampmanager.exe";

    static {
        if (!new ProcessRunner().getProcState(WAMP_PROCESS_ID)) {
            new ProcessRunner().run(WAMP_PATH);
        }
    }

    public static void main(String args[]) {

//        XLSProcessor xlsproc = new XLSProcessor();
//        xlsproc.setPath("C:\\test.xls");
//        xlsproc.setSheetIndex(0);
//        xlsproc.test();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }

}
