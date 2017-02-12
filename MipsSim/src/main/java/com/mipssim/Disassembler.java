package com.mipssim;

import com.mipssim.instructions.Instruction_I;
import com.mipssim.instructions.Instruction_J;
import com.mipssim.instructions.Instruction_R;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Disassembler {

    /**
     * binaryToHex converts a 32-bit binary number into an hexadecimal-number.
     * @param bin String containing the binary number (32-bit) to convert into an hexadecimal number.
     * @return String containing the hex-representation of bin.
     */
    public static String binaryToHex(String bin){
        // To decimal
        int dec = Integer.parseUnsignedInt(bin, 2);

        if (dec < 16) // 16 == 10
            return "0x0"+Integer.toHexString(dec);
        else
            return "0x"+Integer.toHexString(dec);
    }

    /**
     * binaryToDec converts a 32-bit binary number into an decimal-number.
     * Method follows the algorithm on page 76 in the course book.
     * @param bin String containing the binary number (32-bit) to convert into an decimal number.
     * @return String containing the decimal-representation of bin.
     */
    public static int binaryToDec(String bin){

        int lenBin  = bin.length();
        double sum1 = -(Character.digit(bin.charAt(0), 2)*(Math.pow(2, lenBin-1)));
        double sum2 = 0;

        for (int i = 0; i < lenBin-1; i++) {
            sum2 = sum2 + (Character.digit(bin.charAt(i+1), 2)*Math.pow(2, lenBin-1-(i+1)));
        }
        double sum3 = sum1+sum2;
        return (int) sum3;
    }


    /**
     * Converts a decimal number into a hexadecimal number (same value).
     * @param decVal The integer to convert into a hexadecimal-number.
     * @return String - Hexadecimal number.
     */
    public static String decimalToHex(int decVal) {
        if (decVal < 10)
            return "0x0".concat(Integer.toHexString(decVal));
        return "0x".concat(Integer.toHexString(decVal));
    }


    /**
     * Sums the computed part and returns the mnemonic format of an instruction. E.g. "addi $r2, r$1, 5"
     *
     * @param format The format of the instruction.
     * @param func The function when converting a instruction to mnemonic
     * @param rd The destination register when converting a instruction to mnemonic
     * @param rs The first source register when converting a instruction to mnemonic
     * @param rt The second source register when converting a instruction to mnemonic
     * @param immediate The immediate value when converting a I-instruction to mnemonic
     * @param target    The target when converting a J-instruction to mnemonic
     * @return String containing the mnemonic-format of the instruction.
     */
    public static String toMnemonic(String format, String func, String rd, String rs, String rt, String immediate,
                                    String target) {

        if (func == null)
            return null;

        boolean isFirstParam = true;

        String mnemonic = func + " ";


        if (format.equals("R")) {

            // If R-instruction...

            // Param is null then exclude from mnemonic.

            if (rd != null) {
                mnemonic     = mnemonic.concat(rd);
                isFirstParam = false;
            }
            if (rs != null) {
                if (isFirstParam) {
                    mnemonic     = mnemonic.concat(rs);
                    isFirstParam = false;
                } else {
                    mnemonic = mnemonic.concat(", ").concat(rs);
                }
            }
            if (rt != null) {
                if (isFirstParam) {
                    mnemonic     = mnemonic.concat(rt);
                    isFirstParam = false;
                } else {
                    mnemonic = mnemonic.concat(", ").concat(rt);
                }
            }

        } else if (format.equals("I")) {

            // If I-instruction...

            if (rt != null) {
                mnemonic     = mnemonic.concat(rt);
                isFirstParam = false;
            }
            if (rs != null) {
                if (isFirstParam) {
                    mnemonic     = mnemonic.concat(rs);
                    isFirstParam = false;
                } else {
                    mnemonic = mnemonic.concat(", ").concat(rs);
                }
            }
            if (immediate != null) {
                if (isFirstParam) {
                    mnemonic     = mnemonic.concat(immediate);
                    isFirstParam = false;
                } else {
                    mnemonic = mnemonic.concat(", ").concat(immediate);
                }
            }
        } else if (format.equals("I1")) {
            // If I-instruction (alternative for special instructions)...

            if (rs != null) {
                mnemonic     = mnemonic.concat(rs);
                isFirstParam = false;
            }
            if (rt != null) {
                if (isFirstParam) {
                    mnemonic     = mnemonic.concat(rt);
                    isFirstParam = false;
                } else {
                    mnemonic = mnemonic.concat(", ").concat(rt);
                }
            }
            if (immediate != null) {
                if (isFirstParam) {
                    mnemonic     = mnemonic.concat(immediate);
                    isFirstParam = false;
                } else {
                    mnemonic = mnemonic.concat(", ").concat(immediate);
                }
            }

        } else if (format.equals("I2")) {

            // If I-instruction (alternative 2 for special instructions)...

            if (rt != null) {
                mnemonic     = mnemonic.concat(rt);
                isFirstParam = false;
            }
            if (immediate != null) {
                if (isFirstParam) {
                    mnemonic     = mnemonic.concat(immediate);
                    isFirstParam = false;
                } else {
                    mnemonic = mnemonic.concat(", ").concat(immediate);
                }
            }
            if (rs != null) {
                mnemonic = mnemonic.concat("(").concat(rs).concat(")");
            }
            
        } else if (format.equals("J")) {
            // If J-instruction...

            if (target != null) {
                mnemonic = mnemonic.concat(target);
            }

        } else {
            // etc eret, deret, break
            // Do nothing.
        }

        return mnemonic;
    }


    /**
     * Determines if function is a branch-function.
     * @param func Function to inspect.
     * @return True if branch-function, else False.
     */
    public static boolean isBranchFunction(String func) {
        return (func.startsWith("b") && !func.startsWith("bc")) ? true : false;
    }

    /**
     * Returns the decomposition of n parameters where n=0:5 (6 = max fractions in binary number accordingly to MIPS
     * architecture). Works for all decompositions. Decomposition is of form "[op0, ... , op5]"
     * @return The decomposition.
     */
    public static String getDecomp(String op0, String op1, String op2, String op3, String op4,
                                   String op5) {

        int item = 0;

        item = (op0 != null)     ? item = item + 1 : item;
        item = (op1 != null)     ? item = item + 1 : item;
        item = (op2 != null)     ? item = item + 1 : item;
        item = (op3 != null)     ? item = item + 1 : item;
        item = (op4 != null)     ? item = item + 1 : item;
        item = (op5 != null)     ? item = item + 1 : item;

        String decomp = "[";

        for (int i = 0; i < item; i++) {

            // If param is not null then include in decomposition. Independent of format of param.

            if ((op0 != null) && (i == 0)) {
                decomp = decomp.concat(op0);
                continue;
            }
            if ((op1 != null) && (i == 1)) {
                decomp = decomp.concat(" ").concat(op1);
                continue;
            }
            if ((op2 != null) && (i == 2)) {
                decomp = decomp.concat(" ").concat(op2);
                continue;
            }
            if ((op3 != null) && (i == 3)) {
                decomp = decomp.concat(" ").concat(op3);
                continue;
            }
            if ((op4 != null) && (i == 4)) {
                decomp = decomp.concat(" ").concat(op4);
                continue;
            }
            if ((op5 != null) && (i == 5)) {
                decomp = decomp.concat(" ").concat(op5);
                continue;
            }
        }

        decomp = decomp.concat("]");
        return decomp;
    }

    /**
     * parseBin disassembles a given binary-instruction (32-bit) and identifies it's function, format and registers.
     * @param instruction Instruction to disassemble
     * @param srcLine Line of instruction in file it was read from.
     * @param mapper Mappings object.
     * @return Output - Result of disassembling of the given binary instruction.
     */
    public static Output parseBin(String instruction, int srcLine, Mappings mapper) {

        // Decompositions to return when parseBin is done.
        String mnemonic  = null;
        String hexDecomp = null;
        String decDecomp = null;
        String format    = null;

        // Get all HashMaps which contains functions.
        HashMap<String, HashMap> func = mapper.getOpCodeToFuncMap();

        if (binaryToDec(instruction) == 0) {
            // If bin is equal to 0 then return nop-instruction.
            mnemonic  = "nop";
            hexDecomp = "[0x00]";
            decDecomp = "[0]";
            format    = "?";

        } else {

            // The most significant bits.
            String op_bin = instruction.substring(0, 6);
            String opfield = binaryToHex(op_bin);

            // If the six most significant bits equals...
            if (opfield.equals("0x00")) {

                // Retrieve and inspect the six least significant bits (func-field in OP-map).
                String hexFunc = binaryToHex(instruction.substring(26, 32));

                // If func equals 0x01 then get another HashMap to inspect else stick to the old one.
                HashMap<String, HashMap> opSet = null;
                if (hexFunc.equals("0x01")) {

                    opSet = func.get(opfield.concat("/1"));

                    // Use the 15:th bit to retrieve the function.
                    hexFunc = binaryToHex(instruction.substring(15, 16));

                } else {
                    opSet = func.get(opfield);
                }

                // Get HashMap containing format of instruction and instruction.
                HashMap<String, ?> arr = mapper.getInstruction(opSet, hexFunc);
                if (arr == null) {
                    System.err.println(srcLine + "| Error: Func_Hex: " + hexFunc + " does not exist in mapping.");
                    return null;
                }

                // Swap back to func bits. Only necessary if hexFunc was swapped earlier.
                hexFunc = binaryToHex(instruction.substring(26, 32));


                // Get instruction and registers.
                Instruction_R rIntr = (Instruction_R) arr.get("R");
                int iRD     = Integer.parseInt(instruction.substring(16, 21), 2);
                String rd   = mapper.getRegister().get(iRD);
                int iRT     = Integer.parseInt(instruction.substring(11, 16), 2);
                String rt   = mapper.getRegister().get(iRT);
                int iRS     = Integer.parseInt(instruction.substring(6, 11), 2);
                String rs   = mapper.getRegister().get(iRS);


                // Special exceptions of the mnemonic syntax (order of registers etc).
                int funcDec = Integer.parseInt(hexFunc.substring(2), 16);
                if (funcDec == 8)
                    // "jr" has special syntax "jr rs" (excluding rt and rd).
                    mnemonic = toMnemonic("R", rIntr.getFunc(), null, rs, null, null, null);
                else if (funcDec == 9) {
                    // "jalr" has only rs and rd in syntax, "jalr rs, rd"
                    mnemonic = toMnemonic("R", rIntr.getFunc(), rd, rs, null, null, null);
                } else if ((funcDec == 12) || (funcDec == 13) || (funcDec == 15)) {
                    // System calls eg. break, syscall, sync etc.
                    mnemonic = toMnemonic("R", rIntr.getFunc(), null, null, null, null, null);
                } else if (((funcDec > 11) && (funcDec < 28)) || ((funcDec > 47) && (funcDec < 55))) {
                    // Following functions excludes rd from syntax. E.g. mult -> "mult rs, rt"
                    mnemonic = toMnemonic("R", rIntr.getFunc(), null, rs, rt, null, null);
                } else {
                    // Normal R-type instructions.
                    mnemonic = toMnemonic("R", rIntr.getFunc(), rd, rs, rt, null, null);
                }

                // Get decompositions of retrieved function, format and registers.
                hexDecomp = getDecomp(opfield, decimalToHex(iRS), decimalToHex(iRT), decimalToHex(iRD), "0", hexFunc);
                decDecomp = getDecomp(Integer.toString(binaryToDec(op_bin)), Integer.toString(iRS),
                                      Integer.toString(iRT), Integer.toString(iRD), "0",
                                      Integer.toString(binaryToDec(instruction.substring(26, 32))));
                format    = rIntr.getFormat();


            } else if (opfield.equals("0x01")) {

                // If op=0x01 then retrieve new hashmap (RT in OP-map) from bits(11:15).

                HashMap<String, HashMap> opSet = func.get(opfield);
                String rt_hex = binaryToHex(instruction.substring(11, 16));

                // Get HashMap containing format of instruction and instruction.
                HashMap<String, ?> arr = mapper.getInstruction(opSet, rt_hex);
                if (arr == null) {
                    System.err.println(srcLine + "| Error: Func_Hex: " + rt_hex + " does not exist in mapping.");
                    return null;
                }

                // Get instruction and registers.
                Instruction_I iIntr = (Instruction_I) arr.get("I");
                int iRS          = Integer.parseInt(instruction.substring(6, 11), 2);
                String rs        = mapper.getRegister().get(iRS);
                String label_Hex = binaryToHex(instruction.substring(17, 32));

                // Get decompositions of retrieved function, format and registers.
                mnemonic  = toMnemonic("I", iIntr.getFunc(), null, rs, null, label_Hex, null);
                hexDecomp = getDecomp(opfield, decimalToHex(iRS), rt_hex, label_Hex, null, null);
                decDecomp = getDecomp(Integer.toString(binaryToDec(op_bin)), Integer.toString(iRS),
                                      Integer.toString(binaryToDec(instruction.substring(11, 16))),
                                      Integer.toString(binaryToDec(instruction.substring(17, 32))), null, null);
                format    = iIntr.getFormat();


            } else if (opfield.equals("0x1c")) {

                // If OP=0x1c...
                // Retrieve and inspect the six least significant bits (func-field 2 in OP-map).

                HashMap<String, HashMap> opSet = func.get(opfield);

                String hexFunc  = binaryToHex(instruction.substring(26, 32));
                int decFunc     = Integer.parseInt(instruction.substring(26, 32), 2);


                // Get HashMap containing format of instruction and instruction.
                HashMap<String, ?> arr = mapper.getInstruction(opSet, hexFunc);
                if (arr == null) {
                    System.err.println(srcLine + "| Error: Func_Hex: " + hexFunc + " does not exist in mapping.");
                    return null;
                }

                // Get instruction and registers.
                Instruction_R rIntr = (Instruction_R) arr.get("R");
                int iRD     = Integer.parseInt(instruction.substring(16, 21), 2);
                String rd   = mapper.getRegister().get(iRD);
                int iRT     = Integer.parseInt(instruction.substring(11, 16), 2);
                String rt   = mapper.getRegister().get(iRT);
                int iRS     = Integer.parseInt(instruction.substring(6, 11), 2);
                String rs   = mapper.getRegister().get(iRS);


                // Special exceptions of the mnemonic syntax (order of registers etc).
                if (decFunc == 2) // Format for "mul"
                    mnemonic = toMnemonic("R", rIntr.getFunc(), rd, rs, rt, null, null);
                else if (decFunc > 6) // format for clo, clz
                    mnemonic = toMnemonic("R", rIntr.getFunc(), rd, rs, null, null, null);
                else // Normal format.
                    mnemonic = toMnemonic("R", rIntr.getFunc(), null, rs, rt, null, null);

                // Get decompositions of retrieved function, format and registers.
                hexDecomp = getDecomp(opfield, decimalToHex(iRS), decimalToHex(iRT), decimalToHex(iRD), "0", hexFunc);
                decDecomp = getDecomp(Integer.toString(binaryToDec(op_bin)), Integer.toString(iRS),
                                      Integer.toString(iRT), Integer.toString(iRD), "0",
                                      Integer.toString(decFunc));
                format    = rIntr.getFormat();


            } else if (opfield.equals("0x10") || opfield.equals("0x11") || opfield.equals("0x12")) {

                // If OP equals 0x10, 0x11 or 0x12...

                // Variables that certain character in the functions depends on.
                char f = 'z';
                int  z = -1;
                     z = opfield.equals("0x10") ? 0 : z;
                     z = opfield.equals("0x11") ? 1 : z;
                     z = opfield.equals("0x12") ? 2 : z;

                String rs_bin = instruction.substring(6, 11);
                String rs_hex = binaryToHex(rs_bin);

                // Inspect the RS-hashmap in OP-map that corresponds to bits(6:10).
                if (rs_hex.equals("0x00") || rs_hex.equals("0x02") || rs_hex.equals("0x04") || rs_hex.equals("0x06")) {

                    // If rs-bits equals 0x00, 0x02, 0x04 or ox06 then function is in same hashmap.

                    HashMap<String, HashMap> funcs = func.get("rs");

                    // Get HashMap containing format of instruction and instruction.
                    HashMap<String, ?> arr = mapper.getInstruction(funcs, rs_hex);
                    if (arr == null) {
                        System.err.println(srcLine + "| Error: Func_Hex: " + rs_hex + " does not exist in mapping.");
                        return null;
                    }

                    // Get instruction and registers.
                    Instruction_R rIntr = (Instruction_R) arr.get("R");
                    rIntr.setFunc(rIntr.getFunc().replace('z', Integer.toString(z).charAt(0)));
                    int ift     = Integer.parseInt(instruction.substring(11, 16), 2);
                    String ft   = (z == 0) ? mapper.getRegister().get(ift) : mapper.getFloatRegister().get(ift);
                    int ifs     = Integer.parseInt(instruction.substring(16, 21), 2);
                    String fs   = (z == 0) ? mapper.getRegister().get(ifs) : mapper.getFloatRegister().get(ifs);


                    // Get decompositions of retrieved function, format and registers.
                    mnemonic  = toMnemonic("R", rIntr.getFunc(), null, fs, ft, null, null);
                    hexDecomp = getDecomp(opfield, rs_hex, decimalToHex(ift), decimalToHex(ifs), null, "0");
                    decDecomp = getDecomp(Integer.toString(binaryToDec(op_bin)), Integer.toString(binaryToDec(rs_bin)),
                                          Integer.toString(ift), Integer.toString(ifs), null, "0");
                    format    = rIntr.getFormat();

                } else if (rs_hex.equals("0x08")) {

                    // If rs-bits equals 0x08...

                    // Depending on value of z the new hashmap is different.
                    if ((z == 1) || (z == 2)) {

                        String key = "rs/".concat(rs_hex);
                        HashMap<String, HashMap> funcs = func.get(key);

                        String func_bin = instruction.substring(14, 15);
                        String func_hex = binaryToHex(func_bin);

                        // Get HashMap containing format of instruction and instruction.
                        HashMap<String, ?> arr = mapper.getInstruction(funcs, func_hex);
                        if (arr == null) {
                            System.err.println(srcLine + "| Error: Func_Hex: " + func_hex + " does not exist in mapping.");
                            return null;
                        }


                        Instruction_J jIntr = (Instruction_J) arr.get("J");
                        jIntr.setFunc(jIntr.getFunc().replace('z', Integer.toString(z).charAt(0)));

                        String target_bin = instruction.substring(6, 32);
                        String target_hex = binaryToHex(target_bin);


                        // Get decompositions of retrieved function, format and registers.
                        mnemonic  = toMnemonic("J", jIntr.getFunc(), null, null, null, null, target_hex);

                        // Warning: These instructions have following decomp-layout [op rs func target] since they do
                        // not follow the original J-format (only op and rest target) but the syntax is most similar
                        // to J-format thus Instruction_J.
                        hexDecomp = getDecomp(opfield, func_hex, rs_hex, target_hex, null, null);
                        decDecomp = getDecomp(Integer.toString(binaryToDec(op_bin)),
                                              Integer.toString(binaryToDec(rs_bin)),
                                              Integer.toString(binaryToDec(func_bin)),
                                              Integer.toString(binaryToDec(target_bin)), null, null);
                        format    = jIntr.getFormat();

                    } // If z=0 do nothing.

                } else if (rs_hex.equals("0x10")) {

                    // If rs-bits equals 0x10...

                    String key = "rs/".concat(rs_hex).concat("/").concat(Integer.toString(z)); // rs/0x10/0
                    HashMap<String, HashMap> funcs = func.get(key);

                    // Depending on value of z the new hashmap is different.
                    if (z == 0) {

                        String func_bin = instruction.substring(27, 32);
                        String func_hex = binaryToHex(func_bin);
                        int funcDec     = Integer.parseUnsignedInt(func_bin, 2);

                        // Get HashMap containing format of instruction and instruction.
                        HashMap<String, ?> arr = mapper.getInstruction(funcs, func_hex);
                        if (arr == null) {
                            System.err.println(srcLine + "| Error: Func_Hex: " + func_hex + " does not exist in mapping.");
                            return null;
                        }

                        // Get decompositions of retrieved function, format and registers.
                        Instruction_J sysIntr = (Instruction_J) arr.get("J");
                        mnemonic  = toMnemonic("syscall", sysIntr.getFunc(), null, null, null, null, null);
                        hexDecomp = getDecomp(opfield, binaryToHex(instruction.substring(6, 26)), null, null, null, func_hex);
                        decDecomp = getDecomp(Integer.toString(binaryToDec(op_bin)),
                                              Integer.toString(binaryToDec(instruction.substring(6, 26))),
                                              null, null, null, Integer.toString(binaryToDec(func_bin)));
                        format    = sysIntr.getFormat();

                    } else if (z == 1) {

                        f = 's';

                        String func_bin = instruction.substring(27, 32);
                        String func_hex = binaryToHex(func_bin);
                        int funcDec     = Integer.parseUnsignedInt(func_bin, 2);

                        // Get HashMap containing format of instruction and instruction.
                        HashMap<String, ?> arr = mapper.getInstruction(funcs, func_hex);
                        if (arr == null) {
                            System.err.println(srcLine + "| Error: Func_Hex: " + func_hex + " does not exist in mapping.");
                            return null;
                        }

                        // Replace 'f' at the end with f.
                        Instruction_R rIntr = (Instruction_R) arr.get("R");
                        String function = rIntr.getFunc();
                        function = function.replace(function.charAt(function.length() - 1), f);
                        rIntr.setFunc(function);

                        // Get registers.
                        int ift     = Integer.parseInt(instruction.substring(11, 16), 2);
                        String ft   = mapper.getFloatRegister().get(ift);
                        int ifs     = Integer.parseInt(instruction.substring(16, 21), 2);
                        String fs   = mapper.getFloatRegister().get(ifs);
                        int ifd     = Integer.parseInt(instruction.substring(21, 26), 2);
                        String fd   = mapper.getFloatRegister().get(ifd);

                        // Get decompositions of retrieved function, format and registers.
                        mnemonic  = toMnemonic("R", rIntr.getFunc(), fd, fs, ft, null, null);
                        hexDecomp = getDecomp(opfield, decimalToHex(ifs), decimalToHex(ift), decimalToHex(ifd), "0",
                                              func_hex);
                        decDecomp = getDecomp(Integer.toString(binaryToDec(op_bin)), Integer.toString(ifs),
                                              Integer.toString(ift), Integer.toString(ifd), "0",
                                              Integer.toString(binaryToDec(func_bin)));
                        format    = rIntr.getFormat();

                    }

                } else if (rs_hex.equals("0x11")) {

                    // If rs-bits equals 0x10...

                    // Depending on value of z the new hashmap is different.
                    if (z == 1) {

                        f = 'd';

                        String key = "rs/".concat(rs_hex).concat("/").concat(Integer.toString(z)); // "rs/0x11/1"
                        HashMap<String, HashMap> funcs = func.get(key);

                        String func_bin = instruction.substring(27, 32);
                        String func_hex = binaryToHex(func_bin);
                        int funcDec     = Integer.parseUnsignedInt(func_bin, 2);

                        // Get HashMap containing format of instruction and instruction.
                        HashMap<String, ?> arr = mapper.getInstruction(funcs, func_hex);
                        if (arr == null) {
                            System.err.println(srcLine + "| Error: Func_Hex: " + func_hex + " does not exist in mapping.");
                            return null;
                        }

                        // Replace 'f' at the end with f.
                        Instruction_R rIntr = (Instruction_R) arr.get("R");
                        String function = rIntr.getFunc();
                        function = function.replace(function.charAt(function.length() - 1), f);
                        rIntr.setFunc(function);

                        // Get registers.
                        int ift   = Integer.parseInt(instruction.substring(11, 16), 2);
                        String ft = mapper.getFloatRegister().get(ift);
                        int ifs   = Integer.parseInt(instruction.substring(16, 21), 2);
                        String fs = mapper.getFloatRegister().get(ifs);
                        int ifd   = Integer.parseInt(instruction.substring(21, 26), 2);
                        String fd = mapper.getFloatRegister().get(ifd);

                        // Get decompositions of retrieved function, format and registers.
                        mnemonic  = toMnemonic("R", rIntr.getFunc(), fd, fs, ft, null, null);
                        hexDecomp = getDecomp(opfield, binaryToHex(instruction.substring(16, 21)),
                                              binaryToHex(instruction.substring(11, 16)),
                                              binaryToHex(instruction.substring(21, 26)), "0", func_hex);
                        decDecomp = getDecomp(binaryToHex(op_bin), Integer.toString(ifs), Integer.toString(ift),
                                              Integer.toString(ifd), "0", Integer.toString(funcDec));
                        format    = rIntr.getFormat();

                    } // else do nothing
                }

            } else {

                // Instruction is in OP-field...

                HashMap<String, HashMap> funcs = func.get("op");

                // Get HashMap containing format of instruction and instruction.
                HashMap<String, ?> arr = mapper.getInstruction(funcs, opfield);
                if ((arr == null) || (arr.size() == 0)) {
                    System.err.println(srcLine + "| Error: No opfield=" + opfield + " exists in set 'OP'");
                    return null;
                }


                int funcDec = Integer.parseInt(opfield.substring(opfield.length() - 2, opfield.length()), 16);

                // The first 3 instructions are J-instructions, rest are I-instructions.
                if (funcDec < 4) {

                    Instruction_J jIntr = (Instruction_J) arr.get("J");
                    String function     = jIntr.getFunc();

                    String target_Bin = instruction.substring(6, 32);
                    String target_Hex = binaryToHex(target_Bin);

                    // Get decompositions of retrieved function, format and registers.
                    mnemonic    = toMnemonic("J", jIntr.getFunc(), null, null, null, null, target_Hex);
                    hexDecomp   = getDecomp(opfield, target_Hex, null, null, null, null);
                    decDecomp   = getDecomp(Integer.toString(binaryToDec(op_bin)),
                                            Integer.toString(binaryToDec(target_Bin)), null, null, null, null);
                    format      = jIntr.getFormat();

                } else {

                    Instruction_I iIntr = (Instruction_I) arr.get("I");
                    String function     = iIntr.getFunc();

                    // If func deals with coproc 1 then use float register instead of rt
                    boolean ftFloat = (function.endsWith("1")) ? true : false;

                    // Get registers.
                    int irs     = Integer.parseInt(instruction.substring(6, 11), 2);
                    String rs   = mapper.getRegister().get(irs);
                    int irt     = Integer.parseInt(instruction.substring(11, 16), 2);
                    String xt   = (ftFloat) ? mapper.getFloatRegister().get(irt) : mapper.getRegister().get(irt);


                    // Check if function is a branch-function. If so then immed needs to be +1 (starts with NEXT
                    // instruction).
                    String  immed = Integer.toString(binaryToDec(instruction.substring(16, 32)));
                            immed = (isBranchFunction(function)) ?
                                    Integer.toString(Integer.parseUnsignedInt(immed) + 1) : immed;

                    // Special exceptions of the mnemonic syntax (order of registers etc).
                    if (funcDec < 15) { // Format: funct rs, rt/ft, immed
                        if (isBranchFunction(function)) {
                            // Branch has reverse order of params. E.g. beq rs, rt, imm (instead of ... rt, rs, ...)
                            mnemonic = toMnemonic("I1", iIntr.getFunc(), null, rs, xt, immed, null);
                        } else {
                            // Normal format.
                            mnemonic = toMnemonic("I", iIntr.getFunc(), null, rs, xt, immed, null);
                        }
                    } else {
                        // Format: func rs, immed(rt/ft)
                        mnemonic = toMnemonic("I2", iIntr.getFunc(), null, rs, xt, immed, null);
                    }

                    hexDecomp = getDecomp(opfield, binaryToHex(instruction.substring(6, 11)),
                                          binaryToHex(instruction.substring(11, 16)), binaryToHex(instruction.substring(16, 32)),
                                          null, null);
                    decDecomp = getDecomp(Integer.toString(binaryToDec(op_bin)), Integer.toString(irs),
                                          Integer.toString(irt), immed, null, null);
                    format    = iIntr.getFormat();

                }
            }
        }

        return new Output(format, hexDecomp, decDecomp, mnemonic, srcLine);
    }

}


