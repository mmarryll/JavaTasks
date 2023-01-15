package tests;

import MiniTasks.CompressDecorator;
import MiniTasks.EncryptionDecorator;
import MiniTasks.ReadWrite;
import MiniTasks.ZipDecorator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DecoratorTests {
    public DecoratorTests() {
    }

    @Test
    public void zipUnzip() {
        String path = "test files/test1.txt";
        ReadWrite readWrite = new ReadWrite();
        ZipDecorator zipperDecorator = new ZipDecorator(readWrite);
        String text1 = readWrite.read(path);
        zipperDecorator.write("test1.zip", text1);
        String text2 = zipperDecorator.read("test1.zip");
        Assert.assertEquals(text1, text2);
    }

    @Test
    public void encodeDecode() {
        String path = "test files/test1.txt";
        ReadWrite readWrite = new ReadWrite();
        EncryptionDecorator encryptionDecorator = new EncryptionDecorator(readWrite);
        String text1 = readWrite.read(path);
        encryptionDecorator.write("test1.zip", text1);
        String text2 = encryptionDecorator.read("test1.zip");
        Assert.assertEquals(text1, text2);
    }

    @Test
    public void compressDecompress() {
        String path = "test files/test1.txt";
        ReadWrite readWrite = new ReadWrite();
        CompressDecorator compressDecorator = new CompressDecorator(readWrite);
        String text1 = readWrite.read(path);
        compressDecorator.write("test1.zip", text1);
        String text2 = compressDecorator.read("test1.zip");
        Assert.assertEquals(text1, text2);
    }
}

