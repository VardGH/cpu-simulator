import java.util.Map;
import java.util.HashMap;

public class ALU {
    private Memory memory;
    private RegisterFile registerFile;
    private Map<String, Byte> labelMap;

    public ALU(Memory memory, RegisterFile registerFile, Map<String, Byte> labelMap) {
        this.memory = memory;
        this.registerFile = registerFile;
        this.labelMap = labelMap;
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
        byte secondInstruction = memory.read((byte) (registerFile.getGH() + 1));
        byte destOpcode = (byte) (secondInstruction >> 4);
        byte srcOpcode = (byte) (secondInstruction & 0x0F);

        byte src = registerFile.getRegister(srcOpcode);
        byte dest = registerFile.getRegister(destOpcode);

        int result = src + dest;

        // Overflow handling
        if (result > Byte.MAX_VALUE || result < Byte.MIN_VALUE) {
            registerFile.setZA((byte) 1); 
        } else {
            registerFile.setRegister(destOpcode, (byte) result);
            registerFile.setZA((byte) 0); 
        }
    }

    public void sub() {
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte src = registerFile.getRegister(srcOpcode);
        byte dest = registerFile.getRegister(destOpcode);

        int result = src - dest;

        // Overflow handling
        if (result > Byte.MAX_VALUE || result < Byte.MIN_VALUE) {
            registerFile.setZA((byte) 1); 
        } else {
            registerFile.setRegister(destOpcode, (byte) result);
            registerFile.setZA((byte) 0); 
        }
    }

    public void mul() {
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte src = registerFile.getRegister(srcOpcode);
        byte dest = registerFile.getRegister(destOpcode);

        int result = src * dest;

        // Overflow handling
        if (result > Byte.MAX_VALUE || result < Byte.MIN_VALUE) {
            registerFile.setZA((byte) 1); 
        } else {
            registerFile.setRegister(destOpcode, (byte) result);
            registerFile.setZA((byte) 0); 
        }
    }

    public void div() {
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte src = registerFile.getRegister(srcOpcode);
        byte dest = registerFile.getRegister(destOpcode);

        if (src != 0) {
            int result = src / dest;
            // Overflow handling
            if (result > Byte.MAX_VALUE || result < Byte.MIN_VALUE) {
                registerFile.setZA((byte) 1); 
            } else {
                registerFile.setRegister(destOpcode, (byte) result);
                registerFile.setZA((byte) 0); 
            }
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
        byte secondInstruction = memory.read((byte)(registerFile.getGH() + 1));
        byte destOpcode = (byte)(secondInstruction >> 4);
        byte srcOpcode = (byte)(secondInstruction & 0x0F);

        byte reg1 = registerFile.getRegister(destOpcode);
        byte reg2 = registerFile.getRegister(srcOpcode);
        byte daOpcode = (byte)registerFile.registerToOpcode("DA");
        registerFile.setRegister(daOpcode, (byte) (reg1 - reg2));
    }

    public void jmp() {
        byte labelAddress = memory.read((byte)(registerFile.getGH() + 1));
        if (labelAddress == (byte)(registerFile.getGH() + 1)){
            registerFile.setGH(++labelAddress);
        } else { 
            registerFile.setGH(labelAddress);
        }
    }

    public void jg() {
        byte da = registerFile.getDA();
        if (da > 0) {
            byte labelAddress = memory.read((byte)(registerFile.getGH() + 1));
            if (labelAddress == (byte)(registerFile.getGH() + 1)){
                registerFile.setGH(++labelAddress);
            } else { 
                registerFile.setGH(labelAddress);
            }
        }
    }

    public void jl() {
        byte da = registerFile.getDA();
        if (da < 0) {
            byte labelAddress = memory.read((byte)(registerFile.getGH() + 1));
            if (labelAddress == (byte)(registerFile.getGH() + 1)){
                registerFile.setGH(++labelAddress);
            } else { 
                registerFile.setGH(labelAddress);
            }
        }
    }

    public void je() {
        byte da = registerFile.getDA();
        if (da == 0) {
            byte labelAddress = memory.read((byte)(registerFile.getGH() + 1));
            if (labelAddress == (byte)(registerFile.getGH() + 1)){
                registerFile.setGH(++labelAddress);
            } else { 
                registerFile.setGH(labelAddress);
            }
        }
    }
}