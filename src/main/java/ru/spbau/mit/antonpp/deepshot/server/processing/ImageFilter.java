package ru.spbau.mit.antonpp.deepshot.server.processing;

import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.InvertFilter;
import com.jhlabs.image.SkeletonFilter;

import java.awt.image.BufferedImage;

/**
 * @author antonpp
 * @since 27/10/15
 */
public enum ImageFilter {
    GAUSSIAN {
        private final GaussianFilter filter = new GaussianFilter(5);

        @Override
        public BufferedImage process(BufferedImage image) {
            return filter.filter(image, null);
        }
    }, SKELETON {
        private final SkeletonFilter filter = new SkeletonFilter();

        @Override
        public BufferedImage process(BufferedImage image) {
            return filter.filter(image, null);
        }
    }, INVERT {
        private final InvertFilter filter = new InvertFilter();

        @Override
        public BufferedImage process(BufferedImage image) {
            return filter.filter(image, null);
        }
    }, GRAYSCALE {
        private final GrayscaleFilter filter = new GrayscaleFilter();

        @Override
        public BufferedImage process(BufferedImage image) {
            return filter.filter(image, null);
        }
    };

    public static ImageFilter fromLong(long id) {
        switch ((int) id) {
            case 1:
                return GAUSSIAN;
            case 2:
                return SKELETON;
            case 3:
                return INVERT;
            case 4:
                return GRAYSCALE;
            default:
                return null;

        }
    }

    public abstract BufferedImage process(BufferedImage image);
}
