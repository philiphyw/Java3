/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day003teachersolution;

import java.io.File;
import java.util.Scanner;

public class Day003TeacherSolution {

    static String chooseProgramFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        File progFile = new File(chooseProgramFile());
        try (Scanner inputProg = new Scanner(progFile) ){
            while (inputProg.hasNextLine()) {
                    String[] data = inputProg.nextLine().split(";");
//                    data[0] is the the "order" text, like read, push, pop. print...;
                    switch (data[0]) {
                        case "read":
                            ensureValue(data.length,2,"Error:read requires text parameter");
                            System.out.println();
                            break;
                        case "push":
                            break;
                        case "pop":
                            break;
                        case "print":
                            break;
                        case "add":
                            break;
                        case "sub":
                            break;
                        case "mul":
                            break;
                        case "div":
                            break;
                        case "exchange":
                            break;
                        default:
                            System.out.println("Error: invalid command");
                            System.exit(0);
                            break;
                    }
                }
            }catch( Exception e ){
                
                }

        }

    private static void ensureValue(int length, int i, String errorread_requires_text_parameter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }
