/*
 * ICS4U.2015.16.S2
 */
package edu.hdsb.gwss.cook.u1;

import becker.xtras.imageTransformation.ITransformations;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.util.ArrayList;

public class Transformer extends Object implements ITransformations {

    public static final int MIN_NUM_TRANS = 11;
    public static final String DARKEN = "Darken";
    public static final String BRIGHTEN = "Brighten";
    public static final String INVERT = "Invert";
    public static final String FLIPX = "Flip X";
    public static final String FLIPY = "Flip Y";
    public static final String ROTATE = "Rotate";
    public static final String SCALE50 = "Scale 50%";
    public static final String MIRROR = "Mirror";
    public static final String BLUR = "Blur";
    public static final String UNDO = "Undo";
    public static final String RESET = "Reset";

    private String[] transformations = new String[MIN_NUM_TRANS];

    private int[][] pictureOriginal;
    private int[][] picture;

    ArrayList<int[][]> actions = new ArrayList<int[][]>();
    int numberOfActions = 0;
    int undos = 0;
    int rotate = 0;

    /**
     * Construct a Transformer object by setting the possible transformations
     * available.
     */
    public Transformer() {
        super();
        this.transformations[0] = DARKEN;
        this.transformations[1] = BRIGHTEN;
        this.transformations[2] = INVERT;
        this.transformations[3] = FLIPX;
        this.transformations[4] = FLIPY;
        this.transformations[5] = ROTATE;
        this.transformations[6] = SCALE50;
        this.transformations[7] = MIRROR;
        this.transformations[8] = BLUR;
        this.transformations[9] = UNDO;
        this.transformations[10] = RESET;
    }

    /**
     * Construct a Transformer object by setting the possible transformations
     * available and initializing the state of the image.
     *
     * @param originalPic A 2-D array representing a grey scale image. The array
     * contains values from 0 - 255.
     */
    public Transformer(int[][] originalPic) {
        this();
        this.setPixels(originalPic);
    }

    /**
     * Get the image that was transformed.
     *
     * @return The pixels representing the image.
     */
    public int[][] getPixels() {
        return this.picture;
    }

    /**
     * Set the image to be transformed to a new set of pixels.
     *
     * @param newPix The new image to be used for subsequent transformations.
     */
    public void setPixels(int[][] newPix) {
        this.pictureOriginal = newPix;
        this.picture = this.copyArray(newPix);
    }

    /**
     * A array filled with the names of the transformations implemented by this
     * class.
     *
     * @return The array of transformation names.
     */
    public String[] getTransformationNames() {
        return transformations;
    }

    public static void display(int[][] twoDArray) {
        for (int row = 0; row < twoDArray.length; row++) {
            for (int col = 0; col < twoDArray[row].length; col++) {
                if (twoDArray[row][col] == 0) {
                    System.out.print(" .");
                } else {
                    System.out.print(" O");
                }
            }
            System.out.println("");
        }
    }

    /**
     * Perform the transformation indicated.
     *
     * @param transformationName The name of the transformation to perform. Must
     * be one of the transformation names returned by {@link #getTransformationNames
     * getTransformationNames}.
     */
    public void performTransformation(String transformationName) {

        if (DARKEN.equals(transformationName)) {
            this.picture = changeIntensity(-2, this.picture);
        } else if (BRIGHTEN.equals(transformationName)) {
            this.picture = changeIntensity(2, this.picture);
        } else if (INVERT.equals(transformationName)) {
            this.picture = invert(this.picture);
        } else if (FLIPX.equals(transformationName)) {
            this.picture = flipX(this.picture);
        } else if (FLIPY.equals(transformationName)) {
            this.picture = flipY(this.picture);
        } else if (ROTATE.equals(transformationName)) {
            this.picture = rotate(this.picture);
        } else if (MIRROR.equals(transformationName)) {
            this.picture = mirror(this.picture);
        } else if (SCALE50.equals(transformationName)) {
            this.picture = scale50(this.picture);
        } else if (BLUR.equals(transformationName)) {
            this.picture = blur(this.picture);
        } else if (RESET.equals(transformationName)) {
            this.picture = this.copyArray(this.picture);
        } else if (UNDO.equals(transformationName)) {
            this.picture = this.undo();
        } else {
            throw new Error("Invalid transformation requested.");
        }
    }

    /**
     * TODO: ICS4U - TODO
     */
    private int[][] copyArray(int[][] a) {
        int[][] copy = new int[pictureOriginal.length][pictureOriginal[0].length];
        for (int r = 0; r < copy.length; r++) {
            for (int c = 0; c < copy[r].length; c++) {
                copy[r][c] = pictureOriginal[r][c];
            }
        }

        a = copy;
        return a;
    }

    /**
     * TODO: ICS4U - TODO
     */
    private int[][] undo() {
        undos++;

        if (!actions.isEmpty() && actions.size() != 1 && numberOfActions - undos > 0) {
            return actions.get(numberOfActions - undos - 1);
        } else {
            return copyArray(pictureOriginal);
        }

    }

    /**
     * TODO: ICS4U - TODO
     */
    private int[][] changeIntensity(double percent, int[][] sourcePixels) {
        // TO DO  
        boolean dark = false;

        for (int r = 0; r < sourcePixels.length; r++) {
            for (int c = 0; c < sourcePixels[r].length; c++) {
                if (percent == 2) {
                    if (sourcePixels[r][c] + 10 < 256) {
                        sourcePixels[r][c] += 10;
                        dark = true;
                    }
                } else {
                    if (sourcePixels[r][c] - 10 > -1) {
                        sourcePixels[r][c] -= 10;
                        dark = false;
                    }
                }
            }
        }

        int[][] transformedArray = new int[sourcePixels.length][sourcePixels[0].length];
        for (int r = 0; r < transformedArray.length; r++) {
            for (int c = 0; c < transformedArray[r].length; c++) {
                transformedArray[r][c] = sourcePixels[r][c];
            }

        }

        actions.add(transformedArray);
        numberOfActions++;

        return sourcePixels;
    }

    /**
     * TODO: ICS4U - TODO
     */
    private int[][] invert(int[][] sourcePixels) {
        // TO DO

        for (int r = 0; r < sourcePixels.length; r++) {
            for (int c = 0; c < sourcePixels[r].length; c++) {
                sourcePixels[r][c] = 255 - sourcePixels[r][c];
            }
        }

        int[][] transformedArray = new int[sourcePixels.length][sourcePixels[0].length];
        for (int r = 0; r < transformedArray.length; r++) {
            for (int c = 0; c < transformedArray[r].length; c++) {
                transformedArray[r][c] = sourcePixels[r][c];
            }

        }

        actions.add(transformedArray);
        numberOfActions++;

        return sourcePixels;
    }

    /**
     * TODO: ICS4U - TODO
     */
    private int[][] flipX(int[][] sourcePixels) {
        // TO DO

        for (int r = 0; r < sourcePixels.length; r++) {
            for (int c = 0; c < sourcePixels[r].length / 2; c++) {
                int temp = sourcePixels[r][sourcePixels[r].length - c - 1];
                sourcePixels[r][sourcePixels[r].length - c - 1] = sourcePixels[r][c];
                sourcePixels[r][c] = temp;

            }
        }

        int[][] transformedArray = new int[sourcePixels.length][sourcePixels[0].length];
        for (int r = 0; r < transformedArray.length; r++) {
            for (int c = 0; c < transformedArray[r].length; c++) {
                transformedArray[r][c] = sourcePixels[r][c];
            }

        }

        actions.add(transformedArray);
        numberOfActions++;

        return sourcePixels;
    }

    /**
     * TODO: ICS4U - TODO
     */
    private int[][] flipY(int[][] sourcePixels) {
        // TO DO

        for (int r = 0; r < sourcePixels.length / 2; r++) {
            for (int c = 0; c < sourcePixels[r].length; c++) {
                int temp = sourcePixels[sourcePixels.length - r - 1][c];
                sourcePixels[sourcePixels.length - r - 1][c] = sourcePixels[r][c];
                sourcePixels[r][c] = temp;
            }
        }

        int[][] transformedArray = new int[sourcePixels.length][sourcePixels[0].length];
        for (int r = 0; r < transformedArray.length; r++) {
            for (int c = 0; c < transformedArray[r].length; c++) {
                transformedArray[r][c] = sourcePixels[r][c];
            }

        }

        actions.add(transformedArray);
        numberOfActions++;

        return sourcePixels;
    }

    /**
     * TODO: ICS4U - TODO
     */
    private int[][] rotate(int[][] sourcePixels) {
        // TO DO
        rotate++;
        boolean transformed = false;
        boolean sourced = false;

        int[][] transformedPixels = new int[sourcePixels[0].length][sourcePixels.length];
        for (int r = 0; r < transformedPixels.length; r++) {
            for (int c = 0; c < transformedPixels[r].length; c++) {
                    transformedPixels[r][c] = sourcePixels[c][r];
                    transformed = true;
                 
            }
        }
        
        picture = transformedPixels;

        int[][] transformedArray = new int[transformedPixels.length][transformedPixels[0].length];
        for (int r = 0; r < transformedArray.length; r++) {
            for (int c = 0; c < transformedArray[r].length; c++) {
                transformedArray[r][c] = transformedPixels[r][c];
                //System.out.println(sourcePixels[c][r]);
            }

        }
        
        

        actions.add(transformedArray);
        numberOfActions++;

        
            return transformedPixels;
        
       
    }

    /**
     * TODO: ICS4U - TODO
     */
    private int[][] mirror(int[][] sourcePixels) {
        // TO DO

        int[][] doubleArray = new int[sourcePixels.length][sourcePixels[0].length * 2];

        for (int r = 0; r < sourcePixels.length; r++) {
            for (int c = 0; c < sourcePixels[r].length; c++) {
                doubleArray[r][c] = sourcePixels[r][c];
                doubleArray[r][c + sourcePixels[0].length] = sourcePixels[r][sourcePixels[0].length - 1 - c];
            }
        }

        int[][] transformedArray = new int[doubleArray.length][doubleArray[0].length];
        for (int r = 0; r < transformedArray.length; r++) {
            for (int c = 0; c < transformedArray[r].length; c++) {
                transformedArray[r][c] = doubleArray[r][c];
            }

        }

        actions.add(transformedArray);
        numberOfActions++;

        return doubleArray;
    }

    /**
     * TODO: ICS4U - TODO
     */
    private int[][] scale50(int[][] sourcePixels) {
        // TO DO

        int[][] scaledArray = new int[sourcePixels.length / 2][sourcePixels[0].length / 2];

        for (int r = 0; r < scaledArray.length; r++) {
            for (int c = 0; c < scaledArray[r].length; c++) {
                scaledArray[r][c] = sourcePixels[r * 2][c * 2];
            }
        }

        int[][] transformedArray = new int[scaledArray.length][scaledArray[0].length];
        for (int r = 0; r < transformedArray.length; r++) {
            for (int c = 0; c < transformedArray[r].length; c++) {
                transformedArray[r][c] = scaledArray[r][c];
            }

        }

        actions.add(transformedArray);
        numberOfActions++;

        return scaledArray;
    }

    /**
     * TODO: ICS4U - TODO
     */
    private int[][] blur(int[][] sourcePixels) {
        int[][] blurredPixels = new int[sourcePixels.length][sourcePixels[0].length];
        int average = 0;

        for (int r = 1; r < blurredPixels.length - 1; r++) {
            for (int c = 1; c < blurredPixels[r].length - 1; c++) {
                average = (sourcePixels[r - 1][c - 1] + sourcePixels[r - 1][c] + sourcePixels[r - 1][c + 1] + sourcePixels[r][c - 1] + sourcePixels[r][c] + sourcePixels[r][c + 1] + sourcePixels[r + 1][c - 1] + sourcePixels[r + 1][c] + sourcePixels[r + 1][c + 1]) / 9;

                blurredPixels[r][c] = average;
            }
        }

        System.out.println("LENGTH = " + sourcePixels.length);

        // ADD CASES FOR END ROWS
        // - For far left and far right row
        // - Work from top to bottom
        for (int c = 0; c < sourcePixels[0].length; c = c + sourcePixels[0].length - 1) {
            for (int r = 0; r < sourcePixels.length; r++) {
                if (c == 0 && r > 0 && r < sourcePixels.length - 1) {
                    average = (sourcePixels[r - 1][0] + sourcePixels[r - 1][1] + sourcePixels[r][1] + sourcePixels[r][0] + sourcePixels[r + 1][1] + sourcePixels[r + 1][0]) / 6;
                    blurredPixels[r][0] = average;
                } else if (c == sourcePixels[0].length - 1 && r > 0 && r < sourcePixels.length - 1) {
                    average = (sourcePixels[r - 1][sourcePixels[0].length - 1] + sourcePixels[r - 1][sourcePixels[0].length - 2] + sourcePixels[r][sourcePixels[0].length - 2] + sourcePixels[r][sourcePixels[0].length - 1] + sourcePixels[r + 1][sourcePixels[0].length - 2] + sourcePixels[r + 1][sourcePixels[0].length - 1]) / 6;
                    blurredPixels[r][sourcePixels[0].length - 1] = average;
                } else if (r == 0 && c == 0) {
                    average = (sourcePixels[r][c] + sourcePixels[r][c + 1] + sourcePixels[r + 1][c] + sourcePixels[r + 1][c + 1]) / 4;
                    blurredPixels[r][c] = average;
                } else if (r == 0 && c == sourcePixels[0].length - 1) {
                    average = (sourcePixels[r][c] + sourcePixels[r][c - 1] + sourcePixels[r + 1][c] + sourcePixels[r + 1][c - 1]) / 4;
                    blurredPixels[r][c] = average;
                } else if (r == (sourcePixels.length - 1) && c == 0) {
                    average = (sourcePixels[r][c] + sourcePixels[r - 1][c] + sourcePixels[r - 1][c + 1] + sourcePixels[r][c + 1]) / 4;
                    blurredPixels[r][c] = average;
                }
            }
        }

        // - From top to bottom
        // - Work from left to right
        for (int r = 0; r < sourcePixels.length; r = r + sourcePixels.length - 1) {
            for (int c = 0; c < sourcePixels.length - 1; c++) {
                if (c > 0 && r == 0) {
                    average = (sourcePixels[0][c] + sourcePixels[0][c - 1] + sourcePixels[0][c + 1] + sourcePixels[1][c] + sourcePixels[1][c - 1] + sourcePixels[1][c + 1]) / 6;
                    blurredPixels[r][c] = average;
                } else if (r == sourcePixels.length - 1 && c > 0) {
                    average = (sourcePixels[sourcePixels.length - 1][c] + sourcePixels[sourcePixels.length - 1][c - 1] + sourcePixels[sourcePixels.length - 1][c + 1] + sourcePixels[sourcePixels.length - 2][c] + sourcePixels[sourcePixels.length - 2][c - 1] + sourcePixels[sourcePixels.length - 2][c + 1]) / 6;
                    blurredPixels[r][c] = average;
                }
            }
        }

        int[][] transformedArray = new int[blurredPixels.length][blurredPixels[0].length];
        for (int r = 0; r < transformedArray.length; r++) {
            for (int c = 0; c < transformedArray[r].length; c++) {
                transformedArray[r][c] = blurredPixels[r][c];
            }

        }

        actions.add(transformedArray);
        numberOfActions++;

        return blurredPixels;
    }

    private int[][] transformArray(int[][] sourcePixels) {
        int[][] transformedArray = new int[sourcePixels.length][sourcePixels[0].length];
        for (int r = 0; r < transformedArray.length; r++) {
            for (int c = 0; c < transformedArray[r].length; c++) {
                transformedArray[r][c] = sourcePixels[r][c];
            }

        }
        return transformedArray;
    }

    /**
     * TODO: ICS4U - TODO
     */
    public static void main(String[] args) {

        int[][] myPicture = new int[4][15];

        myPicture[0][0] = 1;
        myPicture[1][1] = 1;
        myPicture[2][2] = 1;
        myPicture[3][3] = 1;
        myPicture[2][4] = 1;
        myPicture[1][5] = 1;
        myPicture[2][6] = 1;
        myPicture[3][7] = 1;
        myPicture[2][8] = 1;
        myPicture[1][9] = 1;
        myPicture[0][10] = 1;

//       Construct the test object
        Transformer test = new Transformer(myPicture);

//       Display Original Image
        System.out.println("Original\n");
        display(myPicture);

//       Test flip on X-axis
        System.out.println("\nFlipped on the X axis.\n");
        test.performTransformation(FLIPX);
        display(test.getPixels());

//       Test flip on Y-axis
        System.out.println("\nFlipped on the Y axis.\n");
        test.performTransformation(FLIPY);
        display(test.getPixels());

//       Test Rotate 90 degrees
        System.out.println("\nRotated 90 degrees.\n");
        test.performTransformation(ROTATE);
        display(test.getPixels());

//       Test Rotate Scale 50%
        System.out.println("\nScaled 50%.\n");
        test.performTransformation(SCALE50);
        display(test.getPixels());

//       Test Mirror Image
        System.out.println("\nMirror image.\n");
        test.performTransformation(MIRROR);
        display(test.getPixels());

//       Test Reset
        System.out.println("\nReset image.\n");
        test.performTransformation(RESET);
        display(test.getPixels());

    }

}
