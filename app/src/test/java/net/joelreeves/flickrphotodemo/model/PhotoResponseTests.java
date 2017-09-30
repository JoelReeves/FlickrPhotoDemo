package net.joelreeves.flickrphotodemo.model;

import net.joelreeves.flickrphotodemo.data.models.Photo;
import net.joelreeves.flickrphotodemo.data.models.PhotoResponse;
import net.joelreeves.flickrphotodemo.utils.TestJsonLoader;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class PhotoResponseTests {

    private PhotoResponse photoResponse;

    @Before
    public void setUp() throws Exception {
        photoResponse = TestJsonLoader.load(TestJsonLoader.PHOTO_RESPONSE, PhotoResponse.class);
    }

    @Test
    public void testPhotoResponseIsNotNull() {
        assertThat(photoResponse).isNotNull();
    }

    @Test
    public void testPhotoResponsePhotosArrayIsNotNull() {
        assertThat(photoResponse.getPhotos()).isNotNull();
    }

    @Test
    public void testPhotoResponsePhotosArraySizeIsExpectedValue() {
        String expectedSize = "1000";
        assertThat(photoResponse.getPhotos().getTotal()).isEqualTo(expectedSize);
    }

    @Test
    public void testValidIndexReturnsPhoto() {
        assertThat(getPhotoAtIndex(50)).isNotNull();
    }

    @Test
    public void testInvalidIndexReturnsNull() {
        assertThat(getPhotoAtIndex(-1)).isNull();
    }

    @Test
    public void testPhotoValuesAreCorrect() {
        Photo firstPhoto = getPhotoAtIndex(0);
        String expectedId = "23546196988";
        String expectedTitle = "How cool is this headwrap dressing?";
        assertThat(firstPhoto).isNotNull();
        assertThat(firstPhoto.getId()).isEqualTo(expectedId);
        assertThat(firstPhoto.getTitle()).isEqualTo(expectedTitle);
    }

    private Photo getPhotoAtIndex(int index) {
        if (index < 0 || index > photoResponse.getPhotos().getPhoto().size() - 1) {
            return null;
        } else {
            return photoResponse.getPhotos().getPhoto().get(index);
        }
    }
}
