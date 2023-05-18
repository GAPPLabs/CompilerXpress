package com.gapplabs.model.analysis;

import com.gapplabs.model.analysis.util.Functions;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptimisationAnalysis {

    private final String regexFunction;
    private final String regexCallFunction;

    private final String test = """
            ent_ ISC111 , ISC112 , ISC113 ;
            dec_ ISC211 ;
            car_ ISC311 ;
                                            
            ISC111 = ISC112 * ISC113 ; 
            ISC211 = ISC112 ;
                                            
            ent_ ISC333 ( ent_ ISC914 )
            {
            ISC914 = 5235 * ISC113 ;
            return ISC914 ;
            }
                                            
            ent_ ISC334 ( ent_ ISC915 )
            {
                                            
            ISC915 = ISC333 ( ISC915 ) ;
            return ISC915 ;
            }
                                            
            ent_ ISC335 ( ent_ ISC914 )
            {
            ISC914 = 5235 * ISC113 ;
            return ISC914 ;
            }
                                            
            ent_ ISC336 ( ent_ ISC914 )
            {
            ISC914 = ISC335 ( ISC914 , ISC913 , ISC916 ) ;
            return ISC914 ;
            }
                                            
            ISC111 = ISC333 ( 555 ) ;
            """;

    public OptimisationAnalysis() {
        this.regexFunction = "(ent_|dec_|car_)\\s*(ISC\\d{3})\\s*\\((.*?)\\)\\s*\\{((?s).*?)\\}";
        this.regexCallFunction = "\\s*(ISC\\d{3})\\s*\\((.*?)\\)\\s*;";
    }

    private Functions[] chechFunctionOptimizer(String text) {
        Pattern patternFunction = Pattern.compile(regexFunction);
        Matcher matcherFunction = patternFunction.matcher(text);

        while (matcherFunction.find()) {
            Pattern patternCall = Pattern.compile(regexCallFunction);
            Matcher matcherCall = patternCall.matcher(matcherFunction.group());
            while (matcherCall.find()) {
                Functions functionBase = decomposingFuction(matcherFunction, true);
                Functions fuctionCall = decomposingFuction(matcherCall, false);
                Functions functionRemove = searchFunction(text, fuctionCall.getIdentify());
                return new Functions[]{functionBase, fuctionCall, functionRemove};
            }
        }
        return null;
    }

    private Functions searchFunction(String text, String identify) {
        Pattern patternFunction = Pattern.compile(regexFunction);
        Matcher matcherFunction = patternFunction.matcher(text);

        while (matcherFunction.find()) {
            if (matcherFunction.group(2).equals(identify)) {
                return decomposingFuction(matcherFunction, true);
            }
        }
        return null;
    }

    private Functions decomposingFuction(Matcher matcher, boolean function) {
        String identify = matcher.group(function ? 2 : 1);
        String[] params = matcher.group(function ? 3 : 2)
                .replaceAll("(ent_|dec_|car_)", "")
                .split("\\s+,\\s+|\\s+");
        params = Arrays.stream(params).filter(str -> !str.isEmpty()).toArray(String[]::new);
        if (function) {
            String body = matcher.group(4);
            int start = matcher.start();
            int end = matcher.end();
            System.out.println("Identify: " + identify);
            System.out.println("Params: " + Arrays.toString(params));
            System.out.println("Body: " + body);
            System.out.println("Start position: " + start);
            System.out.println("End position: " + end);
            return new Functions(identify, params, body, start, end, function, matcher);
        } else {
            System.out.println("Identify: " + identify);
            System.out.println("Params: " + Arrays.toString(params));
            return new Functions(identify, params, null, -1, -1, function, matcher);
        }
    }

    public String compactBody(Functions body0, Functions body2, Functions call) {
        System.out.println("EMPIEZA LA FUNCIÓN DE COMPACT BODY");
        String textBody2 = body2.getBody();
        String textBody0 = body0.getBody();
//        System.out.println(textBody2);
        // Iterando cada parámetro pra hacer el reemplazo de los nombres de las variables
        for (int i = 0; i < call.getParams().length; i++) {

            textBody2 = textBody2.replace(body2.getParams()[i], call.getParams()[i]);
//            System.out.print(textBody2.indexOf(body2.getParams()[i]));
        }
//        System.out.println(textBody2);
        // Extrayendo las líneas de código antes del return y se hace la separación por línea
        String stringBeforeReturn = textBody2.split("return")[0];
        System.out.println("haciendo el split");
//        System.out.println(stringBeforeReturn);


        // Encontrando la línea anterior a la invocación del método a remover
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.addAll(List.of(textBody0.split("\n")));

        // Código para intentar eliminar los espacios / líneas en blanc0. Probablemente se tenga que hacer un último
        // procesamiento para eliminar el \n\n porque no sé en qué parte se están duplicando
        stringArrayList.remove(" ");

        for (int i = 0; i < stringArrayList.size(); i++) {
            if (stringArrayList.get(i).contains(call.getMatcher().group())) {
                stringArrayList.addAll(i - 1, List.of(stringBeforeReturn));
                break;
            }
        }

        // Creando una nueva variable que contiene la nueva información agregada que se extrajo de la función a remover
        String newTextBody0 = String.join("\n", stringArrayList);

        // Adaptando el return de la función a remover por la secuencia encontrada en la función base
        newTextBody0 = newTextBody0.replace(call.getMatcher().group(), textBody2.split("return")[1]);
        System.out.println(newTextBody0);

        // Retornando el objeto String de la función base que ya tiene insertado el código de la función a remover
        return newTextBody0;


    }

    public String removeTransitiveVariables(String compactedBody) {
        // Línea que se usa para encontrar variables transitivas
        String transitivePattern = "ISC\\d{3} = ISC\\d{3} ;";
        String codigoFinal = "";

        Pattern pattern = Pattern.compile(transitivePattern);

        String[] lines = compactedBody.split("\n");
        // Iterando las lineas para encontrar alguna variable transtiva desde el final al principio
        for (int i = lines.length - 1; i > 0; i--) {
            // encontrar variable transitiva
            Matcher matcher = pattern.matcher(lines[i]);
            if (matcher.find()) {
                // Encontrando origen de la variable transitiva
                String redundantNameVariable = matcher.group().split(" ")[2];
                // Creando el patrón para encontrar la sentencia origen de la variable transitiva
                String originSequencePattern = redundantNameVariable + " = *.* ;";

                pattern = Pattern.compile(originSequencePattern);

                matcher = pattern.matcher(compactedBody);
                // El find() funciona como un compilado. Importante realizar una condicional en caso de que no haya
                // sentencia de origen de la variable transtiva (posible caso de ser manejada desde la sección de argumentos)
                matcher.find();

                // Seleccionando el código de la sentencia de origen de la variable transitiva
                String originSequence = matcher.group();
                // Extrayendo la operación núcleo. Se aumentan y reducen en 2 para eliminar el =,; y " "
                String operation = originSequence.substring((originSequence.indexOf("=") + 2), (originSequence.indexOf(";") - 2));
                // Para evitar problemas como "variable1 = variable1", se aumenta el " ;" para asegurar que se selecciona el
                // valor (variable transitiva) y no la variable usada para asignar ese valor
                lines[i] = lines[i].replace(redundantNameVariable + " ;", operation + " ;");
                // Al  finalizar se hace uso de un join para unir las lineas, cuales solamente tiene la modificación realizada
                // en la línea actual de la variable transitiva.
                codigoFinal = String.join("\n", lines);
                // Con la sentencia de origen ya trasladada a la línea de la variable transitiva, se elimina dicha sentencia
                codigoFinal = codigoFinal.replace(originSequence, "");
                System.out.println("IMPRIMIENDO EL CÓDIGO FINAL");
                System.out.println(codigoFinal);
                // Se termina la iteración, ya que con los cambios hechos, la estructura de lines ya no es la misma
                // En caso de existir varias variables transitivas o anidadas, es importante empelar  un while para esta
                // función para iterar y eliminar todas las variables transitivas existentes
                break;
            }

        }
        return codigoFinal;
    }

    public static void main(String[] args) {
        OptimisationAnalysis op = new OptimisationAnalysis();
        Functions[] funciones = op.chechFunctionOptimizer(op.test);
//        System.out.println("imprimiendo lo siguiente");
//        System.out.println(funciones[0].getBody());
//        System.out.println("imprimiendo lo siguiente 1");
//        System.out.println(funciones[2].getBody());
//        funciones[0].
       String compactedBody = op.compactBody(funciones[0], funciones[2], funciones[1]);
       op.removeTransitiveVariables(compactedBody);


    }
}