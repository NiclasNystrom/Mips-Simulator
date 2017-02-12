package com.mipssim.instructions;

public class Instruction_R extends Instructions {

    public Instruction_R(String binCode, String func) {
        super();
        this.setFormat("R");
        this.setFunc(func);
        this.setBin(binCode);
    }

}
