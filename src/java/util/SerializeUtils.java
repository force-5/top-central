package util;

import java.io.*;

public class SerializeUtils {

    public static Object clone(Object object) throws Exception {
        return readFromMem(writeToMem(object));
    }

    public static Object readFromFile(String filename) throws Exception {
        FileInputStream fin = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fin);
        Object object = ois.readObject();
        ois.close();
        fin.close();
        return object;
    }

    public static void writeToFile(Object object, String filename) throws Exception {
        FileOutputStream fout = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(object);
        oos.flush();
        oos.close();
        fout.close();
    }

    public static Object readFromMem(byte[] byteArray) throws Exception {
        ByteArrayInputStream bin = new ByteArrayInputStream(byteArray);
        ObjectInputStream ois = new ObjectInputStream(bin);
        Object object = ois.readObject();
        ois.close();
        bin.close();
        return object;
    }

    public static byte[] writeToMem(Object object) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.flush();
        return bos.toByteArray();
    }

}

