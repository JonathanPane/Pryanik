package com.example.pryanik.project.library;

import com.example.pryanik.BeanContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Logger {

    private static final PrintStream console_print_stream;
    private static volatile List<String> info_about_bean;

    static {
        BeanContext.register_system_bean("console_output", System.out);
        if (BeanContext.contains_bean("bean_info"))
            info_about_bean = BeanContext.get_bean("bean_info");
        console_print_stream = System.out;
        OutputStream os = new OutputStream() {
            final LinkedList<Byte> bytes = new LinkedList<>();

            @Override
            public void write(int b) throws IOException {
                bytes.add((byte) b);
            }

            @Override
            public void flush() throws IOException {
                info(new String(toStr()));
                bytes.clear();
            }

            private byte[] toStr() {
                byte[] res = new byte[bytes.size()];
                for (int i = 0; i < res.length; i++) {
                    res[i] = bytes.get(i);
                }
                return res;
            }
        };
        PrintStream ps = new PrintStream(os, true);
        System.setOut(ps);
        startDaemonForBeanChecking();
    }

    private static void startDaemonForBeanChecking() {
        Thread thr = new Thread(() -> {
            while (true)
                checkBeanContext();
        });
        thr.setDaemon(true);
        thr.setName("Daemon for bean logging");
        thr.start();
    }

    public static void info(String str) {
        print_by_type(str, "INFO", Color.valueOf("#2a7aaf"));
    }

    public static void warn(String str) {
        print_by_type(str, "WARN", Color.color(255, 204, 0));
    }

    public static void error(String str) {
        print_by_type(str, "ERROR", Color.RED);
    }

    private static void checkBeanContext() {
        if (info_about_bean == null) {
            if (BeanContext.contains_bean("bean_info"))
                info_about_bean = BeanContext.get_bean("bean_info");
            return;
        }
        if (info_about_bean.isEmpty())
            return;
        for (int i = 0; i < info_about_bean.size(); i++) {
            print_by_type(info_about_bean.get(i), "BEAN", Color.GREEN);
        }
        info_about_bean.clear();
    }

    private static void print_by_type(String str, String type, Color color) {
        if (str.isEmpty() || str.isBlank())
            return;
        console_print_stream.print(colorize(getTimestamp(), Color.valueOf("#ed864a")) + " [" + colorize(type, color) + "]: " + normalize_line_feed(str));
    }

    private static String colorize(String str, Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return "\u001b[38;2;" + r + ";" + g + ";" + b + "m" + str + "\u001b[0m";
    }

    static String normalize_line_feed(String str) {
        if (str.charAt(str.length() - 1) != '\n') {
            str += '\n';
        }
        while (str.charAt(str.length() - 1) == '\n' && str.charAt(str.length() - 2) == '\n')
            str = str.substring(0, str.length() - 1);
        return str;
    }

    static String getTimestamp() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        return time.format(formatter);
    }
}
