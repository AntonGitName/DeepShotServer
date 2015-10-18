package ru.spbau.mit.antonpp.deepshot.server;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;

/**
 * @author antonpp
 * @since 19/10/15
 */
public class InvertFilter {
    public BufferedImage processImage(BufferedImage image) {
        byte[] invertArray = new byte[256];

        for (int counter = 0; counter < 256; counter++)
            invertArray[counter] = (byte) (255 - counter);

        BufferedImageOp invertFilter = new LookupOp(new ByteLookupTable(0, invertArray), null);
        return invertFilter.filter(image, null);

    }
}
