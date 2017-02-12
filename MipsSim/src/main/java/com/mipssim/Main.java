package com.mipssim;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

public class Main
{

    /**
     * @param args args[0] = file to read instructions from,
     *             args[1] = file to write result to (optional).
     */
    public static void main(String[] args) {

        ArrayList<String> instructions;
        ArrayList<Output> result;


        boolean writeToFile = false;
        File wfile;
        Writer wr = null;

        instructions = new ArrayList<>();
        result       = new ArrayList<>();


        if (args.length < 1) {
            System.err.println("\nError: No input-file to read data from.");
            return;
        }

        if (args.length > 1) {
            wfile = new File(args[1]);
            System.out.println("\nFile to write: <" + args[1] + ">");
            try {
                wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1])));
                writeToFile = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // Optional to give a file to write result to. Either-way the result will be presented in terminal.
            System.err.println("\nNo file to write result to was given.");
        }

        try {

            File fileToRead = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(fileToRead));

            System.out.println("\nFile to read: <" + args[0] + ">");
            System.out.println("------------------------------------------------------------------------------------" +
                    "-----------------------");
            String line;

            while((line = br.readLine()) != null) {

                String bin = null;

                try {

                    if (line.startsWith("0x")) {
                        // Line == hex-number!
                        if ((line.length() > 0) && (line.substring(0, 2).equalsIgnoreCase("0x"))) {
                            // Valid Hex-code
                            String subHex = line.substring(2);
                            bin = new BigInteger(subHex, 16).toString(2);
                        } else {
                            System.err.println("Error: Unknown Hex-line in file at line " + line + ".");
                        }

                    } else {
                        // Line == decimal-number!
                        bin = Integer.toBinaryString(Integer.parseUnsignedInt(line));
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Error: Input not a number.");
                    continue;
                }

                if (bin != null) {

                    // Pad binary number if less than 32 bits with 0s.
                    while (bin.length() < 32) {
                        bin = "0" + bin;
                    }

                    instructions.add(bin);
                }
            }

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create Mapping object which identifies instructions.
        Mappings map = new Mappings();

        // Parse every read-instruction and print it's function according to MIPS-architecture.
        Iterator it = instructions.iterator();

        // Line number
        int j = 0;

        System.out.println("");
        System.out.printf("%-10s\t\t%-5s\t%-25s %-35s %-35s %n", "Instruction", "Format", "DecComp", "HexComp", "Mnemonic");
        System.out.println("------------------------------------------------------------------------------------" +
                "-----------------------");

        while(it.hasNext()) {

            String bin = (String) it.next();
            j++;

            // Parse the instruction and get Output to print/write to file.
            Output res = Disassembler.parseBin(bin, j, map);
            if (res == null)
                continue;
            res.setInput("0x".concat(Integer.toHexString(Disassembler.binaryToDec(bin))));

            result.add(res);

            System.out.printf("%-10s\t\t%-5s\t%-25s %-35s %-35s %n", res.getInput(), res.getFormat(),
                    res.getDecDecomp(), res.getHexDecomp(),
                    res.getMnemonic());

            if (writeToFile && (wr != null)) {
                try {

                    wr.write(String.format("%-10s\t\t%-5s\t%-25s %-35s %-35s %n", res.getInput(), res.getFormat(),
                            res.getDecDecomp(), res.getHexDecomp(),
                            res.getMnemonic()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        if (wr != null) {
            try {
                wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return;
    }


}
