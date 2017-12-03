package com.company;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskIO {
    public static void write(TaskList tasks, OutputStream out) {
        DataOutputStream outStream = new DataOutputStream(out);
        try {
            outStream.writeInt(tasks.size());
            for (Task task : tasks) {
                int lt = task.getTitle().length();
                outStream.writeInt(task.getTitle().length());

                for (int i = 0; i < lt; i++) {
                    outStream.writeChar(task.getTitle().charAt(i));
                }

                outStream.writeBoolean(task.isActive());
                int interv = task.getRepeatInterval();
                outStream.writeInt(interv);

                if (interv == 0) {
                    outStream.writeLong(task.getStartTime().getTime());
                } else {
                    outStream.writeLong(task.getStartTime().getTime());
                    outStream.writeLong(task.getEndTime().getTime());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void read(TaskList tasks, InputStream in) {
        DataInputStream inStream = new DataInputStream(in);
        int len = 0;

        try {
            len = inStream.readInt();
            for (int i = 0; i < len; i++) {
                StringBuilder nt = new StringBuilder();
                int lt = inStream.readInt();

                for (int ii = 0; ii < lt; ii++) {
                    nt.append(inStream.readChar());
                }

                boolean active = inStream.readBoolean();
                int interv = inStream.readInt();
                Date date = new Date(inStream.readLong());
                Task t;

                if (interv == 0) {
                    t = new Task(nt.toString(), date);
                    t.setActive(active);
                } else {
                    Date dateEnd = new Date(inStream.readLong());
                    t = new Task(nt.toString(), date, dateEnd, interv);
                    t.setActive(active);
                }
                tasks.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void writeBinary(TaskList tasks, File file) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            write(tasks, dos);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void readBinary(TaskList tasks, File file) {
        try (DataInputStream dos = new DataInputStream(new FileInputStream(file))) {
            read(tasks, dos);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void write(TaskList tasks, Writer out) {
        BufferedWriter bufOut = null;
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            bufOut = new BufferedWriter(out);
            int lenArr = tasks.size();

            for (Task t : tasks) {
                bufOut.append("\"").append(t.getTitle()).append("\"");

                if (!t.isRepeated()) {
                    bufOut.append(" at [").append(dateForm.format(t.getTime())).append("]");
                } else {
                    bufOut.append(" from [").append(dateForm.format(t.getStartTime())).append("]");
                    bufOut.append(" to [").append(dateForm.format(t.getEndTime())).append("]");
                    bufOut.append(" every ");

                    int interOld = t.getRepeatInterval();
                    int interNew;
                    boolean flag = false;
                    bufOut.append("[");

                    if (interOld >= 86400) {
                        flag = true;

                        interNew = interOld / 86400;
                        if (interNew == 1) bufOut.append("1 day");
                        else bufOut.append(interNew + " day");

                        interOld = interOld - (interNew * 86400);
                    }


                    if (interOld >= 3600) {
                        if (flag) {
                            bufOut.append(" ");
                        } else {
                            flag = true;
                        }

                        interNew = interOld / 3600;
                        if (interNew == 1) {
                            bufOut.append("1 hour");
                        } else {
                            bufOut.append(interNew + " hours");
                        }
                        interOld = interOld - (interNew * 3600);
                    }

                    if (interOld >= 60) {
                        if (flag) {
                            bufOut.append(" ");
                        } else {
                            flag = true;
                        }

                        interNew = interOld / 60;
                        if (interNew == 1) {
                            bufOut.append("1 minute");
                        } else {
                            bufOut.append(interNew + " minutes");
                        }
                        interOld = interOld - (interNew * 60);
                    }

                    if (interOld > 0) {
                        if (flag) {
                            bufOut.append(" ");
                        }
                        if (interOld == 1) {
                            bufOut.append("1 second");
                        } else {
                            bufOut.append(interOld + " seconds");
                        }
                    }
                    bufOut.append("]");
                }

                if (!t.isActive()) {
                    bufOut.append(" inactive");
                }

                if (lenArr > 1) {
                    bufOut.append(";");
                } else {
                    bufOut.append(".");
                }
                lenArr--;
                bufOut.append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufOut != null) {
                    bufOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void read(TaskList tasks, Reader in) {
        BufferedReader bufIn = null;
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            bufIn = new BufferedReader(in);
            String s;
            int len;
            Task task;
            boolean active;

            while ((s = bufIn.readLine()) != null) {

                String[] newStrTask = s.split("\"");
                String nameTask = newStrTask[1];


                String[] newStr = newStrTask[2].split(" ");
                newStrTask = null;

                System.arraycopy(newStr, 1, newStr, 0, newStr.length - 1);

                if (newStr[0].equals("at")) {
                    newStr[1] = newStr[1].substring(1, newStr[1].length());

                    if (newStr[3].equals("inactive;") || newStr[3].equals("inactive.")) {
                        active = false;
                        newStr[2] = newStr[2].substring(0, newStr[2].length() - 1);
                    } else {
                        newStr[2] = newStr[2].substring(0, newStr[2].length() - 2);
                        active = true;
                    }

                    task = new Task(nameTask, date.parse(newStr[1] + " " + newStr[2]));
                    task.setActive(active);
                    tasks.add(task);

                } else if (newStr[0].equals("from")) {
                    newStr[1] = newStr[1].substring(1, newStr[1].length());
                    newStr[2] = newStr[2].substring(0, newStr[2].length() - 1);
                    Date start = date.parse(newStr[1] + " " + newStr[2]);

                    newStr[4] = newStr[4].substring(1, newStr[4].length());
                    newStr[5] = newStr[5].substring(0, newStr[5].length() - 1);
                    Date end = date.parse(newStr[4] + " " + newStr[5]);

                    newStr[7] = newStr[7].substring(1, newStr[7].length());

                    len = newStr.length - 2;

                    if (newStr[len].equals("inactive;") || newStr[len].equals("inactive.")) {
                        len = len - 1;
                        active = false;
                        newStr[len] = newStr[len].substring(0, newStr[len].length() - 1);
                    } else {
                        newStr[len] = newStr[len].substring(0, newStr[len].length() - 2);
                        active = true;
                    }

                    int interval = 0;

                    for (int i = 7; i <= len - 1; i = i + 2) {
                        if (newStr[i + 1].equals("day") || newStr[i + 1].equals("days"))
                            interval = interval + Integer.parseInt(newStr[i]) * 86400;
                        if (newStr[i + 1].equals("hour") || newStr[i + 1].equals("hours"))
                            interval = interval + Integer.parseInt(newStr[i]) * 3600;
                        if (newStr[i + 1].equals("minute") || newStr[i + 1].equals("minutes"))
                            interval = interval + Integer.parseInt(newStr[i]) * 60;
                        if (newStr[i + 1].equals("second") || newStr[i + 1].equals("seconds"))
                            interval = interval + Integer.parseInt(newStr[i]);
                    }

                    task = new Task(nameTask, start, end, interval);
                    task.setActive(active);
                    tasks.add(task);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufIn != null)
                    bufIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeText(TaskList tasks, File file) {
        try {
            write(tasks, new FileWriter(file));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void readText(TaskList tasks, File file) {
        try {
            read(tasks, new FileReader(file));
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
