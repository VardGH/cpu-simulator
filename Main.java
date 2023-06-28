import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CPUSimulator cpu = new CPUSimulator();

        try {
            cpu.loadProgram("program.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        cpu.execute();
        cpu.dumpMemory();
        cpu.printRegister();
    }
}