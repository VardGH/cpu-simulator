public class ALU {
    private Memory memory;
    private RegisterFile registerFile;

    public ALU(Memory memory, RegisterFile registerFile) {
        this.memory = memory;
        this.registerFile = registerFile;
    }

    public boolean lastBitOne(byte instruction) {
        byte tmp_instruction = instruction;
        if ((byte)(tmp_instruction & 1) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void mov() {
        byte instruction = memory.read((byte)(registerFile.getGH()));
        if (lastBitOne(instruction)) {
            byte regOpcode = (byte) ((instruction << 4) >> 5);
            byte registerValue = registerFile.getRegister(regOpcode);
            byte memoryAddress = memory.read((byte)(registerFile.getGH() + 1));
            memory.write(memoryAddress, registerValue);

        } else {
            byte regOpcode = (byte) ((byte) (instruction << 4) >> 5);
            byte memoryData = memory.read((byte) (registerFile.getGH() + 1));
            registerFile.setRegister(regOpcode, memoryData);
        }
    }

    public void add() {
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte src = registerFile.getRegister(srcOpcode);
        byte dest = registerFile.getRegister(destOpcode);

        registerFile.setRegister(destOpcode, (byte)(src + dest));
    }

    public void sub() {
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte src = registerFile.getRegister(srcOpcode);
        byte dest = registerFile.getRegister(destOpcode);

        registerFile.setRegister(destOpcode, (byte)(dest - src));
    }

    public void mul() {
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte src = registerFile.getRegister(srcOpcode);
        byte dest = registerFile.getRegister(destOpcode);

        registerFile.setRegister(destOpcode, (byte)(dest * src));
    }

    public void div() {
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte src = registerFile.getRegister(srcOpcode);
        byte dest = registerFile.getRegister(destOpcode);

        if (src != 0) {
            registerFile.setRegister(destOpcode, (byte)(dest / src));
        } else {
            System.out.println("Error: Division by zero");
        }
    }

    public void and() {
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte src = registerFile.getRegister(srcOpcode);
        byte dest = registerFile.getRegister(destOpcode);

        registerFile.setRegister(destOpcode, (byte)(dest & src));
    }

    public void or() {
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte src = registerFile.getRegister(srcOpcode);
        byte dest = registerFile.getRegister(destOpcode);

        registerFile.setRegister(destOpcode, (byte)(dest | src));
    }

    public void not() {
        byte registerOpcode = memory.read((byte) (registerFile.getGH() + 1));
        byte registerValue = registerFile.getRegister(registerOpcode);
        byte result = (byte) (~registerValue);
        registerFile.setRegister(registerOpcode, result);
    }

    public void cmp() {
        System.out.println("CMP ");
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte reg1 = registerFile.getRegister(destOpcode);
        System.out.println("reg1 " + reg1);
        byte reg2 = registerFile.getRegister(srcOpcode);
        System.out.println("reg2 " + reg2);
        System.out.println("reg1 - reg2" + (reg1 - reg2));

        byte daOpcode = (byte)registerFile.registerToOpcode("DA");
        System.out.println("daopcode " + daOpcode);
        registerFile.setRegister(daOpcode, (byte) (reg1 - reg2));
    }

}