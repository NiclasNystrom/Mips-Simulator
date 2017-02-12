package com.mipssim;

public class Output {

    private String input;
    private String format;
    private String hexDecomp;
    private String decDecomp;
    private String mnemonic;
    private int lineInput;

    /**
     *
     * @param format Format of instruction.
     * @param hexDecomp Hexadecimal decomposition of instruction.
     * @param decDecomp Decimal decomposition of instruction.
     * @param mnemonic Mnemonic decomposition of instruction.
     * @param lineInput The linenumber of the instruction (when read from file).
     */
    public Output(String format, String hexDecomp, String decDecomp, String mnemonic, int lineInput) {
        this.format      = format;
        this.hexDecomp   = hexDecomp;
        this.decDecomp   = decDecomp;
        this.mnemonic    = mnemonic;
        this.lineInput   = lineInput;
    }

    /**
     * Returns the format of the instruction.
     * @return Instruction format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Returns the hexadecimal decomposition of the instruction.
     * @return Hexadecimal number
     */
    public String getHexDecomp() {
        return hexDecomp;
    }

    /**
     * Returns the decimal decomposition of the instruction.
     * @return Decimal number.
     */
    public String getDecDecomp() {
        return decDecomp;
    }

    /**
     * Returns the mnemonic decomposition of the instruction.
     * @return The mnemonic decomposition (String).
     */
    public String getMnemonic() {
        return mnemonic;
    }

    /**
     * Returns the linenumber where the instruction was extracted from when read the input-file.
     * @return Linenumber (int).
     */
    public int getLineInput() {
        return lineInput;
    }

    /**
     * Returns the original input either in hexa- or decimal-format.
     * @return input
     */
    public String getInput() {
        return input;
    }

    /**
     * Set the input
     * @param input Original input from file.
     */
    public void setInput(String input) {
        this.input = input;
    }
}
