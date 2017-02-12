package com.mipssim;

import com.mipssim.instructions.Instruction_I;
import com.mipssim.instructions.Instruction_J;
import com.mipssim.instructions.Instruction_R;
import com.mipssim.instructions.Instructions;

import java.util.HashMap;

@SuppressWarnings("unchecked")

/**
 * Mappings contain HashMaps that maps functions and register to certain hexadecimal-numbers accordingly to the MIPS-
 * architecture i.e. the OP-map which is found in the course book on A-50 (5:th ed).
 *
 */
public class Mappings {
    
    private HashMap<String, HashMap> opCodeToFuncMap;
    private HashMap<Integer, String> register;
    private HashMap<Integer, String> floatRegister;


    public Mappings() {

        opCodeToFuncMap = new HashMap<>();
        
        HashMap<String, ? super Instructions> fSet1 = new HashMap<>();
        fSet1.put("0x00", new Instruction_R(null, "sll"));
        fSet1.put("0x02", new Instruction_R(null, "srl"));
        fSet1.put("0x03", new Instruction_R(null, "sra"));
        fSet1.put("0x04", new Instruction_R(null, "sllv"));
        fSet1.put("0x06", new Instruction_R(null, "srlv"));
        fSet1.put("0x07", new Instruction_R(null, "srav"));
        fSet1.put("0x08", new Instruction_R(null, "jr"));
        fSet1.put("0x09", new Instruction_R(null, "jalr")); 
        fSet1.put("0x0a", new Instruction_R(null, "movz")); 
        fSet1.put("0x0b", new Instruction_R(null, "movn")); 
        fSet1.put("0x0c", new Instruction_R(null, "syscall")); 
        fSet1.put("0x0d", new Instruction_R(null, "break")); 
        fSet1.put("0x0f", new Instruction_R(null, "sync")); 
        fSet1.put("0x10", new Instruction_R(null, "mfhi"));
        fSet1.put("0x11", new Instruction_R(null, "mthi")); 
        fSet1.put("0x12", new Instruction_R(null, "mflo"));
        fSet1.put("0x13", new Instruction_R(null, "mtlo")); 
        fSet1.put("0x18", new Instruction_R(null, "mult"));
        fSet1.put("0x19", new Instruction_R(null, "multu"));
        fSet1.put("0x1a", new Instruction_R(null, "div"));
        fSet1.put("0x1b", new Instruction_R(null, "divu"));
        fSet1.put("0x20", new Instruction_R(null, "add"));
        fSet1.put("0x21", new Instruction_R(null, "addu"));
        fSet1.put("0x22", new Instruction_R(null, "sub"));
        fSet1.put("0x23", new Instruction_R(null, "subu"));
        fSet1.put("0x24", new Instruction_R(null, "and"));
        fSet1.put("0x25", new Instruction_R(null, "or"));
        fSet1.put("0x26", new Instruction_R(null, "xor"));
        fSet1.put("0x27", new Instruction_R(null, "nor"));
        fSet1.put("0x2a", new Instruction_R(null, "slt"));
        fSet1.put("0x2b", new Instruction_R(null, "sltu"));
        fSet1.put("0x30", new Instruction_R(null, "tge")); 
        fSet1.put("0x31", new Instruction_R(null, "tgeu")); 
        fSet1.put("0x32", new Instruction_R(null, "tlt")); 
        fSet1.put("0x33", new Instruction_R(null, "tltu")); 
        fSet1.put("0x34", new Instruction_R(null, "teq")); 
        fSet1.put("0x36", new Instruction_R(null, "tne")); 
        opCodeToFuncMap.put("0x00", fSet1);

        HashMap<String, ? super Instructions> fSet11 = new HashMap<>();
        fSet11.put("0x00", new Instruction_R(null, "movf"));
        fSet11.put("0x01", new Instruction_R(null, "movt"));
        opCodeToFuncMap.put("0x00/1", fSet11);

        HashMap<String, ? super Instructions> funcSet2 = new HashMap<>();
        funcSet2.put("0x00", new Instruction_I(null, "bltz"));
        funcSet2.put("0x01", new Instruction_I(null, "bgez"));
        funcSet2.put("0x02", new Instruction_I(null, "bltzl"));
        funcSet2.put("0x03", new Instruction_I(null, "bgezl"));
        funcSet2.put("0x08", new Instruction_I(null, "tgei"));
        funcSet2.put("0x09", new Instruction_I(null, "tgeiu"));
        funcSet2.put("0x0a", new Instruction_I(null, "tlti"));
        funcSet2.put("0x0b", new Instruction_I(null, "tltiu"));
        funcSet2.put("0x0c", new Instruction_I(null, "tegi"));
        funcSet2.put("0x0e", new Instruction_I(null, "tnei"));
        funcSet2.put("0x10", new Instruction_I(null, "bltzal"));
        funcSet2.put("0x11", new Instruction_I(null, "bgezal"));
        funcSet2.put("0x12", new Instruction_I(null, "bltzall"));
        funcSet2.put("0x13", new Instruction_I(null, "bgczall"));
        opCodeToFuncMap.put("0x01", funcSet2);


        HashMap<String, ? super Instructions>  funcSet3 = new HashMap<>();
        funcSet3.put("0x00", new Instruction_R(null, "madd"));
        funcSet3.put("0x01", new Instruction_R(null, "maddu"));
        funcSet3.put("0x02", new Instruction_R(null, "mul"));
        funcSet3.put("0x04", new Instruction_R(null, "msub"));
        funcSet3.put("0x05", new Instruction_R(null, "msubu"));
        funcSet3.put("0x20", new Instruction_R(null, "clz"));
        funcSet3.put("0x21", new Instruction_R(null, "clo"));
        opCodeToFuncMap.put("0x1c", funcSet3);

        HashMap<String, ? super Instructions>  funcSetRS = new HashMap<>();
        funcSetRS.put("0x00", new Instruction_R(null, "mfcz"));
        funcSetRS.put("0x01", new Instruction_R(null, "cfcz"));
        funcSetRS.put("0x04", new Instruction_R(null, "mtcz"));
        funcSetRS.put("0x06", new Instruction_R(null, "ctcz"));
        opCodeToFuncMap.put("rs", funcSetRS);


        HashMap<String, ? super Instructions>  funcSet4 = new HashMap<>();
        funcSet4.put("0x00", new Instruction_J(null, "bczf"));
        funcSet4.put("0x01", new Instruction_J(null, "bczt"));
        funcSet4.put("0x02", new Instruction_J(null, "bczfl"));
        funcSet4.put("0x03", new Instruction_J(null, "bcztl"));
        opCodeToFuncMap.put("rs/0x08", funcSet4);


        HashMap<String, ? super Instructions>  funcSet5 = new HashMap<>();
        funcSet5.put("0x01", new Instruction_J(null, "tlbr"));
        funcSet5.put("0x02", new Instruction_J(null, "tlbwi"));
        funcSet5.put("0x06", new Instruction_J(null, "tlbwr"));
        funcSet5.put("0x08", new Instruction_J(null, "tlbp"));
        funcSet5.put("0x18", new Instruction_J(null, "eret"));
        funcSet5.put("0x1f", new Instruction_J(null, "deret"));
        opCodeToFuncMap.put("rs/0x10/0", funcSet5);



        HashMap<String, ? super Instructions>  funcSet6 = new HashMap<>();
        funcSet6.put("0x00", new Instruction_R(null, "add.f"));
        funcSet6.put("0x01", new Instruction_R(null, "sub.f"));
        funcSet6.put("0x02", new Instruction_R(null, "mul.f"));
        funcSet6.put("0x03", new Instruction_R(null, "div.f"));
        funcSet6.put("0x04", new Instruction_R(null, "sqrt.f"));
        funcSet6.put("0x05", new Instruction_R(null, "abs.f"));
        funcSet6.put("0x06", new Instruction_R(null, "mov.f"));
        funcSet6.put("0x07", new Instruction_R(null, "neg.f"));
        funcSet6.put("0x0c", new Instruction_R(null, "round.w.f"));
        funcSet6.put("0x0d", new Instruction_R(null, "trunc.w.f"));
        funcSet6.put("0x0e", new Instruction_R(null, "cell.w.f"));
        funcSet6.put("0x0f", new Instruction_R(null, "floor.w.f"));
        funcSet6.put("0x12", new Instruction_R(null, "movz.f"));
        funcSet6.put("0x13", new Instruction_R(null, "movn.f"));
        funcSet6.put("0x20", new Instruction_R(null, "cvt.s.f"));
        funcSet6.put("0x21", new Instruction_R(null, "cvt.d.f"));
        funcSet6.put("0x24", new Instruction_R(null, "cvt.w.f"));
        funcSet6.put("0x30", new Instruction_R(null, "c.f.f"));
        funcSet6.put("0x31", new Instruction_R(null, "c.un.f"));
        funcSet6.put("0x32", new Instruction_R(null, "c.eq.f"));
        funcSet6.put("0x33", new Instruction_R(null, "c.ueq.f"));
        funcSet6.put("0x34", new Instruction_R(null, "c.olt.f"));
        funcSet6.put("0x35", new Instruction_R(null, "c.ult.f"));
        funcSet6.put("0x36", new Instruction_R(null, "c.ole.f"));
        funcSet6.put("0x37", new Instruction_R(null, "c.ule.f"));
        funcSet6.put("0x38", new Instruction_R(null, "c.sf.f"));
        funcSet6.put("0x39", new Instruction_R(null, "c.ngle.f"));
        funcSet6.put("0x3a", new Instruction_R(null, "c.seq.f"));
        funcSet6.put("0x3b", new Instruction_R(null, "c.ngl.f"));
        funcSet6.put("0x3c", new Instruction_R(null, "c.lt.f"));
        funcSet6.put("0x3d", new Instruction_R(null, "c.nge.f"));
        funcSet6.put("0x3e", new Instruction_R(null, "c.le.f"));
        funcSet6.put("0x3f", new Instruction_R(null, "c.ngt.f"));
        opCodeToFuncMap.put("rs/0x10/1", funcSet6);
        opCodeToFuncMap.put("rs/0x11/1", funcSet4);


        HashMap<String, ? super Instructions>  func0 = new HashMap<>();
        funcSet5.put("0x02", new Instruction_J(null, "j"));
        funcSet5.put("0x03", new Instruction_J(null, "jal"));
        funcSet5.put("0x04", new Instruction_I(null, "beq"));
        funcSet5.put("0x05", new Instruction_I(null, "bne"));
        funcSet5.put("0x06", new Instruction_I(null, "blez"));
        funcSet5.put("0x07", new Instruction_I(null, "bgtz"));
        funcSet5.put("0x08", new Instruction_I(null, "addi"));
        funcSet5.put("0x09", new Instruction_I(null, "addiu"));
        funcSet5.put("0x0a", new Instruction_I(null, "slti"));
        funcSet5.put("0x0b", new Instruction_I(null, "sltiu"));
        funcSet5.put("0x0c", new Instruction_I(null, "andi"));
        funcSet5.put("0x0d", new Instruction_I(null, "ori"));
        funcSet5.put("0x0e", new Instruction_I(null, "xori"));
        funcSet5.put("0x0f", new Instruction_I(null, "lui"));
        funcSet5.put("0x14", new Instruction_I(null, "beql"));
        funcSet5.put("0x15", new Instruction_I(null, "bnel"));
        funcSet5.put("0x16", new Instruction_I(null, "blezl"));
        funcSet5.put("0x17", new Instruction_I(null, "bgtzl"));
        funcSet5.put("0x20", new Instruction_I(null, "lb"));
        funcSet5.put("0x21", new Instruction_I(null, "lh"));
        funcSet5.put("0x22", new Instruction_I(null, "lwl"));
        funcSet5.put("0x23", new Instruction_I(null, "lw"));
        funcSet5.put("0x24", new Instruction_I(null, "lbu"));
        funcSet5.put("0x25", new Instruction_I(null, "lhu"));
        funcSet5.put("0x26", new Instruction_I(null, "lwr"));
        funcSet5.put("0x28", new Instruction_I(null, "sb"));
        funcSet5.put("0x29", new Instruction_I(null, "sh"));
        funcSet5.put("0x2a", new Instruction_I(null, "swl"));
        funcSet5.put("0x2b", new Instruction_I(null, "sw"));
        funcSet5.put("0x2e", new Instruction_I(null, "swr"));
        funcSet5.put("0x2f", new Instruction_I(null, "cache"));
        funcSet5.put("0x30", new Instruction_I(null, "ll"));
        funcSet5.put("0x31", new Instruction_I(null, "lwc1"));
        funcSet5.put("0x32", new Instruction_I(null, "lwc2"));
        funcSet5.put("0x33", new Instruction_I(null, "pref"));
        funcSet5.put("0x35", new Instruction_I(null, "ldc1"));
        funcSet5.put("0x36", new Instruction_I(null, "ldc2"));
        funcSet5.put("0x38", new Instruction_I(null, "sc"));
        funcSet5.put("0x39", new Instruction_I(null, "swc1"));
        funcSet5.put("0x3a", new Instruction_I(null, "swc2"));
        funcSet5.put("0x3d", new Instruction_I(null, "sdc1"));
        funcSet5.put("0x3e", new Instruction_I(null, "sdc2"));
        opCodeToFuncMap.put("op", funcSet5);

        // Register for float-operation instructions.
        floatRegister = new HashMap<>();
        floatRegister.put(0, "$f0");
        floatRegister.put(1, "$f1");
        floatRegister.put(2, "$f2");
        floatRegister.put(3, "$f3");
        floatRegister.put(4, "$f4");
        floatRegister.put(5, "$f5");
        floatRegister.put(6, "$f6");
        floatRegister.put(7, "$f7");
        floatRegister.put(8, "$f8");
        floatRegister.put(9, "$f9");
        floatRegister.put(10, "$f10");
        floatRegister.put(11, "$f11");
        floatRegister.put(12, "$f12");
        floatRegister.put(13, "$f13");
        floatRegister.put(14, "$f14");
        floatRegister.put(15, "$f15");
        floatRegister.put(16, "$f16");
        floatRegister.put(17, "$f17");
        floatRegister.put(18, "$f18");
        floatRegister.put(19, "$f19");
        floatRegister.put(20, "$f20");
        floatRegister.put(21, "$f21");
        floatRegister.put(22, "$f22");
        floatRegister.put(23, "$f23");
        floatRegister.put(24, "$f24");
        floatRegister.put(25, "$f25");
        floatRegister.put(26, "$f26");
        floatRegister.put(27, "$f27");
        floatRegister.put(28, "$f28");
        floatRegister.put(29, "$f29");
        floatRegister.put(30, "$f30");
        floatRegister.put(31, "$f31");

        // Normal registers.
        register = new HashMap<>();
        register.put(0, "$zero");
        register.put(1, "$at");
        register.put(2, "$v0");
        register.put(3, "$v1");
        register.put(4, "$a0");
        register.put(5, "$a1");
        register.put(6, "$a2");
        register.put(7, "$a3");
        register.put(8, "$t0");
        register.put(9, "$t1");
        register.put(10, "$t2");
        register.put(11, "$t3");
        register.put(12, "$t4");
        register.put(13, "$t5");
        register.put(14, "$t6");
        register.put(15, "$t7");
        register.put(16, "$s0");
        register.put(17, "$s1");
        register.put(18, "$s2");
        register.put(19, "$s3");
        register.put(20, "$s4");
        register.put(21, "$s5");
        register.put(22, "$s6");
        register.put(23, "$s7");
        register.put(24, "$t8");
        register.put(25, "$t9");
        register.put(26, "$k0");
        register.put(27, "$k1");
        register.put(28, "$gp");
        register.put(29, "$sp");
        register.put(30, "$fp/s8");
        register.put(31, "$ra");

    }

    /**
     * Returns the hashmap containing all other hashmaps that is used to identifying the functions of a
     * instruction.
     * @return The hashmap containing all HashMap mappings.
     */
    public HashMap<String, HashMap> getOpCodeToFuncMap() {
        return opCodeToFuncMap;
    }


    /**
     * Returns the hashmap containing all possible registers (not including float-registers).
     * @return Hashmap of registers.
     */
    public HashMap<Integer, String> getRegister() {
        return register;
    }

    /**
     * Returns the hashmap containing all possible float-registers (not including normal-registers).
     * @return
     */
    public HashMap<Integer, String> getFloatRegister() {
        return floatRegister;
    }


    /**
     * Given a hashmap of instruction the method deep-copies the instruction in the hashmap that has the key funcOP.
     * This is so the HashMap remains unchanged from future changes to the returned instruction.
     * @param instructions HashMap of functions/registers.
     * @param funcOP String containing hexadecimal-number to identify function to copy.
     * @return Independent instruction.
     * @throws NullPointerException
     */
    public HashMap<String, ? super Instructions> getInstruction(HashMap instructions, String funcOP)
            throws NullPointerException {

        if (instructions == null) {
            System.err.println("Error: No HashMap to retrieve instruction from.");
            return null;
        }

        Instructions instr = (Instructions) instructions.get(funcOP);

        if (instr != null) {

            HashMap<String, ? super Instructions> retMap = new HashMap<String, Instructions>();

            if (instr.getFormat().equals("R")) {
                Instruction_R instr2 = null;
                instr2 = new Instruction_R(null, instr.getFunc());
                instr2.setFormat(instr.getFormat());
                instr2.setBin(instr.getBin());
                retMap.put(instr2.getFormat(), instr2);

            } else if (instr.getFormat().equals("I")) {
                Instruction_I instr2 = null;
                instr2 = new Instruction_I(null, instr.getFunc());
                instr2.setFormat(instr.getFormat());
                instr2.setBin(instr.getBin());
                retMap.put(instr2.getFormat(), instr2);

            } else if (instr.getFormat().equals("J")) {
                Instruction_J instr2 = null;
                instr2 = new Instruction_J(null, instr.getFunc());
                instr2.setFormat(instr.getFormat());
                instr2.setBin(instr.getBin());
                retMap.put(instr2.getFormat(), instr2);
            }
            return retMap;
        }
        return null;
    }
}
