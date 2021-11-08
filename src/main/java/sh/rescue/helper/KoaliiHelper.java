package sh.rescue.helper;

import sh.rescue.entity.SignResponse;
import sh.rescue.entity.VerifyResponse;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class KoaliiHelper {
    private static String hostPre;

    private KoaliiHelper() {
    }

    public static void init(String host) {
        hostPre = host;
    }

    public static SignResponse sign(byte[] data) {
        Map<String, String> map = new HashMap<>();
        map.put("b64OriginData", Base64.getEncoder().encodeToString(data));
        map.put("certAlias", "MZ_SM2");
        String json = JsonHelper.toStr(map);

        byte[] bytes = HttpHelper.post(hostPre + "/api/svs/bss/signMessageDetach", json);
        return JsonHelper.toObj(bytes, SignResponse.class);
    }

    public static boolean verify(String hash,String signData){
        Map<String, String> map = new HashMap<>();
        map.put("b64OriginData", Base64.getEncoder().encodeToString(hash.getBytes(StandardCharsets.UTF_8)));
        map.put("b64SignedMessage", signData);
        String json = JsonHelper.toStr(map);

        byte[] bytes = HttpHelper.post(hostPre + "/api/svs/bss/verifySignedMessageDetach", json);

        VerifyResponse rs = JsonHelper.toObj(bytes, VerifyResponse.class);
        return rs != null && rs.getErrorCode() == 0;
    }

}
