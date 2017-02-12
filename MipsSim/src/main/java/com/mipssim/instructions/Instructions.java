package com.mipssim.instructions;

public class Instructions {

    private String format;
    private String func;
    private String bin;

    public Instructions() {
        format = null;
        func = null;
        bin = null;
    }

    /**
     * Returns the instruction format.
     * @return String - format of instruction.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Set the instruction format.
     * @param format Format of the instruction.
     */
    public void setFormat(String format) {
        this.format = (format.equals("R") || format.equals("I") || format.equals("J")) ? format : null;
    }

    /**
     * Returns the instruction function.
     * @return String - function of instruction
     */
    public String getFunc() {
        return func;
    }

    /**
     * Set a function to the instruction.
     * @param func The function to the instruction.
     */
    public void setFunc(String func) {
        this.func = (func == null) ? null : func;
    }


    /**
     * Returns the binary representation (32-bit) of the instruction.
     * @return The binary representation of the instruction.
     */
    public String getBin() {
        return bin;
    }


    /**
     * Set a binary number to representing the instruction.
     * @param bin String - The 32-bit binary number.
     */
    public void setBin(String bin) {
        this.bin = (bin == null) ? null : bin;
    }

}
