public class Memory {
    private byte[] memory;

    public Memory(byte size) {
        memory = new byte[size];
    }

    public byte read(byte address) {
        return memory[address];
    }

    public void write(byte address, byte data) {
        memory[address] = data;
    }

    public void dumpMemory() {
        for (int i = 0; i < memory.length; i++) {
            String binaryString = String.format("%8s", Integer.toBinaryString(memory[i] & 0xFF)).replace(' ', '0');
            System.out.println("[" + i + "]: " + binaryString);
        }
    }
}
