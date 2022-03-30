import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* This program reads from two files, generates random marks
* and creates a 2d array containing the names and marks that
* gets sent to a new file.
*
* @author Layla Michel
* @version 1.0
* @since 2022-03-22
*/

class ClassMarks {
    /**
    * Creating constants for mean and standard
    * deviation.
    */
    public static final int MEAN = 75;
    public static final int DEVIATION = 10;

    /**
    * Declaring variables.
    */
    static int counter;
    static int counter2;
    static String[][] namesMarksArray;
    static Random randomMark;
    static double markDouble;
    static int markInt;
    static String markString;
    static int rows;
    static int columns;

    /**
    * Creating private constructor due to
    * public/default constructor error.
    *
    * @throws IllegalStateException if there is an issue
    */
    private ClassMarks() {
        throw new IllegalStateException("Utility class");
    }

    /**
    * Creating function to generate random marks for each
    * student into a 2d array.
    *
    * @param arrayNames as array
    * @param arrayAssigns as array
    *
    * @return twoDimensionArray as array
    */
    public static String[][] generateMarks(String[] arrayNames,
        String[] arrayAssigns) {
        // Get the dimensions of the 2d array
        rows = arrayNames.length;
        columns = arrayAssigns.length + 1;

        // Create 2d array
        String[][] twoDimensionArray = new String[columns][rows];

        // Create randomMark to hold in random numbers
        randomMark = new Random();

        // Add names to each row
        for (counter = 0; counter < rows; counter++) {
            twoDimensionArray[0][counter] = arrayNames[counter];
        }

        // Add the 5 marks for each name
        for (counter = 0; counter < rows; counter++) {
            for (counter2 = 1; counter2 < columns; counter2++) {
                twoDimensionArray[counter2][counter] = Integer.toString((int)
                    Math.round(randomMark.nextGaussian() * DEVIATION + MEAN));
            }
        }
        return twoDimensionArray;
    }

    /**
    * Creating main function.
    *
    * @param args nothing passed in
    * @throws IOException if no file is passed in
    */
    public static void main(String[] args)
            throws IOException {
        // Create list to get the names
        final List<String> listOfNames =
            new ArrayList<String>();

        BufferedReader bf = null;
        try {
            // Check if there are some arguments
            if (null != args[0]
                // Length > 4 because a.txt will be shortest filename
                && args[0].length() > 4
                // Check if arguments have the correct file extension
                && args[0].endsWith(".txt")) {
                bf = new BufferedReader(new FileReader(args[0]));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        String line = bf.readLine();

        // Add file elements to list
        while (line != null) {
            listOfNames.add(line);
            line = bf.readLine();
        }

        // Create array of names of the size of the list
        final String[] namesArray = new String[listOfNames.size()];

        // Fill the array with the same elements as the list
        for (counter = 0; counter < listOfNames.size(); counter++) {
            namesArray[counter] = listOfNames.get(counter);
        }

        // Create list to get the assignments
        final List<String> listOfAssigns =
            new ArrayList<String>();

        BufferedReader bf2 = null;
        try {
            // Check if there are some arguments
            if (null != args[1]
                // Length > 4 because a.txt will be shortest filename
                && args[1].length() > 4
                // Check if arguments have the correct file extension
                && args[1].endsWith(".txt")) {
                bf2 = new BufferedReader(new FileReader(args[1]));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        String line2 = bf2.readLine();

        // Add file elements to list
        while (line2 != null) {
            listOfAssigns.add(line2);
            line2 = bf2.readLine();
        }

        // Create array of assignments of the size of the list
        final String[] assignsArray = new String[listOfAssigns.size()];

        // Fill the array with the same elements as the list
        for (counter = 0; counter < listOfAssigns.size(); counter++) {
            assignsArray[counter] = listOfAssigns.get(counter);
        }

        try {
            // Call function to get the 2d array
            namesMarksArray = generateMarks(namesArray, assignsArray);

            // Build a string containing the elements of the 2d array
            final StringBuilder builder = new StringBuilder();
            for (int rows2 = 0; rows2 < namesMarksArray[0].length; rows2++) {
                for (int columns2 = 0; columns2
                        < namesMarksArray.length; columns2++) {
                    builder.append(namesMarksArray[columns2][rows2]);
                    builder.append(" ");
                }
                builder.append("\n");
            }

            // Create new file called "marks.cvs"
            final BufferedWriter writer = new BufferedWriter(new
                    FileWriter("/home/ubuntu/"
                + "environment/ISC4U/Unit1/Unit1-07/Unit1-07-Java/marks.cvs"));
            writer.write(builder.toString());
            writer.close();
            System.out.println("Marks added to 'marks.cvs'");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
