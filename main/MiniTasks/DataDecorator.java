package MiniTasks;

class DataDecorator implements IData {
    protected IData wrappee;

    DataDecorator(IData data) {
        this.wrappee = data;
    }

    public String read(String filename) {
        return this.wrappee.read(filename);
    }

    public void write(String filename, String text) {
        this.wrappee.write(filename, text);
    }
}