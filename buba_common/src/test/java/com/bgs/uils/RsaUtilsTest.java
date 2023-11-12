package com.bgs.uils;

import org.junit.Test;

public class RsaUtilsTest {

    //私钥文件地址
    private String privateFilePath="D:\\key\\pri_key";

    //公钥文件地址
    private String publicFilePath="D:\\key\\public_key";

    /**
     * 生成公钥和私钥
     */
    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(
                publicFilePath,privateFilePath,
                "bgs",2048);
    }

    @Test
    public void getPublicKey() throws Exception{
        System.out.println(RsaUtils.getPublicKey(publicFilePath));
    }

    @Test
    public void getPrivateKey() throws Exception{
        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
    }
}