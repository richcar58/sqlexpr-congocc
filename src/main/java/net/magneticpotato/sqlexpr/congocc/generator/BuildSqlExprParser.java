package net.magneticpotato.sqlexpr.congocc.generator;

import java.nio.file.Path;
import java.util.Map;

/** This class generates the sql expression language subset supported for tapis searching.
 * The default location of the javacc grammar file is relative to the project root directory.
 * If the current directory is something other than the project root directory, the path
 * to the grammar file can be passed in as an argument.
 * 
 * See the project README file for usage information.
 * 
 * @author rcardone
 */
public class BuildSqlExprParser 
{
    // Grammar file pathname relative to project directory.
    public static final String DEFAULT_GRAMMAR_FILE = "src/main/resources/SqlExprParser.ccc";
    
    // Run the javacc parser generator to create the sql expression language.
    public static void main(String[] args) throws Exception 
    {
        // Optional user input overrides the default.
        String path = args.length > 0 ? args[0] : DEFAULT_GRAMMAR_FILE;
        System.out.println("CongoCC input file: " + path);
        
        // We assume that we are in the project root directory.
        // Path grammarFile, Path outputDir, String codeLang, int jdkTarget, boolean quiet, Map<String, String> symbols
        final var grammarFile = Path.of(path);
        final var outputDir = Path.of("../java/");
        final var codeLang = "java";
        final int jdkTarget = 17;
        final var quiet = false;
        final Map<String, String> symbols = null;
        
        //try {org.congocc.app.Main.main(new String[] {path});}
        try {org.congocc.app.Main.mainProgram(grammarFile, outputDir, codeLang, jdkTarget, quiet, symbols);}
        catch (Exception e) {
        	System.out.println("ERROR: CongoCC parser parser code:\n\n" + e);
        }
    }
}
