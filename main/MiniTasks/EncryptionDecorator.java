package common.MiniTasks;

import java.util.Base64;

public class EncryptionDecorator extends DataDecorator {
    public EncryptionDecorator(IData data) {
        super(data);
    }

    @Override
    public String read(String filename) {
        return decode(super.read(filename));
    }

    @Override
    public void write(String filename, String text) {
        super.write(filename, encode(text));
    }

    private String encode(String data) {
        byte[] result = data.getBytes();
        for (int i = 0; i < result.length; i++) {
            result[i] += (byte) 1;
        }
        return Base64.getEncoder().encodeToString(result);
    }

    private String decode(String data) {
        byte[] result = Base64.getDecoder().decode(data);
        for (int i = 0; i < result.length; i++) {
            result[i] -= (byte) 1;
        }
        return new String(result);
    }
}
