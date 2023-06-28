public class RegisterFile {
    private byte ayb;
    private byte ben;
    private byte gim;
    private byte da;
    private byte ech;
    private byte za;
    private byte gh;

    public RegisterFile() {
        ayb = 0;
        ben = 0;
        gim = 0;
        da = 0;
        ech = 0;
        za = 0;
        gh = 0;
    }

    public byte getGH() {
        return gh;
    }

    public void setGH(byte value) {
        gh = value;
    }

    public byte registerToOpcode(String register) {
        switch (register) {
            case "AYB":
                return 0;
            case "BEN":
                return 1;
            case "GIM":
                return 2;
            case "DA":
                return 3;
            case "ECH":
                return 4;
            case "ZA":
                return 5;
            default:
                throw new IllegalArgumentException("Invalid register: " + register);
        }
    }

    public void setRegister(byte regOpcode, byte data) {
        switch (regOpcode) {
            case 0:
                ayb = data;
                break;
            case 1:
                ben = data;
                break;
            case 2:
                gim = data;
                break;
            case 3:
                da = data;
                break;
            case 4:
                ech = data;
                break;
            case 5:
                za = data;
                break;
            default:
                System.out.println("Invalid register opcode in set: " + regOpcode);
                break;
        }
    }

    public byte getRegister(byte regOpcode) {
        switch (regOpcode) {
            case 0:
                return ayb;
            case 1:
                return ben;
            case 2:
                return gim;
            case 3:
                return da;
            case 4:
                return ech;
            case 5:
                return za;
            default:
                System.out.println("Invalid register opcode in get: " + regOpcode);
                break;
        }
        return 0;
    }

    public void printRegister() {
        System.out.println("Registers");
        System.out.println("ayb " + ayb);
        System.out.println("ben " + ben);
        System.out.println("gim " + gim);
        System.out.println("da  " + da);
        System.out.println("ech " + ech);
        System.out.println("za  " + za);
    }
}