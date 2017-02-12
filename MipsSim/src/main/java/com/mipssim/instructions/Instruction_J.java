package com.mipssim.instructions;

public class Instruction_J extends Instructions {

    public Instruction_J(String binCode, String func) {
        super();
        this.setFormat("J");
        this.setFunc(func);
        this.setBin(binCode);
    }

}
