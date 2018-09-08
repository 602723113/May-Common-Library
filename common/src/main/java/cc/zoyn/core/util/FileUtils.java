package cc.zoyn.core.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件处理工具类
 *
 * @author Zoyn
 */
public final class FileUtils {

    // Prevent accidental construction
    private FileUtils() {
    }

    /**
     * 移动 文件或者文件夹
     *
     * @param oldPath 老路径
     * @param newPath 新路径
     * @throws IOException IO异常
     */
    public static void moveTo(String oldPath, String newPath) throws IOException {
        copyFile(oldPath, newPath);
        deleteFile(oldPath);
    }

    /**
     * 删除 文件或者文件夹
     *
     * @param filePath 文件/文件夹路径
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            // prevent forEach null
            if (list == null) {
                return;
            }
            for (File f : list) {
                deleteFile(f.getAbsolutePath());
            }
        }
        file.delete();
    }

    /**
     * 复制 文件或者文件夹
     *
     * @param oldPath 老路径
     * @param newPath 新路径
     * @throws IOException IO异常
     */
    public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        if (oldFile.exists()) {

            if (oldFile.isDirectory()) { // 如果是文件夹
                File newPathDir = new File(newPath);
                newPathDir.mkdirs();
                File[] lists = oldFile.listFiles();
                if (lists != null && lists.length > 0) {
                    for (File file : lists) {
                        copyFile(file.getAbsolutePath(), newPath.endsWith(File.separator) ? newPath + file.getName()
                                : newPath + File.separator + file.getName());
                    }
                }
            } else {
                InputStream inStream = new FileInputStream(oldFile); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                write2Out(inStream, fs);
                inStream.close();
            }
        }
    }

    /**
     * 重命名文件
     *
     * @param file 文件
     * @param name 名字
     * @return 那个文件的File对象
     */
    public static File renameFile(File file, String name) {
        String fileName = file.getParent() + File.separator + name;
        File dest = new File(fileName);
        file.renameTo(dest);
        return dest;
    }

    /**
     * 压缩多个文件。
     *
     * @param zipFileName 压缩输出文件名
     * @param files       需要压缩的文件
     * @return ZIP文件对象
     * @throws Exception 多个异常
     */
    public static File createZips(String zipFileName, File... files) throws Exception {
        File outFile = new File(zipFileName);
        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(outFile));
            bo = new BufferedOutputStream(out);

            for (File file : files) {
                zip(out, file, file.getName(), bo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bo != null) {
                    bo.close();
                }
            } finally {
                if (out != null) {
                    // 输出流关闭
                    out.close();
                }
            }
        }
        return outFile;
    }

    /**
     * @param zipFileName 压缩输出文件名
     * @param inputFile   需要压缩的文件
     * @return ZIP文件对象
     * @throws Exception 多个异常
     */
    public static File createZip(String zipFileName, File inputFile) throws Exception {
        File outFile = new File(zipFileName);
        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(outFile));
            bo = new BufferedOutputStream(out);
            zip(out, inputFile, inputFile.getName(), bo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bo != null) {
                    bo.close();
                }
            } finally {
                if (out != null) {
                    out.close(); // 输出流关闭
                }
            }
        }
        return outFile;
    }

    private static void zip(ZipOutputStream out, File file, String base, BufferedOutputStream bo) throws Exception { // 方法重载
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/")); // 创建创建一个空的文件夹
            } else {
                for (File fileTemp : files) {
                    zip(out, fileTemp, base + "/" + fileTemp.getName(), bo); // 递归遍历子文件夹
                }
            }

        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入 base 文件

            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                write2Out(in, out);
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    private static void write2Out(InputStream input, OutputStream out) throws IOException {
        byte[] b = new byte[1024];
        int c;
        while ((c = input.read(b)) != -1) {
            out.write(b, 0, c);
            out.flush();
        }
        out.flush();
    }

}
