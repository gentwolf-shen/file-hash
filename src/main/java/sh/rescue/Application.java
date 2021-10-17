package sh.rescue;

import org.apache.commons.cli.*;
import sh.rescue.entity.FileInfo;
import sh.rescue.entity.SignResponse;
import sh.rescue.helper.FileHelper;
import sh.rescue.helper.JsonHelper;
import sh.rescue.helper.KoaliiHelper;
import sh.rescue.helper.Sm3Helper;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class Application {

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("help", false, "show help");
        options.addOption("signHost", true, "签名服务器地址,如 http://60.247.61.98:15009");
        options.addOption("target", true, "hash: 计算hash及签名; verify: 验证hash及签名");
        options.addOption("src", true, "需要计算hash及签名的文件所在目录");
        options.addOption("dst", true, "计算hash及签名结果储存的的文件目录");
        options.addOption("filename", true, "验证hash及签名的数据文件");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("help") || !cmd.hasOption("target") || !cmd.hasOption("signHost")) {
            showHelper(options);
            return;
        }

        KoaliiHelper.init(cmd.getOptionValue("signHost"));

        String target = cmd.getOptionValue("target");
        if ("hash".equals(target)) {
            String src = cmd.getOptionValue("src");
            String dst = cmd.getOptionValue("dst");
            if ("".equals(src) || "".equals(dst)) {
                showHelper(options);
                return;
            }

            toHash(src, dst);
        } else if ("verify".equals(target)) {
            String filename = cmd.getOptionValue("filename");
            if ("".equals(filename)) {
                showHelper(options);
                return;
            }
            verifyHash(filename);
        }
    }

    private static void showHelper(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("参数", options, true);
    }

    /**
     * 生成hash
     *
     * @param src
     * @param dst
     */
    public static void toHash(String src, String dst) {
        List<FileInfo> list = new ArrayList<>();

        List<String> files = FileHelper.getFiles(src);
        if (files == null || files.isEmpty()) {
            return;
        }

        files.forEach(filename -> {
            String hash = Sm3Helper.fileToHashStr(filename);
            SignResponse rs = KoaliiHelper.sign(hash.getBytes());

            FileInfo fileInfo = new FileInfo(filename, hash, rs != null ? rs.getB64SignedMessage() : "");
            list.add(fileInfo);

            System.out.println("file " + fileInfo.getFilename());
            System.out.println("hash " + fileInfo.getHash());
            System.out.println("signed " + (rs != null && rs.getErrorCode() == 0 ? "succeed" : "failed"));
            System.out.println("--------------------------------------");
        });

        String str = JsonHelper.toStr(list);
        String filename = dst + getDateTime() + ".txt";
        boolean bl = FileHelper.save(filename, str.getBytes(StandardCharsets.UTF_8));
        if (bl) {
            System.out.println("save file to " + filename);
        }
    }

    /**
     * 验证hash
     *
     * @param filename
     */
    public static void verifyHash(String filename) {
        if (!FileHelper.isExists(filename)) {
            System.out.println("文件不存在 " + filename);
            return;
        }
        List<FileInfo> list = JsonHelper.fileToList(filename);
        if (list == null || list.isEmpty()) {
            System.out.println("解析文件失败 " + filename);
            return;
        }

        list.forEach(item -> {
            boolean hashVerifyRs = false;
            boolean signVerifyRs = false;
            if (FileHelper.isExists(item.getFilename())) {
                hashVerifyRs = Sm3Helper.verifyByFile(item.getFilename(), item.getHash());
                signVerifyRs = KoaliiHelper.verify(item.getHash(), item.getSign());
            }

            System.out.println("file " + item.getFilename());
            System.out.println("hash verify " + (hashVerifyRs ? "passed" : "failed"));
            System.out.println("sign verify " + (signVerifyRs ? "passed" : "failed"));
            System.out.println("--------------------------------------");
        });
    }

    public static String getDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date(System.currentTimeMillis()));
    }
}
