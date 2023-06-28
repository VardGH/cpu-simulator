import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class CPUSimulator {
    private static final byte MEMORY_SIZE = 32;
    private static final byte PROGRAM_SIZE = 32;
    private static final byte PROGRAM_START_ADDRESS = 0;
    public Map<String, Byte> labelMap = new HashMap<>();

    private ALU alu;
    private Memory memory;
    private RegisterFile registerFile;

    public CPUSimulator() {
        memory = new Memory(MEMORY_SIZE);
        registerFile = new RegisterFile();
        alu = new ALU(memory, registerFile, labelMap);
    }


    public void loadLabels(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        byte address = PROGRAM_START_ADDRESS;
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (!line.isEmpty()) {
                String newline = line.replace(", ", " ");
                String[] tokens = newline.split(" ");
                String instruction = tokens[0];

                if (instruction.endsWith(":")) {
                    String label = instruction.substring(0, instruction.length() - 1);
                    System.out.println("label: " + label);
                    labelMap.put(label, (byte) (address + 2));
                    System.out.println("address label: " + (byte) (address + 2));
                }
            }
            ++address;
        }
        reader.close();
    }

    public void loadProgram(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        loadLabels(filePath);
        byte address = PROGRAM_START_ADDRESS;

        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (!line.isEmpty()) {
                String newline = line.replace(", ", " ");
                String[] tokens = newline.split(" ");
                String instruction = tokens[0];

                if (instruction.equals("MOV")) {
                    String operand1 = tokens[1];
                    String operand2 = tokens[2];

                    if (operand1.startsWith("[")) { // move [address], reg
                        int destAddress = Integer.parseInt(operand1.substring(1, operand1.length() - 1));
                        byte opcode = 0;
                        byte srcOpcode = (byte) (registerFile.registerToOpcode(operand2) << 1);
                        memory.write(address, (byte) ((byte)(opcode | srcOpcode) | 1));
                        memory.write(++address, (byte) destAddress);

                    } else { // move reg, val
                        byte opcode = 0;
                        byte destOpcode = (byte) (registerFile.registerToOpcode(operand1) << 1);
                        int srcOpcode = Integer.parseInt(operand2);
                        memory.write(address, (byte) (opcode | destOpcode));
                        memory.write(++address, (byte) srcOpcode);
                    }
                } else if (instruction.equals("ADD")) {
                    byte opcode = 1;
                    memory.write(address, (byte) (opcode << 4));

                    String destRegister = tokens[1];
                    String srcRegister = tokens[2];
                    byte destOpcode = (byte) (registerFile.registerToOpcode(destRegister) << 4);
                    byte srcOpcode = (byte) registerFile.registerToOpcode(srcRegister);
                    memory.write(++address, (byte) (destOpcode | srcOpcode));

                } else if (instruction.equals("SUB")) {
                    byte opcode = 2;
                    memory.write(address, (byte) (opcode << 4));

                    String destRegister = tokens[1];
                    String srcRegister = tokens[2];
                    byte destOpcode = (byte) (registerFile.registerToOpcode(destRegister) << 4);
                    byte srcOpcode = (byte) registerFile.registerToOpcode(srcRegister);
                    memory.write(++address, (byte) (destOpcode | srcOpcode));

                } else if (instruction.equals("MUL")) {
                    byte opcode = 3;
                    memory.write(address, (byte) (opcode << 4));

                    String destRegister = tokens[1];
                    String srcRegister = tokens[2];
                    byte destOpcode = (byte) (registerFile.registerToOpcode(destRegister) << 4);
                    byte srcOpcode = (byte) registerFile.registerToOpcode(srcRegister);
                    memory.write(++address, (byte) (destOpcode | srcOpcode));

                } else if (instruction.equals("DIV")) {
                    byte opcode = 4;
                    memory.write(address, (byte) (opcode << 4));

                    String destRegister = tokens[1];
                    String srcRegister = tokens[2];
                    byte destOpcode = (byte) (registerFile.registerToOpcode(destRegister) << 4);
                    byte srcOpcode = (byte) registerFile.registerToOpcode(srcRegister);
                    memory.write(++address, (byte) (destOpcode | srcOpcode));

                } else if (instruction.equals("AND")) {
                    byte opcode = 5;
                    memory.write(address, (byte) (opcode << 4));

                    String destRegister = tokens[1];
                    String srcRegister = tokens[2];
                    byte destOpcode = (byte) (registerFile.registerToOpcode(destRegister) << 4);
                    byte srcOpcode = (byte) registerFile.registerToOpcode(srcRegister);
                    memory.write(++address, (byte) (destOpcode | srcOpcode));

                } else if (instruction.equals("OR")) {
                    byte opcode = 6;
                    memory.write(address, (byte) (opcode << 4));

                    String destRegister = tokens[1];
                    String srcRegister = tokens[2];
                    byte destOpcode = (byte) (registerFile.registerToOpcode(destRegister) << 4);
                    byte srcOpcode = (byte) registerFile.registerToOpcode(srcRegister);
                    memory.write(++address, (byte) (destOpcode | srcOpcode));

                } else if (instruction.equals("NOT")) {
                    byte opcode = 7;
                    memory.write(address, (byte) (opcode << 4));
                    String register = tokens[1];
                    byte regOpcode = (byte) registerFile.registerToOpcode(register);
                    memory.write(++address, regOpcode);

                } else if (instruction.equals("CMP")) {
                    byte opcode = 8;
                    memory.write(address, (byte) (opcode << 4));
                    String destRegister = tokens[1];
                    String srcRegister = tokens[2];
                    byte destOpcode = (byte) (registerFile.registerToOpcode(destRegister) << 4);
                    byte srcOpcode = (byte) registerFile.registerToOpcode(srcRegister);
                    memory.write(++address, (byte) (destOpcode | srcOpcode));

                } else if (instruction.equals("JMP")) {
                    byte opcode = 9;
                    memory.write(address, (byte) (opcode << 4));
                    String label = tokens[1];
                    byte targetAddress = labelMap.get(label);
                    memory.write(++address, targetAddress);

                } else if (instruction.equals("JG")) {
                    byte opcode = 10;
                    memory.write(address, (byte) (opcode << 4));
                    String label = tokens[1];
                    byte targetAddress = labelMap.get(label);
                    memory.write(++address, targetAddress);

                } else if (instruction.equals("JL")) {
                    byte opcode = 11;
                    memory.write(address, (byte) (opcode << 4));
                    String label = tokens[1];
                    byte targetAddress = labelMap.get(label);
                    memory.write(++address, targetAddress);

                } else if (instruction.equals("JE")) {
                    byte opcode = 12;
                    memory.write(address, (byte) (opcode << 4));
                    String label = tokens[1];
                    byte targetAddress = labelMap.get(label);
                    memory.write(++address, targetAddress);
                    
                } else if (instruction.equals("HALT")) {
                    byte opcode = 13;
                    byte data = -1;
                    memory.write(address, (byte)(opcode << 4));
                    memory.write(++address, data);

                } else if (instruction.endsWith(":")) {
                    continue;
                } else {
                    System.out.println("Invalid instruction: " + instruction);
                }
                System.out.println("addres: " + address );
                ++address;
            }
        }
        reader.close();
    }

    public void execute() {
        while (true) {
            byte instruction = memory.read(registerFile.getGH());
            byte opcode = (byte) (instruction >> 4);
            System.out.println("opcode1 " + opcode);
            if (opcode < 0 && opcode != -1) {
                opcode = (byte)~((byte)(opcode ^ (byte) 15));
            }
            System.out.println("opcode " + opcode);

            switch (opcode) {
                case 0:
                    alu.mov();
                    break;
                case 1:
                    alu.add();
                    break;
                case 2:
                    alu.sub();
                    break;
                case 3:
                    alu.mul();
                    break;
                case 4:
                    alu.div();
                    break;
                case 5:
                    alu.and();
                    break;
                case 6:
                    alu.or();
                    break;
                case 7:
                    alu.not();
                    break;
                case 8:
                    alu.cmp();
                    break;
                case 9:
                    alu.jmp();
                    break;
                case 10:
                    alu.jg();
                    break;
                case 11:
                    alu.jl();
                    break;
                case 12:
                    alu.je();
                    break;
                case 13:
                    return;
                default:
                    System.out.println("Invalid opcode: " + opcode);
            }
            registerFile.setGH((byte) (registerFile.getGH() + 2));
        }
    }

    public void dumpMemory() {
        memory.dumpMemory();
    }

    public void printRegister() {
        registerFile.printRegister();
    }

    public void printLabelMap(Map<String, Byte> labelMap) {
        for (Map.Entry<String, Byte> entry : labelMap.entrySet()) {
            String label = entry.getKey();
            Byte value = entry.getValue();
            System.out.println(label + " -> " + value);
        }
    }
}