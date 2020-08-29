package com.north.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static byte[] _byte = new byte[1024];

    public static void readAllLine(Path path, Consumer<String> consumer) throws IOException {
        FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1);
        int LF = 10;//换行符
        int CR = 13;//回车符
        boolean hasLF = false;//是否有换行符
        List<String > list = new ArrayList<>();
        byte[] prevByte = new byte[0];
        int length = -1;
        while ( (length = channel.read(buffer))  != -1){
            int startNum = 0;
            buffer.clear();
            byte[] bytes = buffer.array();
            for(int i = 0; i < bytes.length; i++) {
                if (bytes[i] == LF) {
                    hasLF = true;
                    int prevNum = prevByte.length;
                    int lineNum = (i+1) - startNum;
                    byte[] lineByte = new byte[prevNum + lineNum];
                    System.arraycopy(prevByte, 0, lineByte, 0, prevNum);
                    System.arraycopy(bytes, startNum, lineByte, prevNum, lineNum);
                    String line = new String(lineByte, 0, lineByte.length, Charset.forName("UTF-8"));
                    consumer.accept(line);
                    startNum = i + 1;
                }
            }
            if(hasLF){
                prevByte = new byte[bytes.length - startNum];
                System.arraycopy(bytes, startNum, prevByte, 0, prevByte.length);
                hasLF = false;
            }else{
                byte[] temp = new byte[prevByte.length + bytes.length];
                System.arraycopy(prevByte, 0, temp, 0, prevByte.length);
                System.arraycopy(bytes, 0, temp, prevByte.length, bytes.length);
                prevByte = temp;
            }
        }
        channel.close();
    }

    /**
     * 单个文件下载
     *
     * @param file
     * @param request
     * @param response
     */
    public static void singleDownload(String path, String fileName, HttpServletRequest request, HttpServletResponse response) {
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        ServletOutputStream out;
        //通过文件路径获得File对象
        File file = new File(path + File.separator + fileName);

        try {
            FileInputStream inputStream = new FileInputStream(file);

            //3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();

            int b = 0;
            byte[] buffer = new byte[512];
            while ((b = inputStream.read(buffer)) != -1) {
                //4.写到输出流(out)中
                out.write(buffer, 0, b);
            }
            inputStream.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readTxtFile(String filePath, HttpServletRequest request, String name) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    request.setAttribute(name, lineTxt);
                }
                read.close();
            } else {
                logger.error("找不到指定的文件");
            }
        } catch (Exception e) {
            logger.error("读取文件内容出错", e);
        }

    }

    public static String readTxtFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                String lineTxt = null;
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    sb.append(lineTxt);
                }
                read.close();
            } else {
                logger.warn("找不到指定的文件");
            }
        } catch (Exception e) {
            logger.error("读取文件内容出错", e);
        }
        return sb.toString();
    }

    public static void writeTxt(String content, String savePath) {
        try {
            //String content = getContent(map);

            File file = new File(savePath);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     *
     * @param sourceFilePath :待压缩的文件路径
     * @param zipFilePath    :压缩后存放路径
     * @param fileName       :压缩后文件的名称
     * @return
     */
    public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if (sourceFile.exists() == false) {
            System.out.println("待压缩的文件目录：" + sourceFilePath + "不存在.");
        } else {
            try {
                File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
                if (zipFile.exists()) {
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
                } else {
                    File[] sourceFiles = sourceFile.listFiles();
                    if (null == sourceFiles || sourceFiles.length < 1) {
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    } else {
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024 * 10];
                        for (int i = 0; i < sourceFiles.length; i++) {
                            //创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024 * 10);
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                zos.write(bufs, 0, read);
                            }
                        }
                        flag = true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                //关闭流
                try {
                    if (null != bis) bis.close();
                    if (null != zos) zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return flag;
    }

    /**
     * 解压缩zip包
     *
     * @param zipFilePath   zip文件的全路径
     * @param unzipFilePath 解压后的文件保存的路径
     */
    @SuppressWarnings({"unchecked", "resource"})
    public static String unZip(String zipFilePath, String unzipFilePath) throws Exception {

        String fileName = "";
        File zipFile = new File(zipFilePath);

        // 创建解压缩文件保存的路径
        File unzipFileDir = new File(unzipFilePath);
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
            unzipFileDir.mkdirs();
        }

        // 开始解压
        ZipEntry entry = null;
        String entryFilePath = null;
        File entryFile = null;
        int count = 0, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
        // 循环对压缩包里的每一个文件进行解压
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            // 构建压缩包中一个文件解压后保存的文件全路径
            entryFilePath = unzipFilePath + File.separator + entry.getName();
            fileName += entryFilePath + ",";
            // 创建解压文件
            entryFile = new File(entryFilePath);

            // 写入文件
            bos = new BufferedOutputStream(new FileOutputStream(entryFile));
            bis = new BufferedInputStream(zip.getInputStream(entry));
            while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                bos.write(buffer, 0, count);
            }
            bos.flush();
        }
        bos.close();
        bis.close();
        zip.close();
        return fileName;
    }

    /**
     * 压缩文件或路径
     *
     * @param zip      压缩的目的地址
     * @param srcFiles 压缩的源文件
     */
    public static void zipFile(String zip, List<File> srcFiles) {
        try {
            if (zip.endsWith(".zip") || zip.endsWith(".ZIP")) {
                ZipOutputStream _zipOut = new ZipOutputStream(new FileOutputStream(new File(zip)));
                //_zipOut.setEncoding("GBK");
                for (File _f : srcFiles) {
                    handlerFile(zip, _zipOut, _f, "");
                }
                _zipOut.close();
            } else {
                System.out.println("target file[" + zip + "] is not .zip type file");
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    /**
     * @param zip     压缩的目的地址
     * @param zipOut
     * @param srcFile 被压缩的文件信息
     * @param path    在zip中的相对路径
     * @throws IOException
     */
    private static void handlerFile(String zip, ZipOutputStream zipOut, File srcFile, String path) throws IOException {
        System.out.println(" begin to compression file[" + srcFile.getName() + "]");
        if (!"".equals(path) && !path.endsWith(File.separator)) {
            path += File.separator;
        }
        if (!srcFile.getPath().equals(zip)) {
            if (srcFile.isDirectory()) {
                File[] _files = srcFile.listFiles();
                if (_files.length == 0) {
                    zipOut.putNextEntry(new ZipEntry(path + srcFile.getName() + File.separator));
                    zipOut.closeEntry();
                } else {
                    for (File _f : _files) {
                        handlerFile(zip, zipOut, _f, path + srcFile.getName());
                    }
                }
            } else {
                InputStream _in = new FileInputStream(srcFile);
                zipOut.putNextEntry(new ZipEntry(path + srcFile.getName()));
                int len = 0;
                while ((len = _in.read(_byte)) > 0) {
                    zipOut.write(_byte, 0, len);
                }
                _in.close();
                zipOut.closeEntry();
            }
        }
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 把十六进制转换为byte数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 转换方法
     *
     * @param c
     * @return
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 将byte数组写入文件
     *
     * @param data
     * @param path
     */
    public static void byteTofile(byte[] data, String path, String fileName) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        path = path + fileName;
        if (data.length < 3 || path.equals("")) return;
        try {
            FileOutputStream imageOutput = new FileOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /***
     * 将需要的内容写入文件
     */
    public static <T> void writeToFile(final T obj, final String pathStr, final String fileName) throws Exception {
        Callable<Object> callable = new Callable<Object>() {
            public Object call() throws Exception {
                Path path = Paths.get(pathStr, fileName);
                File baseFile = new File(pathStr);
                //创建目录
                if (Files.notExists(path.getParent())) {
                    Files.createDirectories(path.getParent());
                }
                //获取类型
                Class isList = obj.getClass();
                //如果是list执行
                if (isList.getName().contains("List")) {
                    //json数组头
                    String head = "[" + System.getProperty("line.separator");
                    Files.write(path, head.getBytes(), StandardOpenOption.CREATE_NEW);
                    List list = (List) obj;
                    for (int i = 0; i < list.size(); i++) {
                        //构造json对象
                        StringBuffer json = new StringBuffer("{");
                        Class clazz = list.get(i).getClass();
                        Field[] fs = clazz.getDeclaredFields();
                        Method[] methods = clazz.getMethods();
                        Map<String, String> map = new LinkedHashMap<>();
                        //如果有serialVersionUID -2否则-1
                        int dot = 1;
                        for (int k = 0; k < fs.length; k++) {
                            if (fs[k].getName().equals("serialVersionUID")) {
                                dot = 2;
                            }
                        }
                        for (int j = 0; j < fs.length; j++) {
                            //如果是serialVersionUID不取
                            if (fs[j].getName().equals("serialVersionUID")) {
                                continue;
                            }
                            PropertyDescriptor pd = new PropertyDescriptor(fs[j].getName(), clazz);
                            Method getMethod = pd.getReadMethod();
                            //如果没有值输出空字符串，否则输出对应的值
                            String value = getMethod.invoke(list.get(i)) == null ? "" : getMethod.invoke(list.get(i)).toString();
                            //如果是Date类型格式为日期
                            if (fs[j].getType().equals(Date.class)) {
                                Date d = (Date) getMethod.invoke(list.get(i));
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                                if (d != null) {
                                    value = sdf.format(d);
                                }
                            }
                            String name = fs[j].getName();
                            json.append("\"").append(name).append("\"");
                            json.append(":");
                            json.append("\"").append(value).append("\"");
                            //如果有serialVersionUID -2否则-1
                            if (j != fs.length - dot) {
                                json.append(",");
                            }
                        }
                        json.append("}");
                        json.append(",");
                        json.append(System.getProperty("line.separator"));
                        Files.write(path, json.toString().getBytes(), StandardOpenOption.APPEND);
                    }
                    String foot = "]";
                    Files.write(path, foot.getBytes(), StandardOpenOption.APPEND);
                } else {
                    //如果是对象执行
                    Files.write(path, JSONUtil.parseObjectToJSONString(obj).getBytes(), StandardOpenOption.CREATE_NEW);
                }
                return true;
            }
        };
        FutureTask<Object> task = new FutureTask<>(callable);
        new Thread(task).start();
    }

    public static void saveFile(MultipartFile file, Path path) throws IllegalStateException, IOException {
        if (Files.notExists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        file.transferTo(path.toFile());
    }

    public static void writeTxt(Path path, byte[] bytes) {
        try {
            if (Files.exists(path)) {
                Files.write(path, bytes, StandardOpenOption.TRUNCATE_EXISTING);
            } else {
                Files.write(path, bytes, StandardOpenOption.CREATE_NEW);
            }
        } catch (IOException e) {
            logger.error("Exception ", e);
        }
    }
}
