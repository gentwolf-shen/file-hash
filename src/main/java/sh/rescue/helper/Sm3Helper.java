package sh.rescue.helper;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Arrays;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;


public class Sm3Helper {
    private static final Charset ENCODING = StandardCharsets.UTF_8;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private Sm3Helper() {
    }

    /**
     * 计算字符串的hash
     *
     * @param str
     * @return
     */
    public static String toHashStr(String str) {
        return toHashStr(str.getBytes(ENCODING));
    }

    /**
     * 计算byte的hash
     *
     * @param bytes
     * @return
     */
    public static String toHashStr(byte[] bytes) {
        return toHexStr(toHashByte(bytes));
    }

    /**
     * 计算byte的hash, 并返回byte
     *
     * @param bytes
     * @return
     */
    public static byte[] toHashByte(byte[] bytes) {
        byte[] rs = new byte[]{};
        try {
            SM3Digest digest = new SM3Digest();
            digest.update(bytes, 0, bytes.length);
            rs = new byte[digest.getDigestSize()];
            digest.doFinal(rs, 0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("toHashByte error "+ e.getMessage());
        }

        return rs;
    }

    /**
     * 计算文件的hash
     *
     * @param filename
     * @return
     */
    public static String fileToHashStr(String filename) {
        return toHexStr(getHashFromFile(filename)).toUpperCase();
    }

    /**
     * 计算文件有hash上否相同
     *
     * @param filename
     * @param hexStr
     * @return
     */
    public static boolean verifyByFile(String filename, String hexStr) {
        return hexStr.equals(fileToHashStr(filename));
    }

    /**
     * 验证字符的hash是否相同
     *
     * @param str
     * @param hexStr
     * @return
     */
    public static boolean verifyByStr(String str, String hexStr) {
        boolean flag = false;
        try {
            byte[] sm3Hash = ByteUtils.fromHexString(hexStr);
            byte[] newHash = toHashByte(str.getBytes(ENCODING));
            flag = Arrays.equals(newHash, sm3Hash);
        } catch (Exception e) {
            System.out.println("verifyByStr error "+ e.getMessage());
        }
        return flag;
    }

    private static String toHexStr(byte[] bytes) {
        return ByteUtils.toHexString(bytes).toUpperCase();
    }

    private static byte[] getHashFromFile(String filename) {
        byte[] rs = new byte[]{};
        try {
            SM3Digest digest = new SM3Digest();
            try (FileInputStream inputStream = new FileInputStream(filename)) {
                byte[] bytes = new byte[1024000];
                int n = 0;
                while (-1 != (n = inputStream.read(bytes))) {
                    digest.update(bytes, 0, n);
                }
            }
            rs = new byte[digest.getDigestSize()];
            digest.doFinal(rs, 0);
        } catch (Exception e) {
            System.out.println("getHashFromFile error "+ e.getMessage());
        }

        return rs;
    }

}
