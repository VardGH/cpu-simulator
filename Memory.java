public class Memory {
    private byte[] memory;
    private byte programMemorySize;

    public Memory(byte memorySize) {
        memory = new byte[memorySize];
        programMemorySize = 0;
    }

    public byte read(byte address) {
        if (address < 0 || address >= memory.length) {
            throw new IndexOutOfBoundsException("Invalid memory address: " + address);
        }
        return memory[address];
    }

    public void write(byte address, byte data) {
        if (address < 0 || address >= memory.length || address < programMemorySize) {
            throw new IndexOutOfBoundsException("Invalid memory address: " + address);
        }
        memory[address] = data;
    }

    public void setProgramSize(byte size) {
        programMemorySize = size;
    }

    public void dumpMemory() {
        System.out.println("RAM");
        for (int i = 0; i < memory.length; i++) {
            String binaryString = String.format("%8s", Integer.toBinaryString(memory[i] & 0xFF)).replace(' ', '0');
            System.out.println("[" + i + "]: " + binaryString);
        }
    }
}
