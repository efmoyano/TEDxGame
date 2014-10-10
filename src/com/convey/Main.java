package com.convey;

import com.convey.GUI.MainFrame;
import com.convey.utils.ProcessPaths;
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

    static {
        if (!new ProcessRunner().getProcState(ProcessPaths.WAMP_PROCESS_ID)) {
            new ProcessRunner().run(ProcessPaths.WAMP_PATH);
        }
        try {
            boolean is64bit = false;
            if (System.getProperty("os.name").contains("Windows")) {
                is64bit = (System.getenv("ProgramFiles(x86)") != null);
            } else {
                is64bit = (System.getProperty("os.arch").contains("64"));
            }
            if (is64bit) {
                System.load(Main.class.getResource("/lib/opencv/x64/opencv_java249.dll").getPath());
            } else {
                System.load(Main.class.getResource("/lib/opencv/x86/opencv_java249.dll").getPath());
            }

        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    public static void main(String args[]) {
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
