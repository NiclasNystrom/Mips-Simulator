package com.mipssim.instructions;

public class Instruction_I extends  Instructions {

    public Instruction_I(String binCode, String func) {
        super();
        this.setFormat("I");
        this.setFunc(func);
        this.setBin(binCode);
    }

}
