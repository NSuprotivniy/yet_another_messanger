package security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class TokenCryptoProvider {

    private static String PUBLIC_KEY = "security/public_key.der";
    private static String PRIVATE_KEY = "security/private_key.der";


    private static final TokenCryptoProvider INSTANCE;
    static {
        try {
            INSTANCE  = new TokenCryptoProvider();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    public static TokenCryptoProvider getInstance() {
        return INSTANCE;
    }

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public TokenCryptoProvider() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        byte[] publicKeyContent = FileUtils.readFileToByteArray(new File(PUBLIC_KEY));
        byte[] privateKeyContent = FileUtils.readFileToByteArray(new File(PRIVATE_KEY));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicKeyContent);
        RSAPublicKey publicKey = (RSAPublicKey) kf.generatePublic(publicSpec);
        PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateKeyContent);
        RSAPrivateKey privateKey = (RSAPrivateKey)kf.generatePrivate(privateSpec);
        algorithm = Algorithm.RSA256(publicKey, privateKey);
        verifier = JWT.require(algorithm).build();
    }

    public String getToken(String uuid) {
        return JWT.create()
                .withSubject(uuid)
                .sign(algorithm);
    }

    public String getSubject(String token) {
        return verifier.verify(token).getSubject();
    }

    public static void main(String[] args) {
        TokenCryptoProvider instance = TokenCryptoProvider.getInstance();
        String token = instance.getToken("cae738c0-766b-11e9-9b3f-fd65cfaf2cb8");
        System.out.println(instance.getSubject(token));
    }
}
