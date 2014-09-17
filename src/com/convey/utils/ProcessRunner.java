package com.convey.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @projectName TEDxGame
 * @package com.convey.utils
 * @filename ProcessRunner.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 07/09/2014 16:02:26
 */
public class ProcessRunner {

    /**
     *
     * @param p_path
     */
    public void run(String p_path) {
        try {
            Process newProcess = new ProcessBuilder(new String[]{"cmd.exe", "/C", p_path}).start();
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ProcessRunner.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            newProcess.destroy();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * @param processId
     * @return
     */
    public boolean getProcState(String processId) {

        try {
            String str_proceso = null;
            String tasklist = System.getenv("windir") + "\\system32\\" + "tasklist.exe";
            Process proceso = Runtime.getRuntime().exec(tasklist);

            try (BufferedReader input = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                while ((str_proceso = input.readLine()) != null) {
                    if (isPhrasePresent(str_proceso, processId)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param p_phrase
     * @param p_pattern
     * @return
     */
    public boolean isPhrasePresent(String p_phrase, String p_pattern) {

        Pattern l_pattern = Pattern.compile(p_pattern);
        Matcher l_matcher = l_pattern.matcher(p_phrase);
        while (l_matcher.find()) {
            return true;
        }
        return false;
    }

}
